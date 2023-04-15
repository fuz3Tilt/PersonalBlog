package ru.kradin.blog.dto;

import java.time.LocalDateTime;
import java.util.List;

public class PostDTO {
    private long id;
    private String title;
    private String content;
    private List<CommentDTO> comments;
    private List<LikeDTO> likes;
    private LocalDateTime createdAt;

    public PostDTO() {
    }

    public PostDTO(long id, String title, String content, List<CommentDTO> comments, List<LikeDTO> likes, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.comments = comments;
        this.likes = likes;
        this.createdAt = createdAt;
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

    public List<CommentDTO> getComments() {
        return comments;
    }

    public void setComments(List<CommentDTO> comments) {
        this.comments = comments;
    }

    public List<LikeDTO> getLikes() {
        return likes;
    }

    public void setLikes(List<LikeDTO> likes) {
        this.likes = likes;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
