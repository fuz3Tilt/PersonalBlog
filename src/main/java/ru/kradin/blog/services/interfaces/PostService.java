package ru.kradin.blog.services.interfaces;

import ru.kradin.blog.exceptions.PostNotFoundException;
import ru.kradin.blog.models.Post;

import java.util.List;

public interface PostService {

    public List<Post> getAllPosts();

    public Post getPostById(long id) throws PostNotFoundException;

    public void createPost(Post post);

    public void updatePost(Post post) throws PostNotFoundException;

    public void deletePostById(long id);
}
