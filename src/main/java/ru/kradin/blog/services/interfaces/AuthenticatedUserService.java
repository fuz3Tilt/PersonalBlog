package ru.kradin.blog.services.interfaces;

import org.springframework.security.core.Authentication;
import ru.kradin.blog.models.User;

public interface AuthenticatedUserService {

    public User getCurentUser(Authentication authentication);
}
