package ru.kradin.blog.services.implementations;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import ru.kradin.blog.dto.PostCreateDTO;
import ru.kradin.blog.dto.PostDTO;
import ru.kradin.blog.dto.PostUpdateDTO;
import ru.kradin.blog.exceptions.PostNotFoundException;
import ru.kradin.blog.models.Post;
import ru.kradin.blog.repositories.PostRepository;
import ru.kradin.blog.services.interfaces.PostService;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    private static final Logger log = LoggerFactory.getLogger(PostServiceImpl.class);

    @Autowired
    PostRepository postRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Cacheable(value = "posts")
    public List<PostDTO> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        List<PostDTO> postDTOS = modelMapper.map(posts, new TypeToken<List<PostDTO>>() {}.getType());
        return postDTOS;
    }

    @Override
    @Cacheable(value = "post", key = "#id")
    public PostDTO getPostById(long id) throws PostNotFoundException {
        Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException());
        PostDTO postDTO = modelMapper.map(post, PostDTO.class);
        return postDTO;
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Caching(put = {@CachePut(value = "post", key = "#result.id")},
            evict = {@CacheEvict(value = "posts", allEntries = true)})
    @Transactional
    public PostDTO createPost(PostCreateDTO postCreateDTO) {
        Post post = new Post();
        post.setTitle(postCreateDTO.getTitle());
        post.setContent(postCreateDTO.getContent());
        post.setCreatedAt(LocalDateTime.now());
        post = postRepository.save(post);
        PostDTO postDTO = modelMapper.map(post,PostDTO.class);
        log.info("New post with id {} created."+post.getId());
        return postDTO;
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Caching(put = {@CachePut(value = "post", key = "#result.id")},
            evict = {@CacheEvict(value = "posts", allEntries = true)})
    @Transactional
    public PostDTO updatePost(PostUpdateDTO postUpdateDTO) throws PostNotFoundException {
        Post existingPost = postRepository.findById(postUpdateDTO.getId()).orElseThrow(() -> new PostNotFoundException());
        existingPost.setTitle(postUpdateDTO.getTitle());
        existingPost.setContent(postUpdateDTO.getContent());
        existingPost = postRepository.save(existingPost);
        PostDTO postDTO = modelMapper.map(existingPost,PostDTO.class);
        log.info("Post with id {} updated."+existingPost.getId());
        return postDTO;
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Caching(evict = {
            @CacheEvict(value = "posts", allEntries = true),
            @CacheEvict(value = "post", key = "#id")
    })
    @Transactional
    public void deletePostById(long id) {
        postRepository.deleteById(id);
        log.info("Post with id {} deleted."+id);
    }
}
