package ru.kradin.blog.dto;

import java.time.LocalDateTime;

public class LikeDTO {
    private long postId;
    private long commentId;
    private UserDTO user;
    private LocalDateTime createdAt;

    public LikeDTO() {
    }

    public LikeDTO(long postId, long commentId, UserDTO userDTO, LocalDateTime createdAt) {
        this.postId = postId;
        this.commentId = commentId;
        this.user = userDTO;
        this.createdAt = createdAt;
    }

    public long getPostId() {
        return postId;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }

    public long getCommentId() {
        return commentId;
    }

    public void setCommentId(long commentId) {
        this.commentId = commentId;
    }

    public UserDTO getUserDTO() {
        return user;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.user = userDTO;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
