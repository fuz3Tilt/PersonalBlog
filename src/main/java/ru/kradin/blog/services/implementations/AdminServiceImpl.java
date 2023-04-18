package ru.kradin.blog.services.implementations;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kradin.blog.dto.UserInfoDTO;
import ru.kradin.blog.enums.Role;
import ru.kradin.blog.exceptions.CommentNotFoundException;
import ru.kradin.blog.models.Comment;
import ru.kradin.blog.models.User;
import ru.kradin.blog.repositories.CommentRepository;
import ru.kradin.blog.repositories.UserRepository;
import ru.kradin.blog.services.interfaces.AdminService;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    private static final Logger log = LoggerFactory.getLogger(AdminServiceImpl.class);

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<UserInfoDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserInfoDTO> userDTOS = modelMapper.map(users, new TypeToken<List<UserInfoDTO>>() {}.getType());
        return userDTOS;
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<UserInfoDTO> getNonBannedUsers() {
        List<User> nonBannedUsers = userRepository.findByAccountNonLocked(true);
        List<UserInfoDTO> nonBannedUserDTOS = modelMapper.map(nonBannedUsers, new TypeToken<List<UserInfoDTO>>() {}.getType());
        return nonBannedUserDTOS;
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<UserInfoDTO> getBannedUsers() {
        List<User> bannedUsers = userRepository.findByAccountNonLocked(false);
        List<UserInfoDTO> bannedUserDTOS = modelMapper.map(bannedUsers, new TypeToken<List<UserInfoDTO>>() {}.getType());
        return bannedUserDTOS;
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteComment(long id) throws CommentNotFoundException {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new CommentNotFoundException());
        comment.setDeleted(true);
        commentRepository.save(comment);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void toggleUserBan(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if (user.isAccountNonLocked()) {
            user.setAccountNonLocked(false);
        } else {
            user.setAccountNonLocked(true);
        }
        userRepository.save(user);
    }

    @Override
    @Transactional
    @Scheduled(fixedRate = 1000*60*60*24, initialDelay = 0)
    public void createAdminAccountIfNotExist() {
        List<User> userList = userRepository.findByRole(Role.ROLE_ADMIN);
        if(userList.isEmpty()){
            User admin = new User();
            admin.setUsername("admin");
            admin.setEmail("");
            admin.setEmailVerified(false);
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setRole(Role.ROLE_ADMIN);
            admin.setAccountNonLocked(true);
            admin.setCreatedAt(LocalDateTime.now());
            userRepository.save(admin);
            log.info("Created admin account");
        }
    }
}
