package ru.kradin.blog.services.interfaces;

import org.springframework.security.core.Authentication;
import ru.kradin.blog.dto.CommentDTO;
import ru.kradin.blog.exceptions.CommentNotFoundException;
import ru.kradin.blog.exceptions.PostNotFoundException;

public interface CommentService {

    public void addCommentToPost(Authentication authentication, CommentDTO commentDTO) throws PostNotFoundException;

    public void addCommentToComment(Authentication authentication, CommentDTO commentDTO) throws PostNotFoundException, CommentNotFoundException;

    public void deleteComment(Authentication authentication, long commentId) throws CommentNotFoundException;
}
