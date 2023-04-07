package ru.kradin.blog.services.interfaces;

import org.springframework.security.core.Authentication;
import ru.kradin.blog.exceptions.EmailAlreadyVerifiedException;
import ru.kradin.blog.exceptions.UserDoesNotHaveEmailException;
import ru.kradin.blog.exceptions.UserVerificationTokenAlreadyExistException;
import ru.kradin.blog.exceptions.UserVerificationTokenNotFoundException;
import ru.kradin.blog.models.additional.EmailInfo;

public interface UserService {

    public void updateEmail(Authentication authentication, String email);

    public EmailInfo getEmailInfo(Authentication authentication);

    public void updatePassword(Authentication authentication, String password);

    public void sendVerificationEmail(Authentication authentication) throws UserDoesNotHaveEmailException, EmailAlreadyVerifiedException, UserVerificationTokenAlreadyExistException;

    public void verifyEmail(String token) throws UserVerificationTokenNotFoundException;

    public void sendPasswordResetEmail(String email);

    public void resetPasswordWithToken(String token, String password) throws UserVerificationTokenNotFoundException;
}
