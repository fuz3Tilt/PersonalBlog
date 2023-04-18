package ru.kradin.blog.services.interfaces;

import ru.kradin.blog.dto.UserRegistrationDTO;
import ru.kradin.blog.exceptions.EmailAlreadyVerifiedException;
import ru.kradin.blog.exceptions.UserDoesNotHaveEmailException;
import ru.kradin.blog.exceptions.UserVerificationTokenAlreadyExistException;

public interface RegistrationService {
    public void register(UserRegistrationDTO userRegistrationDTO) throws EmailAlreadyVerifiedException, UserDoesNotHaveEmailException, UserVerificationTokenAlreadyExistException;
}
