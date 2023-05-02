package ru.kradin.blog.services.implementations;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kradin.blog.dto.CommentCreateDTO;
import ru.kradin.blog.dto.CommentDTO;
import ru.kradin.blog.exceptions.CommentNotFoundException;
import ru.kradin.blog.exceptions.PostNotFoundException;
import ru.kradin.blog.models.Comment;
import ru.kradin.blog.models.Post;
import ru.kradin.blog.models.User;
import ru.kradin.blog.repositories.CommentRepository;
import ru.kradin.blog.repositories.PostRepository;
import ru.kradin.blog.services.interfaces.CommentService;
import ru.kradin.blog.services.interfaces.AuthenticatedUserService;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    private static final Logger log = LoggerFactory.getLogger(CommentServiceImpl.class);

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    AuthenticatedUserService authenticatedUserService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional
    @Cacheable(value = "comments", key = "#id")
    public List<CommentDTO> getPostCommentsByPostId(long id) throws PostNotFoundException {
        Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException());
        List<Comment> comments = post.getComments();
        List<CommentDTO> commentDTOS = modelMapper.map(comments, new TypeToken<List<CommentDTO>>() {}.getType());
        return commentDTOS;
    }

    @Override
    @Transactional
    @PreAuthorize("isAuthenticated()")
    @CacheEvict(value = "comments", key = "#result.parentPost.id")
    public CommentDTO addComment(CommentCreateDTO commentCreateDTO) throws PostNotFoundException, CommentNotFoundException {
        Comment comment = new Comment();
        User user = authenticatedUserService.getCurentUser();
        Post parentPost = postRepository.findById(commentCreateDTO.getParentPostId()).orElseThrow(() -> new PostNotFoundException());

        if (commentCreateDTO.getParentCommentId()>0) {
            Comment parentComment = commentRepository.findById(commentCreateDTO.getParentCommentId()).orElseThrow(() -> new CommentNotFoundException());
            comment.setParentComment(parentComment);
        }

        comment.setParentPost(parentPost);
        comment.setUser(user);
        comment.setText(commentCreateDTO.getText());
        comment.setDeleted(false);
        comment.setCreatedAt(LocalDateTime.now());
        comment.setDepth(0);
        comment = commentRepository.save(comment);

        log.info("User {} added comment to post with id {}",user.getUsername(),comment.getParentPost().getId());

        return modelMapper.map(comment, CommentDTO.class);
    }

    @Override
    @Transactional
    @PreAuthorize("isAuthenticated()")
    @CacheEvict(value = "comments", key = "#result.parentPost.id")
    public CommentDTO deleteComment(long commentId) throws CommentNotFoundException {
        User user = authenticatedUserService.getCurentUser();
        Comment comment = commentRepository.findByUserAndId(user, commentId).orElseThrow(() -> new CommentNotFoundException());
        comment.setDeleted(true);
        comment.setText("deleted");
        comment = commentRepository.save(comment);

        log.info("User {} deleted comment with id {}", user.getUsername(), comment.getId());

        return modelMapper.map(comment, CommentDTO.class);
    }
}
