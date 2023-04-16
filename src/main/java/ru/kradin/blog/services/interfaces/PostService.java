package ru.kradin.blog.services.interfaces;

import ru.kradin.blog.dto.PostCreateDTO;
import ru.kradin.blog.dto.PostDTO;
import ru.kradin.blog.dto.PostUpdateDTO;
import ru.kradin.blog.exceptions.PostNotFoundException;

import java.util.List;

public interface PostService {

    public List<PostDTO> getAllPosts();

    public PostDTO getPostById(long id) throws PostNotFoundException;

    public void createPost(PostCreateDTO postCreateDTO);

    public void updatePost(PostUpdateDTO postUpdateDTO) throws PostNotFoundException;

    public void deletePostById(long id);
}
