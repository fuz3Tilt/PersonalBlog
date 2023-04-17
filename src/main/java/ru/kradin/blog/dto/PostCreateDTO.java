package ru.kradin.blog.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class PostCreateDTO {
    @NotEmpty
    @Size(min = 1, max = 255)
    private String title;
    @NotEmpty
    @Size(min = 1, max = 50000)
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
