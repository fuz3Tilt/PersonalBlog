package ru.kradin.blog.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.kradin.blog.models.User;
import ru.kradin.blog.repositories.UserRepository;
import ru.kradin.blog.services.interfaces.AuthenticatedUserService;

import java.util.Optional;

@Service //Only for services
public class AuthenticatedUserServiceImpl implements AuthenticatedUserService {

    @Autowired
    UserRepository userRepository;

    @Override //Only for services
    public User getCurentUser(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        Optional<User> user = userRepository.findByUsername(username);

        return user.get();
    }

}