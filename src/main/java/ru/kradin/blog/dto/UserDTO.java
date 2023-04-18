package ru.kradin.blog.dto;

import ru.kradin.blog.enums.Role;

import java.time.LocalDateTime;

public class UserDTO {
    private long id;
    private String username;

    public UserDTO() {
    }

    public UserDTO(long id, String username) {
        this.id = id;
        this.username = username;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
