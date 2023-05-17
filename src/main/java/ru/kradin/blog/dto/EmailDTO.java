package ru.kradin.blog.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
/*
    Uses for updating user email
 */
public class EmailDTO {
    @NotNull(message = "Email cannot be null")
    @Email(message = "Email should be valid")
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
