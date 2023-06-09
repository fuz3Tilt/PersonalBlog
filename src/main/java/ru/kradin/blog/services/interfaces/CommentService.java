package ru.kradin.blog.services.interfaces;

import ru.kradin.blog.dto.CommentCreateDTO;
import ru.kradin.blog.dto.CommentDTO;
import ru.kradin.blog.exceptions.CommentNotFoundException;
import ru.kradin.blog.exceptions.PostNotFoundException;

import java.util.List;

public interface CommentService {

    public List<CommentDTO> getPostCommentsByPostId(long id) throws PostNotFoundException;

    public CommentDTO addComment(CommentCreateDTO commentCreateDTO) throws PostNotFoundException, CommentNotFoundException;

    public CommentDTO deleteComment(long commentId) throws CommentNotFoundException;
}
