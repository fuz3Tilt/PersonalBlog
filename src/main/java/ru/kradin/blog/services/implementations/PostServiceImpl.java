package ru.kradin.blog.services.implementations;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
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
    public List<PostDTO> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        List<PostDTO> postDTOS = modelMapper.map(posts, new TypeToken<List<PostDTO>>() {}.getType());
        return postDTOS;
    }

    @Override
    public PostDTO getPostById(long id) throws PostNotFoundException {
        Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException());
        PostDTO postDTO = modelMapper.map(post, PostDTO.class);
        return postDTO;
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Transactional
    public void createPost(PostDTO postDTO) {
        Post post = modelMapper.map(postDTO, Post.class);
        post.setCreatedAt(LocalDateTime.now());
        postRepository.save(post);
        log.info("New post with id {} created."+post.getId());
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Transactional
    public void updatePost(PostDTO postDTO) throws PostNotFoundException {
        Post existingPost = postRepository.findById(postDTO.getId()).orElseThrow(() -> new PostNotFoundException());
        existingPost.setTitle(postDTO.getTitle());
        existingPost.setContent(postDTO.getContent());
        postRepository.save(existingPost);
        log.info("Post with id {} updated."+existingPost.getId());
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Transactional
    public void deletePostById(long id) {
        postRepository.deleteById(id);
        log.info("Post with id {} deleted."+id);
    }
}
