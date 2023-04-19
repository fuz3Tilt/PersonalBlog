package ru.kradin.blog.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

public class EmailDTO {
    @NotNull
    @Email
    private String email;

    public EmailDTO() {
    }

    public EmailDTO(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
