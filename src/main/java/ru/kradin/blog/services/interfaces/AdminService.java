package ru.kradin.blog.services.interfaces;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.kradin.blog.dto.UserInfoDTO;
import ru.kradin.blog.exceptions.CommentNotFoundException;
import ru.kradin.blog.exceptions.UserNotFoundException;

import java.util.List;

public interface AdminService {

    public UserInfoDTO getUserById(long id) throws UserNotFoundException;

    public List<UserInfoDTO> getAllUsers();

    public List<UserInfoDTO> getActiveUsers();

    public List<UserInfoDTO> getBannedUsers();

    public void deleteComment(long id) throws CommentNotFoundException;

    public void toggleUserBan(String username) throws UserNotFoundException;

    public void createAdminAccountIfNotExist();
}
