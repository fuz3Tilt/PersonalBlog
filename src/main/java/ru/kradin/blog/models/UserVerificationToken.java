package ru.kradin.blog.models;

import javax.persistence.*;
import ru.kradin.blog.enums.TokenPurpose;

import java.time.LocalDateTime;
/*
    Entity can use for verifying any data
 */
@Entity
public class UserVerificationToken extends SuperEntity {
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @Column(nullable = false, unique = true)
    private String token;
    @Enumerated(EnumType.STRING)
    @Column(name = "token_purpose", nullable = false)
    private TokenPurpose tokenPurpose;
    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime expiryDate;

    public UserVerificationToken() {
    }

    public UserVerificationToken(long id, User user, String token, TokenPurpose tokenPurpose, LocalDateTime expiryDate) {
        super.setId(id);
        this.user = user;
        this.token = token;
        this.tokenPurpose = tokenPurpose;
        this.expiryDate = expiryDate;
    }

    public long getId() {
        return super.getId();
    }

    public void setId(long id) {
        super.setId(id);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public TokenPurpose getTokenPurpose() {
        return tokenPurpose;
    }

    public void setTokenPurpose(TokenPurpose tokenPurpose) {
        this.tokenPurpose = tokenPurpose;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }
}
