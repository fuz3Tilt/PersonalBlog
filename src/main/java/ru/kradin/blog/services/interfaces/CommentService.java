package ru.kradin.blog.services.interfaces;

import org.springframework.security.core.Authentication;
import ru.kradin.blog.exceptions.CommentNotFoundException;
import ru.kradin.blog.exceptions.PostNotFoundException;
import ru.kradin.blog.models.Comment;

public interface CommentService {

    public void addCommentToPost(Authentication authentication, Comment comment) throws PostNotFoundException;

    public void addCommentToComment(Authentication authentication, Comment comment) throws PostNotFoundException, CommentNotFoundException;

    public void deleteComment(Authentication authentication, Comment comment) throws CommentNotFoundException;
}
