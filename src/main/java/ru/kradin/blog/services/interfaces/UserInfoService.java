package ru.kradin.blog.services.interfaces;

import ru.kradin.blog.dto.EmailDTO;
import ru.kradin.blog.dto.PasswordDTO;
import ru.kradin.blog.dto.UserInfoDTO;

public interface UserInfoService {

    public UserInfoDTO getUserInfo();

    public void updateEmail(EmailDTO emailDTO);

    public void updatePassword(PasswordDTO passwordDTO);

}
