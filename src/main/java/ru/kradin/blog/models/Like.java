package ru.kradin.blog.models;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_like")
public class Like extends SuperEntity {
    @ManyToOne
    @JoinColumn(name = "post_id", nullable = true)
    private Post post;
    @ManyToOne
    @JoinColumn(name = "comment_id", nullable = true)
    private Comment comment;
    @ManyToOne
    @JoinColumn(name = "usr_id", nullable = false)
    private User user;
    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

    public Like() {
    }

    public Like(long id, Post post, Comment comment, User user, LocalDateTime createdAt) {
        super.setId(id);
        this.post = post;
        this.comment = comment;
        this.user = user;
        this.createdAt = createdAt;
    }

    public long getId() {
        return super.getId();
    }

    public void setId(long id) {
        super.setId(id);
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
