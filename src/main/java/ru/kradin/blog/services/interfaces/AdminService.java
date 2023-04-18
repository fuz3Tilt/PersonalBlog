package ru.kradin.blog.services.interfaces;

import ru.kradin.blog.dto.UserInfoDTO;
import ru.kradin.blog.exceptions.CommentNotFoundException;

import java.util.List;

public interface AdminService {

    public List<UserInfoDTO> getAllUsers();

    public List<UserInfoDTO> getNonBannedUsers();

    public List<UserInfoDTO> getBannedUsers();

    public void deleteComment(long id) throws CommentNotFoundException;

    public void toggleUserBan(String username);

    public void createAdminAccountIfNotExist();
}
