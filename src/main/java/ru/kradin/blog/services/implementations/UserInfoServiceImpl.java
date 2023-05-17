package ru.kradin.blog.services.implementations;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kradin.blog.dto.EmailDTO;
import ru.kradin.blog.dto.PasswordDTO;
import ru.kradin.blog.dto.UserInfoDTO;
import ru.kradin.blog.models.User;
import ru.kradin.blog.repositories.UserRepository;
import ru.kradin.blog.services.interfaces.AuthenticatedUserService;
import ru.kradin.blog.services.interfaces.UserInfoService;

/*
    Service for getting and changing information about the current user
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {
    private static final Logger log = LoggerFactory.getLogger(UserInfoServiceImpl.class);

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticatedUserService authenticatedUserService;

    @Override
    @PreAuthorize("isAuthenticated()")
    public UserInfoDTO getUserInfo() {
        User user = authenticatedUserService.getCurentUser();
        UserInfoDTO userDTO = modelMapper.map(user, UserInfoDTO.class);
        return userDTO;
    }

    @Override
    @Transactional
    @PreAuthorize("isAuthenticated()")
    public void updateEmail(EmailDTO emailDTO) {
        User user = authenticatedUserService.getCurentUser();

        user.setEmail(emailDTO.getEmail());
        user.setEmailVerified(false);
        userRepository.save(user);
        log.info("{} email updated.", user.getUsername());
    }

    @Override
    @Transactional
    @PreAuthorize("isAuthenticated()")
    public void updatePassword(PasswordDTO passwordDTO) {
        User user = authenticatedUserService.getCurentUser();

        user.setPassword(passwordEncoder.encode(passwordDTO.getPassword()));
        userRepository.save(user);
        log.info("{} password updated.", user.getUsername());
    }
}
