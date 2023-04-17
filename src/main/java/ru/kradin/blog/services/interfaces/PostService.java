package ru.kradin.blog.services.interfaces;

import ru.kradin.blog.dto.PostCreateDTO;
import ru.kradin.blog.dto.PostDTO;
import ru.kradin.blog.dto.PostUpdateDTO;
import ru.kradin.blog.exceptions.PostNotFoundException;

import java.util.List;

public interface PostService {

    public List<PostDTO> getAllPosts();

    public PostDTO getPostById(long id) throws PostNotFoundException;

    public PostDTO createPost(PostCreateDTO postCreateDTO);

    public PostDTO updatePost(PostUpdateDTO postUpdateDTO) throws PostNotFoundException;

    public void deletePostById(long id);
}
