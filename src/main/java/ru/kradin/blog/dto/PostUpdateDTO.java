package ru.kradin.blog.dto;

import javax.validation.constraints.*;

public class PostUpdateDTO {
    @NotNull(message = "ID cannot be null.")
    @Min(value = 1, message = "ID must be greater than or equal to 1.")
    private long id;

    @NotNull(message = "Title cannot be null")
    @NotEmpty(message = "Title cannot be empty")
    @NotBlank(message = "Title cannot be blank.")
    @Size(min = 1, max = 255, message = "Title must be between 1 and 255 characters.")
    private String title;

    @NotNull(message = "Content cannot be null")
    @NotEmpty(message = "Content cannot be empty")
    @NotBlank(message = "Content cannot be blank.")
    @Size(min = 1, max = 50000, message = "Content must be between 1 and 50000 characters.")
    private String content;

    public PostUpdateDTO() {
    }

    public PostUpdateDTO(long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
