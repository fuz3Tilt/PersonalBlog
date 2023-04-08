package ru.kradin.blog.services.interfaces;

import org.springframework.security.core.Authentication;
import ru.kradin.blog.models.additional.UserInfo;

public interface UserInfoService {

    public void updateEmail(Authentication authentication, String email);

    public void updatePassword(Authentication authentication, String password);

    public UserInfo getUserInfo(Authentication authentication);
}
