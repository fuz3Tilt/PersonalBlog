package ru.kradin.blog.services.interfaces;

import org.springframework.security.core.Authentication;
import ru.kradin.blog.exceptions.CommentNotFoundException;
import ru.kradin.blog.exceptions.PostNotFoundException;

public interface LikeService {

    public void togglePostLike(Authentication authentication, long postId) throws PostNotFoundException;

    public void toggleCommentLike(Authentication authentication, long commentId) throws CommentNotFoundException;

}
