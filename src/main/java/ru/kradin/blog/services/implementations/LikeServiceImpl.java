package ru.kradin.blog.services.implementations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
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
import ru.kradin.blog.services.interfaces.UserAuthenticationService;

import java.time.LocalDateTime;
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
    UserAuthenticationService userAuthenticationService;

    @Override
    public void togglePostLike(Authentication authentication, long postId) throws PostNotFoundException {
        User user = userAuthenticationService.getCurentUser(authentication);
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

    }

    @Override
    public void toggleCommentLike(Authentication authentication, long commentId) throws CommentNotFoundException {
        User user = userAuthenticationService.getCurentUser(authentication);
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
    }
}
