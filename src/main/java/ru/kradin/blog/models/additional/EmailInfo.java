package ru.kradin.blog.models.additional;

public class EmailInfo {

    private String email;

    private boolean emailVerified;

    public EmailInfo(String email, boolean emailVerified) {
        this.email = email;
        this.emailVerified = emailVerified;
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
}

