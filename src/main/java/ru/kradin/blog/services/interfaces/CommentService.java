package ru.kradin.blog.services.interfaces;

import org.springframework.security.core.Authentication;
import ru.kradin.blog.dto.CommentCreateDTO;
import ru.kradin.blog.dto.CommentDTO;
import ru.kradin.blog.exceptions.CommentNotFoundException;
import ru.kradin.blog.exceptions.PostNotFoundException;

import java.util.List;

public interface CommentService {

    public List<CommentDTO> getPostCommentsByPostId(long id);

    public void addCommentToPost(Authentication authentication, CommentCreateDTO commentCreateDTO) throws PostNotFoundException;

    public void addCommentToComment(Authentication authentication, CommentCreateDTO commentCreateDTO) throws PostNotFoundException, CommentNotFoundException;

    public void deleteComment(Authentication authentication, long commentId) throws CommentNotFoundException;
}
