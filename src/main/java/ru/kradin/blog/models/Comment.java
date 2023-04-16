package ru.kradin.blog.models;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @Column(length = 1000,nullable = false)
    private String text;
    @Column(nullable = false)
    private int depth;
    @ManyToOne
    @JoinColumn(name = "parent_post_id", nullable = false)
    private Post parentPost;
    @ManyToOne
    @JoinColumn(name = "parent_comment_id", nullable = true)
    private Comment parentComment;
    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.MERGE, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Comment> replies;
    @OneToMany(mappedBy = "comment", cascade = CascadeType.MERGE, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Like> likes;
    @Column(nullable = false)
    private boolean deleted;
    @Column(name = "expiry_date", nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

    @PostLoad
    private void onLoad() {
        if (deleted) {
            user.setId(0);
            user.setEmail("none");
            user.setUsername("none");
            user.setPassword("none");
            user.setCreatedAt(LocalDateTime.now());
            text = "none";
        } else {
            user.setPassword("none");
            user.setEmail("none");
            user.setEmailVerified(false);
        }
    }

    public Comment() {
    }

    public Comment(long id, User user, String text, int depth, Post parentPost, Comment parentComment, List<Comment> replies, List<Like> likes, boolean deleted, LocalDateTime createdAt) {
        this.id = id;
        this.user = user;
        this.text = text;
        this.depth = depth;
        this.parentPost = parentPost;
        this.parentComment = parentComment;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public Post getParentPost() {
        return parentPost;
    }

    public void setParentPost(Post parentPost) {
        this.parentPost = parentPost;
    }

    public Comment getParentComment() {
        return parentComment;
    }

    public void setParentComment(Comment parentComment) {
        this.parentComment = parentComment;
    }

    public List<Comment> getReplies() {
        return replies;
    }

    public void setReplies(List<Comment> replies) {
        this.replies = replies;
    }

    public List<Like> getLikes() {
        return likes;
    }

    public void setLikes(List<Like> likes) {
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
