package ru.kradin.blog.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import ru.kradin.blog.models.User;
import ru.kradin.blog.repositories.UserRepository;
import ru.kradin.blog.services.interfaces.UserAuthenticationService;

import java.util.Optional;

public class UserAuthenticationServiceImpl implements UserAuthenticationService {

    @Autowired
    UserRepository userRepository;

    @Override
    public User getUserFromAuthentication(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        Optional<User> user = userRepository.findByUsername(username);

        return user.get();
    }

}
