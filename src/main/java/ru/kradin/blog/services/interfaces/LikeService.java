package ru.kradin.blog.services.interfaces;

import ru.kradin.blog.dto.LikeDTO;
import ru.kradin.blog.exceptions.CommentNotFoundException;
import ru.kradin.blog.exceptions.PostNotFoundException;

import java.util.List;

public interface LikeService {

    public List<LikeDTO> getPostLikesByPostId(long id);

    public void togglePostLike(long postId) throws PostNotFoundException;

    public void toggleCommentLike(long commentId) throws CommentNotFoundException;

}
