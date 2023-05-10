package ru.kradin.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kradin.blog.dto.EmailDTO;
import ru.kradin.blog.dto.PasswordDTO;
import ru.kradin.blog.exceptions.UserVerificationTokenNotFoundException;
import ru.kradin.blog.services.interfaces.UserVerificationService;
import ru.kradin.blog.utils.FieldErrorsUtil;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/verification")
public class VerificationController {

    @Autowired
    UserVerificationService userVerificationService;

    @PostMapping("/email")
    public ResponseEntity< ? > verifyEmail(@RequestParam("token") String token) throws UserVerificationTokenNotFoundException {
        userVerificationService.verifyEmail(token);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/password-reset")
    public ResponseEntity<?> sendPasswordResetEmail(@RequestBody @Valid EmailDTO emailDTO,
                                                    BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return ResponseEntity.badRequest().body(FieldErrorsUtil.getErrors(bindingResult));

        userVerificationService.sendPasswordResetEmail(emailDTO);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/password-reset")
    public ResponseEntity<?> resetPassword(@RequestParam("token") String token,
                                                  @RequestBody @Valid PasswordDTO passwordDTO,
                                                  BindingResult bindingResult) throws UserVerificationTokenNotFoundException {
        if (bindingResult.hasErrors())
            return ResponseEntity.badRequest().body(FieldErrorsUtil.getErrors(bindingResult));

        userVerificationService.resetPasswordWithToken(token,passwordDTO);

        return ResponseEntity.ok().build();
    }
}
