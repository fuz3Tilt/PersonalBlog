package ru.kradin.blog.dto;

public class CommentCreateDTO {
    private String text;
    private long parentPostId;
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
