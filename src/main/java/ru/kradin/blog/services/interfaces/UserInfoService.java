package ru.kradin.blog.services.interfaces;

import org.springframework.security.core.Authentication;
import ru.kradin.blog.dto.UserDTO;

public interface UserInfoService {

    public UserDTO getUserInfo(Authentication authentication);

    public void updateEmail(Authentication authentication, String email);

    public void updatePassword(Authentication authentication, String password);

}
