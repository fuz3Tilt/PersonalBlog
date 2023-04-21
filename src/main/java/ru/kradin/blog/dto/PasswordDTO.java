package ru.kradin.blog.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class PasswordDTO {
    @NotBlank(message = "Password cannot be blank")
    @Size(min = 8, max = 255, message = "Password must be between 8 and 255 characters")
    private String password;

    public PasswordDTO() {
    }

    public PasswordDTO(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
