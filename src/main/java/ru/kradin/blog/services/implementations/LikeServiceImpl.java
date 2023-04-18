package ru.kradin.blog.services.implementations;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import ru.kradin.blog.dto.LikeDTO;
import ru.kradin.blog.exceptions.CommentNotFoundException;
import ru.kradin.blog.exceptions.PostNotFoundException;
import ru.kradin.blog.models.Comment;
import ru.kradin.blog.models.Like;
import ru.kradin.blog.models.Post;
import ru.kradin.blog.models.User;
import ru.kradin.blog.repositories.CommentRepository;
import ru.kradin.blog.repositories.LikeRepository;
import ru.kradin.blog.repositories.PostRepository;
import ru.kradin.blog.services.interfaces.LikeService;
import ru.kradin.blog.services.interfaces.AuthenticatedUserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class LikeServiceImpl implements LikeService {
    private static final Logger log = LoggerFactory.getLogger(LikeServiceImpl.class);

    @Autowired
    LikeRepository likeRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    AuthenticatedUserService authenticatedUserService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Cacheable(value = "likes", key = "#id")
    public List<LikeDTO> getPostLikesByPostId(long id) {
        List<Like> likes = likeRepository.findByPost_Id(id);
        List<LikeDTO> likeDTOS = modelMapper.map(likes, new TypeToken<List<LikeDTO>>() {}.getType());
        return likeDTOS;
    }

    @Override
    @Transactional
    @PreAuthorize("isAuthenticated()")
    @CacheEvict(value = "likes", key = "#result")
    public long togglePostLike(long postId) throws PostNotFoundException {
        User user = authenticatedUserService.getCurentUser();
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException());

        Optional<Like> likeOptional = likeRepository.findByUserAndPost(user, post);
        if(likeOptional.isPresent()){
            likeRepository.delete(likeOptional.get());
            log.info("Post {} was unliked by {}", post.getId(), user.getUsername());
        } else {
            Like like = new Like();
            like.setUser(user);
            like.setPost(post);
            like.setCreatedAt(LocalDateTime.now());
            likeRepository.save(like);
            log.info("Post {} was liked by {}", post.getId(), user.getUsername());
        }
        return post.getId();
    }

    @Override
    @Transactional
    @PreAuthorize("isAuthenticated()")
    @CacheEvict(value = "comments", key = "#result")
    public long toggleCommentLike(long commentId) throws CommentNotFoundException {
        User user = authenticatedUserService.getCurentUser();
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new CommentNotFoundException());

        Optional<Like> likeOptional = likeRepository.findByUserAndComment(user, comment);
        if (likeOptional.isPresent()) {
            likeRepository.delete(likeOptional.get());
            log.info("Comment {} was unliked by {}", comment.getId(), user.getUsername());
        } else {
            Like like = new Like();
            like.setUser(user);
            like.setComment(comment);
            like.setCreatedAt(LocalDateTime.now());
            likeRepository.save(like);
            log.info("Comment {} was liked by {}", comment.getId(), user.getUsername());
        }
        return comment.getParentPost().getId();
    }
}
