package ru.kradin.blog.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    @ManyToOne
    @JoinColumn(name = "post_id", nullable = true)
    private Post post;
    @ManyToOne
    @JoinColumn(name = "comment_id", nullable = true)
    private Comment comment;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

    @PostLoad
    private void onLoad(){
        user.setPassword(null);
        user.setEmail(null);
        user.setEmailVerified(false);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
