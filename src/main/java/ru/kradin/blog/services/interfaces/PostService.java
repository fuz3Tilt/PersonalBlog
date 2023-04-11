package ru.kradin.blog.services.interfaces;

import ru.kradin.blog.models.Post;

import java.util.List;

public interface PostService {

    public List<Post> getAllPosts();

    public Post getPostById(long id);

    public void createPost(Post post);

    public void updatePost(Post post);

    public void deletePostById(long postId);
}
