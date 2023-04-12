package ru.kradin.blog.services.interfaces;

import ru.kradin.blog.exceptions.EmailAlreadyVerifiedException;
import ru.kradin.blog.exceptions.UserDoesNotHaveEmailException;
import ru.kradin.blog.exceptions.UserVerificationTokenAlreadyExistException;
import ru.kradin.blog.models.User;

public interface RegistrationService {
    public void register(User user) throws EmailAlreadyVerifiedException, UserDoesNotHaveEmailException, UserVerificationTokenAlreadyExistException;
}
