package ru.kradin.blog.services.interfaces;

import ru.kradin.blog.dto.UserInfoDTO;

public interface UserInfoService {

    public UserInfoDTO getUserInfo();

    public void updateEmail(String email);

    public void updatePassword(String password);

}
