package ru.kradin.blog.services.interfaces;

import ru.kradin.blog.dto.EmailDTO;
import ru.kradin.blog.dto.PasswordDTO;
import ru.kradin.blog.exceptions.EmailAlreadyVerifiedException;
import ru.kradin.blog.exceptions.UserDoesNotHaveEmailException;
import ru.kradin.blog.exceptions.UserVerificationTokenAlreadyExistException;
import ru.kradin.blog.exceptions.UserVerificationTokenNotFoundException;
import ru.kradin.blog.models.User;

public interface UserVerificationService {

    public void sendVerificationEmail() throws UserDoesNotHaveEmailException, EmailAlreadyVerifiedException, UserVerificationTokenAlreadyExistException;

    public void sendVerificationEmail(User user) throws EmailAlreadyVerifiedException, UserDoesNotHaveEmailException, UserVerificationTokenAlreadyExistException;

    public void verifyEmail(String token) throws UserVerificationTokenNotFoundException;

    public void sendPasswordResetEmail(EmailDTO emailDTO);

    public void resetPasswordWithToken(String token, PasswordDTO passwordDTO) throws UserVerificationTokenNotFoundException;
}
