package ru.kradin.blog.services.interfaces;

public interface UserVerificationTokenService {
    public void removeExpiredTokens();
}
