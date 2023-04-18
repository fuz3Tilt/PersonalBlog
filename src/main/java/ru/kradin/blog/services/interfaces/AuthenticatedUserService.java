package ru.kradin.blog.services.interfaces;

import ru.kradin.blog.models.User;

public interface AuthenticatedUserService {

    public User getCurentUser();
}
