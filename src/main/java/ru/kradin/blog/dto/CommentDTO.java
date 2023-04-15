package ru.kradin.blog.dto;

import java.time.LocalDateTime;
import java.util.List;

public class CommentDTO {
    private long id;
    private UserDTO user;
    private String text;
    private long parentPostId;
    private long parentCommentId;
    private List<CommentDTO> replies;
    private List<LikeDTO> likes;
    private boolean deleted;
    private LocalDateTime createdAt;

    public CommentDTO() {
    }

    public CommentDTO(long id, UserDTO user, String text, long parentPostId, long parentCommentId, List<CommentDTO> replies, List<LikeDTO> likes, boolean deleted, LocalDateTime createdAt) {
        this.id = id;
        this.user = user;
        this.text = text;
        this.parentPostId = parentPostId;
        this.parentCommentId = parentCommentId;
        this.replies = replies;
        this.likes = likes;
        this.deleted = deleted;
        this.createdAt = createdAt;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
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

    public List<CommentDTO> getReplies() {
        return replies;
    }

    public void setReplies(List<CommentDTO> replies) {
        this.replies = replies;
    }

    public List<LikeDTO> getLikes() {
        return likes;
    }

    public void setLikes(List<LikeDTO> likes) {
        this.likes = likes;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
