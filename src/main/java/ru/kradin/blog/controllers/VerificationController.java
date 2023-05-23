package ru.kradin.blog.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kradin.blog.dto.EmailDTO;
import ru.kradin.blog.dto.PasswordDTO;
import ru.kradin.blog.exceptions.UserVerificationTokenNotFoundException;
import ru.kradin.blog.responses.ValidationErrorResponse;
import ru.kradin.blog.services.interfaces.UserVerificationService;
import ru.kradin.blog.utils.FieldErrorsUtil;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/verification")
@Api(value = "Verification API", tags = {"Verification"})
public class VerificationController {

    @Autowired
    UserVerificationService userVerificationService;

    @PostMapping("/email")
    @ApiOperation(value = "Verify user email")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Email successfully verified"),
            @ApiResponse(code = 404, message = "The verification token was not found.")
    })
    public ResponseEntity< ? > verifyEmail(@RequestParam("token") String token) throws UserVerificationTokenNotFoundException {
        userVerificationService.verifyEmail(token);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/password-reset")
    @ApiOperation(value = "Send password reset email")
    @ApiResponse(code = 200, message = "If a user with this email exists, a password reset email will be sent successfully")
    public ResponseEntity<?> sendPasswordResetEmail(@RequestBody EmailDTO emailDTO){
        userVerificationService.sendPasswordResetEmail(emailDTO);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/password-reset")
    @ApiOperation(value = "Reset user password")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Password successfully reset"),
            @ApiResponse(code = 400, message = "Validation error", response = ValidationErrorResponse.class),
            @ApiResponse(code = 404, message = "The verification token was not found.")
    })
    public ResponseEntity<?> resetPassword(@RequestParam("token") String token,
                                                  @RequestBody @Valid PasswordDTO passwordDTO,
                                                  BindingResult bindingResult) throws UserVerificationTokenNotFoundException {
        if (bindingResult.hasErrors())
            return ResponseEntity.badRequest().body(FieldErrorsUtil.getErrors(bindingResult));

        userVerificationService.resetPasswordWithToken(token,passwordDTO);

        return ResponseEntity.ok().build();
    }
}
