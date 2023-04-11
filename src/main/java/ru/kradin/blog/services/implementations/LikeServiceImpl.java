package ru.kradin.blog.services.implementations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import ru.kradin.blog.models.Post;
import ru.kradin.blog.repositories.CommentRepository;
import ru.kradin.blog.repositories.LikeRepository;
import ru.kradin.blog.repositories.PostRepository;
import ru.kradin.blog.services.interfaces.LikeService;
import ru.kradin.blog.services.interfaces.UserAuthenticationService;

import java.util.Optional;

public class LikeServiceImpl implements LikeService {
    private static final Logger log = LoggerFactory.getLogger(LikeServiceImpl.class);

    @Autowired
    LikeRepository likeRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    UserAuthenticationService userAuthenticationService;

    @Override
    public void togglePostLike(Authentication authentication, long id) {

    }

    @Override
    public void toggleCommentLike(Authentication authentication, long id) {

    }
}
