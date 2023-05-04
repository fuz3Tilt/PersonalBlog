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
import ru.kradin.blog.dto.PostUpdateCreateDTO;
import ru.kradin.blog.dto.PostDTO;
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
    public PostDTO createPost(PostUpdateCreateDTO postUpdateCreateDTO) {
        Post post = new Post();
        post.setTitle(postUpdateCreateDTO.getTitle());
        post.setContent(postUpdateCreateDTO.getContent());
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
    public PostDTO updatePost(long id, PostUpdateCreateDTO postUpdateCreateDTO) throws PostNotFoundException {
        Post existingPost = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException());
        existingPost.setTitle(postUpdateCreateDTO.getTitle());
        existingPost.setContent(postUpdateCreateDTO.getContent());
        existingPost = postRepository.save(existingPost);
        PostDTO postDTO = modelMapper.map(existingPost,PostDTO.class);
        log.info("Post with id {} updated."+existingPost.getId());
        return postDTO;
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Caching(evict = {
            @CacheEvict(value = "posts", allEntries = true),
            @CacheEvict(value = "post", key = "#id"),
            @CacheEvict(value = "comments", key = "#id"),
            @CacheEvict(value = "likes", key = "#id")
    })
    @Transactional
    public void deletePostById(long id) throws PostNotFoundException {
        Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException());
        postRepository.delete(post);
        log.info("Post with id {} deleted."+id);
    }
}
