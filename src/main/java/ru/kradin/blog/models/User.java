package ru.kradin.blog.models;

import javax.persistence.*;
import ru.kradin.blog.enums.Role;

import java.time.LocalDateTime;

@Entity
@Table(name = "usr")
public class User extends SuperEntity {
    @Column(unique = true, nullable = false)
    private String email;
    @Column(name = "email_verified", nullable = false)
    private boolean emailVerified = false;
    @Column(unique = true, nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(name = "account_non_locked", nullable = false)
    private boolean accountNonLocked;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;
    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

    public User() {
    }

    public User(long id, String email, boolean emailVerified, String username, String password, boolean accountNonLocked, Role role, LocalDateTime createdAt) {
        super.setId(id);
        this.email = email;
        this.emailVerified = emailVerified;
        this.username = username;
        this.password = password;
        this.accountNonLocked = accountNonLocked;
        this.role = role;
        this.createdAt = createdAt;
    }

    public long getId() {
        return super.getId();
    }

    public void setId(long id) {
        super.setId(id);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
