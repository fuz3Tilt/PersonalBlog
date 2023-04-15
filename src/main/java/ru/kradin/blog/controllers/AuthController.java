package ru.kradin.blog.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kradin.blog.dto.AuthenticationDTO;
import ru.kradin.blog.dto.UserDTO;
import ru.kradin.blog.dto.UserRegistrationDTO;
import ru.kradin.blog.exceptions.EmailAlreadyVerifiedException;
import ru.kradin.blog.exceptions.UserDoesNotHaveEmailException;
import ru.kradin.blog.exceptions.UserVerificationTokenAlreadyExistException;
import ru.kradin.blog.models.User;
import ru.kradin.blog.security.JWTUtil;
import ru.kradin.blog.services.interfaces.RegistrationService;
import ru.kradin.blog.utlis.UserValidator;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private RegistrationService registrationService;
    @Autowired
    private UserValidator userValidator;
    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/registration")
    public Map<String, String> performRegistration(@RequestBody @Valid UserRegistrationDTO userRegistrationDTO,
                                                   BindingResult bindingResult) {

        userValidator.validate(userRegistrationDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            return Map.of("message", "Ошибка!");
        }

        try {
            registrationService.register(userRegistrationDTO);
        } catch (EmailAlreadyVerifiedException e) {
            return Map.of("message", "EmailAlreadyVerifiedException");
        } catch (UserDoesNotHaveEmailException e) {
            return Map.of("message", "UserDoesNotHaveEmailException");
        } catch (UserVerificationTokenAlreadyExistException e) {
            return Map.of("message", "UserVerificationTokenAlreadyExistException");
        }

        String token = jwtUtil.generateToken(userRegistrationDTO.getUsername());
        return Map.of("jwt-token", token);
    }

    @PostMapping("/login")
    public Map<String, String> performLogin(@RequestBody AuthenticationDTO authenticationDTO) {
        UsernamePasswordAuthenticationToken authInputToken =
                new UsernamePasswordAuthenticationToken(authenticationDTO.getUsername(),
                        authenticationDTO.getPassword());

        try {
            authenticationManager.authenticate(authInputToken);
        } catch (BadCredentialsException e) {
            return Map.of("message", "Incorrect credentials!");
        }

        String token = jwtUtil.generateToken(authenticationDTO.getUsername());
        return Map.of("jwt-token", token);
    }
}
