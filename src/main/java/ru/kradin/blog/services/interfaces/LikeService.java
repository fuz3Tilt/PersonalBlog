package ru.kradin.blog.services.interfaces;

import org.springframework.security.core.Authentication;

public interface LikeService {

    public void togglePostLike(Authentication authentication, long id);

    public void toggleCommentLike(Authentication authentication, long id);

}
