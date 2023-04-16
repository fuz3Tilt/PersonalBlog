package ru.kradin.blog.dto;

import java.time.LocalDateTime;
import java.util.List;

public class CommentDTO {
    private long id;
    private UserDTO user;
    private String text;
    private int depth;
    private PostDTO parentPost;
    private CommentDTO parentComment;
    private List<CommentDTO> replies;
    private List<LikeDTO> likes;
    private boolean deleted;
    private LocalDateTime createdAt;

    public CommentDTO() {
    }

    public CommentDTO(long id, UserDTO user, String text, int depth, PostDTO parentPost, CommentDTO parentComment, List<CommentDTO> replies, List<LikeDTO> likes, boolean deleted, LocalDateTime createdAt) {
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

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public PostDTO getParentPost() {
        return parentPost;
    }

    public void setParentPost(PostDTO parentPost) {
        this.parentPost = parentPost;
    }

    public CommentDTO getParentComment() {
        return parentComment;
    }

    public void setParentComment(CommentDTO parentComment) {
        this.parentComment = parentComment;
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
