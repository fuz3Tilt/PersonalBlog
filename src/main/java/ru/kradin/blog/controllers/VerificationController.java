package ru.kradin.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kradin.blog.dto.EmailDTO;
import ru.kradin.blog.dto.PasswordDTO;
import ru.kradin.blog.exceptions.UserVerificationTokenNotFoundException;
import ru.kradin.blog.services.interfaces.UserVerificationService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/verification")
public class VerificationController {

    @Autowired
    UserVerificationService userVerificationService;

    @PostMapping("/email")
    public ResponseEntity< ? > verifyEmail(@RequestParam("token") String token) throws UserVerificationTokenNotFoundException {
        userVerificationService.verifyEmail(token);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/password-reset")
    public ResponseEntity<?> sendPasswordResetEmail(@RequestBody @Valid EmailDTO emailDTO,
                                                    BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return new ResponseEntity<>(bindingResult.getAllErrors(), HttpStatus.BAD_REQUEST);
        userVerificationService.sendPasswordResetEmail(emailDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping ResponseEntity<?> resetPassword(@RequestParam("token") String token,
                                                  @RequestBody @Valid PasswordDTO passwordDTO,
                                                  BindingResult bindingResult) throws UserVerificationTokenNotFoundException {
        if (bindingResult.hasErrors())
            return new ResponseEntity<>(bindingResult.getAllErrors(),HttpStatus.BAD_REQUEST);
        userVerificationService.resetPasswordWithToken(token,passwordDTO);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
