package ru.kradin.blog.services.interfaces;

import org.springframework.security.core.Authentication;
import ru.kradin.blog.exceptions.CommentNotFoundException;
import ru.kradin.blog.exceptions.PostNotFoundException;
import ru.kradin.blog.models.Comment;

public interface CommentService {

    public void addCommentToPost(Authentication authentication, Comment comment, long postId) throws PostNotFoundException;

    public void addCommentToComment(Authentication authentication, Comment comment, long postId, long parentCommentId) throws PostNotFoundException, CommentNotFoundException;

    public void deleteCommentByAuthenticationAndId(Authentication authentication, long commentId) throws CommentNotFoundException;
}
