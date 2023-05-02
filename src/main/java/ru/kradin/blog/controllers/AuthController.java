package ru.kradin.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kradin.blog.dto.AuthenticationDTO;
import ru.kradin.blog.dto.UserRegistrationDTO;
import ru.kradin.blog.exceptions.EmailAlreadyVerifiedException;
import ru.kradin.blog.exceptions.UserDoesNotHaveEmailException;
import ru.kradin.blog.exceptions.UserVerificationTokenAlreadyExistException;
import ru.kradin.blog.security.JWTUtil;
import ru.kradin.blog.services.interfaces.RegistrationService;
import ru.kradin.blog.utils.FieldErrorsUtil;
import ru.kradin.blog.utils.UserValidator;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private RegistrationService registrationService;
    @Autowired
    private UserValidator userValidator;
    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/registration")
    public ResponseEntity<?> performRegistration(@RequestBody @Valid UserRegistrationDTO userRegistrationDTO,
                                                 BindingResult bindingResult) {

        userValidator.validate(userRegistrationDTO, bindingResult);

        if (bindingResult.hasErrors())
            return ResponseEntity.badRequest().body(Collections.singletonMap("errors", FieldErrorsUtil.getErrors(bindingResult)));

        try {
            registrationService.register(userRegistrationDTO);
        } catch (EmailAlreadyVerifiedException e) {
            return ResponseEntity.badRequest().body("Email already verified");
        } catch (UserDoesNotHaveEmailException e) {
            return ResponseEntity.badRequest().body("User does not have email");
        } catch (UserVerificationTokenAlreadyExistException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Verification token already exists");
        }

        String token = jwtUtil.generateToken(userRegistrationDTO.getUsername());

        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("token", token));
    }

    @PostMapping("/login")
    public ResponseEntity<?> performLogin(@RequestBody AuthenticationDTO authenticationDTO) {
        UsernamePasswordAuthenticationToken authInputToken =
                new UsernamePasswordAuthenticationToken(authenticationDTO.getUsername(),
                        authenticationDTO.getPassword());

        try {
            authenticationManager.authenticate(authInputToken);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect credentials!");
        }

        String token = jwtUtil.generateToken(authenticationDTO.getUsername());

        return ResponseEntity.ok(Map.of("token", token));
    }
}
