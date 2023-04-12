package ru.kradin.blog.services.interfaces;

import org.springframework.security.core.Authentication;
import ru.kradin.blog.exceptions.CommentNotFoundException;
import ru.kradin.blog.exceptions.PostNotFoundException;
import ru.kradin.blog.models.Comment;
import ru.kradin.blog.models.Post;

public interface LikeService {

    public void togglePostLike(Authentication authentication, Post post) throws PostNotFoundException;

    public void toggleCommentLike(Authentication authentication, Comment comment) throws CommentNotFoundException;

}
