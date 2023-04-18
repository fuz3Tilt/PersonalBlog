package ru.kradin.blog.services.interfaces;

import ru.kradin.blog.dto.CommentCreateDTO;
import ru.kradin.blog.dto.CommentDTO;
import ru.kradin.blog.exceptions.CommentNotFoundException;
import ru.kradin.blog.exceptions.PostNotFoundException;

import java.util.List;

public interface CommentService {

    public List<CommentDTO> getPostCommentsByPostId(long id) throws PostNotFoundException;

    public void addCommentToPost(CommentCreateDTO commentCreateDTO) throws PostNotFoundException;

    public void addCommentToComment(CommentCreateDTO commentCreateDTO) throws PostNotFoundException, CommentNotFoundException;

    public void deleteComment(long commentId) throws CommentNotFoundException;
}
