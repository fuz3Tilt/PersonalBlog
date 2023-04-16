package ru.kradin.blog.dto;

import java.time.LocalDateTime;

public class LikeDTO {
    private PostDTO post;
    private CommentDTO comment;
    private UserDTO user;
    private LocalDateTime createdAt;

    public LikeDTO() {
    }

    public LikeDTO(PostDTO post, CommentDTO comment, UserDTO user, LocalDateTime createdAt) {
        this.post = post;
        this.comment = comment;
        this.user = user;
        this.createdAt = createdAt;
    }

    public PostDTO getPost() {
        return post;
    }

    public void setPost(PostDTO post) {
        this.post = post;
    }

    public CommentDTO getComment() {
        return comment;
    }

    public void setComment(CommentDTO comment) {
        this.comment = comment;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
