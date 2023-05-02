package ru.kradin.blog.dto;

import javax.validation.constraints.*;

public class CommentCreateDTO {
    @NotNull(message = "Text cannot be null")
    @NotEmpty(message = "Text cannot be empty")
    @NotBlank(message = "Text cannot be blank")
    @Size(min = 1, max = 1000, message = "Text must be between 1 and 1000 characters")
    private String text;
    @Positive(message = "Parent post ID must be greater than 0")
    private long parentPostId;
    @PositiveOrZero(message = "Parent comment ID must be greater than or equal to 0")
    private long parentCommentId;

    public CommentCreateDTO() {
    }

    public CommentCreateDTO(String text, long parentPostId, long parentCommentId) {
        this.text = text;
        this.parentPostId = parentPostId;
        this.parentCommentId = parentCommentId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getParentPostId() {
        return parentPostId;
    }

    public void setParentPostId(long parentPostId) {
        this.parentPostId = parentPostId;
    }

    public long getParentCommentId() {
        return parentCommentId;
    }

    public void setParentCommentId(long parentCommentId) {
        this.parentCommentId = parentCommentId;
    }
}
