package ru.kradin.blog.services.interfaces;

import ru.kradin.blog.models.User;

import java.util.List;

public interface AdminService {

    public List<User> getAllUsers();

    public List<User> getBannedUsers();

    public void toggleUserBan(String username);

    public void createAdminAccountIfNotExist();
}
