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
import ru.kradin.blog.security.JWTUtil;
import ru.kradin.blog.services.interfaces.RegistrationService;
import ru.kradin.blog.utils.FieldErrorsUtil;
import ru.kradin.blog.utils.UserValidator;

import javax.validation.Valid;
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
            return ResponseEntity.badRequest().body(FieldErrorsUtil.getErrors(bindingResult));

        registrationService.register(userRegistrationDTO);

        String token = jwtUtil.generateToken(userRegistrationDTO.getUsername());

        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("JWT-token", token));
    }

    @PostMapping("/login")
    public ResponseEntity<?> performLogin(@RequestBody AuthenticationDTO authenticationDTO) {
        UsernamePasswordAuthenticationToken authInputToken =
                new UsernamePasswordAuthenticationToken(authenticationDTO.getUsername(),
                        authenticationDTO.getPassword());

        try {
            authenticationManager.authenticate(authInputToken);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Incorrect credentials!"));
        }

        String token = jwtUtil.generateToken(authenticationDTO.getUsername());

        return ResponseEntity.ok(Map.of("JWT-token", token));
    }
}
