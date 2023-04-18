package ru.kradin.blog.services.implementations;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import ru.kradin.blog.dto.*;
import ru.kradin.blog.enums.Role;
import ru.kradin.blog.exceptions.CommentNotFoundException;
import ru.kradin.blog.exceptions.PostNotFoundException;
import ru.kradin.blog.models.User;
import ru.kradin.blog.repositories.LikeRepository;
import ru.kradin.blog.repositories.UserRepository;
import ru.kradin.blog.services.interfaces.CommentService;
import ru.kradin.blog.services.interfaces.LikeService;
import ru.kradin.blog.services.interfaces.PostService;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class LikeServiceImplTest {

    @Autowired
    private LikeService likeService;

    @Autowired
    private PostService postService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Test
    public void testGetPostLikesByPostId() throws PostNotFoundException, CommentNotFoundException {
        User user = new User();
        user.setEmail("test@mail.ru");
        user.setUsername("testuser");
        user.setPassword(passwordEncoder.encode("test"));
        user.setRole(Role.ROLE_ADMIN);
        user.setAccountNonLocked(true);
        user.setEmailVerified(true);
        user.setCreatedAt(LocalDateTime.now());
        user = userRepository.save(user);

        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken("testuser", "test");
        Authentication authentication = authenticationManager.authenticate(authRequest);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        PostCreateDTO postCreateDTO = new PostCreateDTO();
        postCreateDTO.setTitle("Test Post");
        postCreateDTO.setContent("Test Content");
        PostDTO savedPost = postService.createPost(postCreateDTO);

        likeService.togglePostLike(savedPost.getId());

        List<LikeDTO> postLikes = likeService.getPostLikesByPostId(savedPost.getId());
        assertEquals(1, postLikes.size());
        LikeDTO postLike = postLikes.get(0);
        assertEquals(user.getId(), postLike.getUser().getId());
        assertEquals(savedPost.getId(), postLike.getPost().getId());

        CommentCreateDTO commentCreateDTO = new CommentCreateDTO();
        commentCreateDTO.setParentPostId(savedPost.getId());
        commentCreateDTO.setText("Test Comment");
        commentService.addCommentToPost(commentCreateDTO);
        List<CommentDTO> postComments = commentService.getPostCommentsByPostId(savedPost.getId());
        assertEquals(1,postComments.size());
        CommentDTO savedComment = postComments.get(0);
        likeService.toggleCommentLike(savedComment.getId());

        postComments = commentService.getPostCommentsByPostId(savedPost.getId());
        assertEquals(1,postComments.size());
        savedComment = postComments.get(0);
        List<LikeDTO> commentLikes = savedComment.getLikes();
        assertEquals(1, commentLikes.size());
        LikeDTO commentLike = commentLikes.get(0);
        assertEquals(user.getId(), commentLike.getUser().getId());
        assertEquals(savedComment.getId(), commentLike.getComment().getId());
    }
}