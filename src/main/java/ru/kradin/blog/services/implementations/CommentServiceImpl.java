package ru.kradin.blog.services.implementations;

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
import ru.kradin.blog.services.interfaces.UserAuthenticationService;

import java.time.LocalDateTime;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserAuthenticationService userAuthenticationService;

    @Override
    public void addCommentToPost(Authentication authentication, Comment comment, long postId) throws PostNotFoundException {
        User user = userAuthenticationService.getCurentUser(authentication);
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException());
        comment.setUser(user);
        comment.setParentPost(post);
        comment.setDeleted(false);
        comment.setCreatedAt(LocalDateTime.now());
        commentRepository.save(comment);
    }

    @Override
    public void addCommentToComment(Authentication authentication, Comment comment, long postId, long parentCommentId) throws PostNotFoundException, CommentNotFoundException {
        User user = userAuthenticationService.getCurentUser(authentication);
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException());
        Comment parentComment = commentRepository.findById(parentCommentId).orElseThrow(() -> new CommentNotFoundException());
        comment.setUser(user);
        comment.setParentPost(post);
        comment.setParentComment(parentComment);
        comment.setDeleted(false);
        comment.setCreatedAt(LocalDateTime.now());
        commentRepository.save(comment);
    }

    @Override
    public void deleteCommentByAuthenticationAndId(Authentication authentication, long commentId) throws CommentNotFoundException {
        User user = userAuthenticationService.getCurentUser(authentication);
        Comment comment = commentRepository.findByUserAndId(user, commentId).orElseThrow(() -> new CommentNotFoundException());
        comment.setDeleted(true);
        commentRepository.save(comment);
    }
}
