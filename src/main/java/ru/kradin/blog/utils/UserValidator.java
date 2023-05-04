package ru.kradin.blog.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.kradin.blog.dto.UserRegistrationDTO;
import ru.kradin.blog.models.User;
import ru.kradin.blog.repositories.UserRepository;

import java.util.Optional;

@Component
public class UserValidator implements Validator {

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean supports(Class<?> aClass) {
        return UserRegistrationDTO.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        UserRegistrationDTO userRegistrationDTO = (UserRegistrationDTO) o;

        Optional<User> userWithTheSameEmail = userRepository.findByEmail(userRegistrationDTO.getEmail());
        if (userWithTheSameEmail.isPresent())
            errors.rejectValue("email", "", "Email is already taken");

        Optional<User> userWithTheSameUsername = userRepository.findByUsername(userRegistrationDTO.getUsername());
        if (userWithTheSameUsername.isPresent())
            errors.rejectValue("username", "", "Username is already taken");
    }
}
