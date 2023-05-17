package ru.kradin.blog.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.kradin.blog.models.User;
import ru.kradin.blog.repositories.UserRepository;
import ru.kradin.blog.services.interfaces.AuthenticatedUserService;

import java.util.Optional;

/*
    Use only for other services
 */
@Service
public class AuthenticatedUserServiceImpl implements AuthenticatedUserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public User getCurentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        Optional<User> user = userRepository.findByUsername(username);

        return user.get();
    }

}
