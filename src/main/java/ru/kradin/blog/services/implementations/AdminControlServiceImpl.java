package ru.kradin.blog.services.implementations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kradin.blog.enums.Role;
import ru.kradin.blog.models.User;
import ru.kradin.blog.repositories.UserRepository;
import ru.kradin.blog.services.interfaces.AdminControlService;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AdminControlServiceImpl implements AdminControlService {

    private static final Logger log = LoggerFactory.getLogger(AdminControlServiceImpl.class);

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    @Override
    @Scheduled(fixedRate = 1000*60*60*24, initialDelay = 1000)
    public void createAdminAccountIfNotExist() {
        List<User> userList = userRepository.findByRole(Role.ROLE_ADMIN);
        if(userList.isEmpty()){
            User admin = new User();
            admin.setUsername("admin");
            admin.setEmail(null);
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
