package ru.kradin.blog.utlis;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.kradin.blog.dto.UserDTO;
import ru.kradin.blog.dto.UserRegistrationDTO;
import ru.kradin.blog.models.User;

@Component
public class UserValidator implements Validator {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public boolean supports(Class<?> aClass) {
        return UserRegistrationDTO.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        UserRegistrationDTO userRegistrationDTO = (UserRegistrationDTO) o;
        User user = modelMapper.map(userRegistrationDTO,User.class);

        try {
            userDetailsService.loadUserByUsername(user.getUsername());
        } catch (UsernameNotFoundException ignored) {
            return;
        }

        errors.rejectValue("username", "", "Username already exists");
    }
}
