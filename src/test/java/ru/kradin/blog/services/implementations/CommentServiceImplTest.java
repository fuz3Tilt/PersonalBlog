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
import ru.kradin.blog.dto.CommentCreateDTO;
import ru.kradin.blog.dto.CommentDTO;
import ru.kradin.blog.dto.PostCreateDTO;
import ru.kradin.blog.dto.PostDTO;
import ru.kradin.blog.enums.Role;
import ru.kradin.blog.exceptions.CommentNotFoundException;
import ru.kradin.blog.exceptions.PostNotFoundException;
import ru.kradin.blog.models.Comment;
import ru.kradin.blog.models.User;
import ru.kradin.blog.repositories.CommentRepository;
import ru.kradin.blog.repositories.UserRepository;
import ru.kradin.blog.services.interfaces.CommentService;
import ru.kradin.blog.services.interfaces.PostService;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class CommentServiceImplTest {

    @Autowired
    private CommentService commentService;

    @Autowired
    private PostService postService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CommentRepository commentRepository;

    @Test
    public void commentServiceTest() throws PostNotFoundException, CommentNotFoundException {
        User user = new User();
        user.setEmail("some@mail.ru");
        user.setUsername("test");
        user.setPassword(passwordEncoder.encode("test"));
        user.setRole(Role.ROLE_ADMIN);
        user.setAccountNonLocked(true);
        user.setEmailVerified(true);
        user.setCreatedAt(LocalDateTime.now());
        user = userRepository.save(user);
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken("test", "test");
        Authentication authentication = authenticationManager.authenticate(authRequest);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        PostCreateDTO postCreateDTO = new PostCreateDTO("title","content");
        PostDTO postDTO = postService.createPost(postCreateDTO);

        CommentCreateDTO commentCreateDTO1 = new CommentCreateDTO("text1", postDTO.getId(),0);
        CommentCreateDTO commentCreateDTO2 = new CommentCreateDTO("text2", postDTO.getId(),0);
        CommentCreateDTO commentCreateDTO3 = new CommentCreateDTO("text3", postDTO.getId(),0);
        commentService.addComment(commentCreateDTO1);
        commentService.addComment(commentCreateDTO2);
        commentService.addComment(commentCreateDTO3);

        List<CommentDTO> commentDTOList = commentService.getPostCommentsByPostId(postDTO.getId());
        long parentComment1 = commentDTOList.get(0).getId();
        long parentComment2 = commentDTOList.get(1).getId();

        CommentCreateDTO commentCreateDTO4 = new CommentCreateDTO("text4", postDTO.getId(),parentComment1);
        CommentCreateDTO commentCreateDTO5 = new CommentCreateDTO("text5", postDTO.getId(),parentComment1);
        CommentCreateDTO commentCreateDTO6 = new CommentCreateDTO("text6", postDTO.getId(),parentComment1);
        CommentCreateDTO commentCreateDTO7 = new CommentCreateDTO("text7", postDTO.getId(),parentComment2);
        commentService.addComment(commentCreateDTO4);
        commentService.addComment(commentCreateDTO5);
        commentService.addComment(commentCreateDTO6);
        commentService.addComment(commentCreateDTO7);

        commentDTOList = commentService.getPostCommentsByPostId(postDTO.getId());

        assertEquals(3, commentDTOList.get(0).getReplies().size());
        assertEquals(1, commentDTOList.get(1).getReplies().size());

        CommentDTO lastCommentDTO = commentDTOList.get(0).getReplies().get(2);
        assertEquals("text6", lastCommentDTO.getText());
        assertEquals(parentComment1, lastCommentDTO.getParentComment().getId());

        CommentDTO commentToDelete = commentDTOList.get(0).getReplies().get(0);
        long commentId = commentToDelete.getId();
        commentService.deleteComment(commentId);

        CommentDTO parentComment = commentDTOList.get(0);
        assertTrue(parentComment.getReplies().stream().anyMatch(comment -> comment.getId() == commentId));
        assertEquals(3, parentComment.getReplies().size());
        assertEquals(1, commentDTOList.get(1).getReplies().size());

        Comment deletedComment = commentRepository.findById(commentId).orElseThrow(() -> new CommentNotFoundException());
        assertTrue(deletedComment.isDeleted());

        CommentDTO newParentComment = modelMapper.map(commentRepository.findById(parentComment.getId()).orElseThrow(() -> new CommentNotFoundException()), CommentDTO.class);
        assertEquals(3, newParentComment.getReplies().size());

        commentService.deleteComment(parentComment.getId());
        commentDTOList = commentService.getPostCommentsByPostId(postDTO.getId());
        assertFalse(commentDTOList.contains(newParentComment));
        Comment deletedParentComment = commentRepository.findById(newParentComment.getId()).orElseThrow(() -> new CommentNotFoundException());
        assertTrue(deletedParentComment.isDeleted());

        postService.getAllPosts().stream().forEach(post -> {
            try {
                postService.deletePostById(post.getId());
            } catch (PostNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
    }
}