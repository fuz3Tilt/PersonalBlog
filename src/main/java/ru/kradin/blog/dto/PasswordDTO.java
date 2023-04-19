package ru.kradin.blog.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PasswordDTO {
    @NotNull
    @Size(min = 8, max = 255)
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
