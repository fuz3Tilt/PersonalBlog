package ru.kradin.blog.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class PostCreateDTO {
    @NotBlank(message = "Title cannot be blank.")
    @Size(min = 1, max = 255, message = "Title must be between 1 and 255 characters.")
    private String title;

    @NotBlank(message = "Content cannot be blank.")
    @Size(min = 1, max = 50000, message = "Content must be between 1 and 50000 characters.")
    private String content;

    public PostCreateDTO() {
    }

    public PostCreateDTO(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
