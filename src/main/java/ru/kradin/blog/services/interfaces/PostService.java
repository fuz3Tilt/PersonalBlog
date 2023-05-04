package ru.kradin.blog.services.interfaces;

import ru.kradin.blog.dto.PostUpdateCreateDTO;
import ru.kradin.blog.dto.PostDTO;
import ru.kradin.blog.exceptions.PostNotFoundException;

import java.util.List;

public interface PostService {

    public List<PostDTO> getAllPosts();

    public PostDTO getPostById(long id) throws PostNotFoundException;

    public PostDTO createPost(PostUpdateCreateDTO postUpdateCreateDTO);

    public PostDTO updatePost(long id, PostUpdateCreateDTO postUpdateCreateDTO) throws PostNotFoundException;

    public void deletePostById(long id) throws PostNotFoundException;
}
