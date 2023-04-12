package ru.kradin.blog.services.implementations;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

@Service
public class CommentServiceImpl implements CommentService {
    private static final Logger log = LoggerFactory.getLogger(CommentServiceImpl.class);

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    AuthenticatedUserService authenticatedUserService;

    @Override
    @Transactional
    public void addCommentToPost(Authentication authentication, Comment comment) throws PostNotFoundException {
        User user = authenticatedUserService.getCurentUser(authentication);
        Post post = postRepository.findById(comment.getParentPost().getId()).orElseThrow(() -> new PostNotFoundException());
        comment.setUser(user);
        comment.setParentPost(post);
        comment.setDeleted(false);
        comment.setCreatedAt(LocalDateTime.now());
        commentRepository.save(comment);
        log.info("User {} added comment to post with id {}",user.getUsername(),post.getId());
    }

    @Override
    @Transactional
    public void addCommentToComment(Authentication authentication, Comment comment) throws PostNotFoundException, CommentNotFoundException {
        User user = authenticatedUserService.getCurentUser(authentication);
        Post post = postRepository.findById(comment.getParentPost().getId()).orElseThrow(() -> new PostNotFoundException());
        Comment parentComment = commentRepository.findById(comment.getParentComment().getId()).orElseThrow(() -> new CommentNotFoundException());
        comment.setUser(user);
        comment.setParentPost(post);
        comment.setParentComment(parentComment);
        comment.setDeleted(false);
        comment.setCreatedAt(LocalDateTime.now());
        commentRepository.save(comment);
        log.info("User {} added comment to comment with id {}", user.getUsername(), parentComment.getId());
    }

    @Override
    @Transactional
    public void deleteComment(Authentication authentication, Comment comment) throws CommentNotFoundException {
        User user = authenticatedUserService.getCurentUser(authentication);
        comment = commentRepository.findByUserAndId(user, comment.getId()).orElseThrow(() -> new CommentNotFoundException());
        comment.setDeleted(true);
        commentRepository.save(comment);
        log.info("User {} deleted comment with id {}", user.getUsername(), comment.getId());
    }
}
