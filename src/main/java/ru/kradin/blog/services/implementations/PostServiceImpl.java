package ru.kradin.blog.services.implementations;

import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
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

    @Override
    public List<Post> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        return posts;
    }

    @Override
    public Post getPostById(long id) throws PostNotFoundException {
        Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException());
        return post;
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Transactional
    public void createPost(Post post) {
        post.setCreatedAt(LocalDateTime.now());
        postRepository.save(post);
        log.info("New post with id {} created."+post.getId());
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Transactional
    public void updatePost(Post post) throws PostNotFoundException {
        Post existingPost = postRepository.findById(post.getId()).orElseThrow(() -> new PostNotFoundException());
        existingPost.setTitle(post.getTitle());
        existingPost.setContent(post.getContent());
        postRepository.save(existingPost);
        log.info("Post with id {} updated."+post.getId());
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Transactional
    public void deletePostById(long id) {
        postRepository.deleteById(id);
        log.info("Post with id {} deleted."+id);
    }
}
