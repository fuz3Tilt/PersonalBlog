package ru.kradin.blog.models;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Post extends SuperEntity {
    @Column(nullable = false)
    private String title;
    @Column(length = 50000,nullable = false)
    private String content;
    @OneToMany(mappedBy = "parentPost", cascade = CascadeType.MERGE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Comment> comments;
    @OneToMany(mappedBy = "post", cascade = CascadeType.MERGE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Like> likes;
    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

    public Post() {
    }

    public Post(long id, String title, String content, List<Comment> comments, List<Like> likes, LocalDateTime createdAt) {
        super.setId(id);
        this.title = title;
        this.content = content;
        this.comments = comments;
        this.likes = likes;
        this.createdAt = createdAt;
    }

    public long getId() {
        return super.getId();
    }

    public void setId(long id) {
        super.setId(id);
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

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Like> getLikes() {
        return likes;
    }

    public void setLikes(List<Like> likes) {
        this.likes = likes;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
