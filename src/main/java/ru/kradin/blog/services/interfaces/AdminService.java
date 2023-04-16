package ru.kradin.blog.services.interfaces;

import ru.kradin.blog.dto.UserDTO;
import ru.kradin.blog.exceptions.CommentNotFoundException;
import ru.kradin.blog.models.User;

import java.util.List;

public interface AdminService {

    public List<UserDTO> getAllUsers();

    public List<UserDTO> getNonBannedUsers();

    public List<UserDTO> getBannedUsers();

    public void deleteComment(long id) throws CommentNotFoundException;

    public void toggleUserBan(String username);

    public void createAdminAccountIfNotExist();
}
