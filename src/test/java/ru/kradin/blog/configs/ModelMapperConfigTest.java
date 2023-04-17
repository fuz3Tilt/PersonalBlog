package ru.kradin.blog.configs;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import ru.kradin.blog.dto.CommentDTO;
import ru.kradin.blog.dto.LikeDTO;
import ru.kradin.blog.dto.PostDTO;
import ru.kradin.blog.dto.UserDTO;
import ru.kradin.blog.enums.Role;
import ru.kradin.blog.models.Comment;
import ru.kradin.blog.models.Like;
import ru.kradin.blog.models.Post;
import ru.kradin.blog.models.User;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class ModelMapperConfigTest {

    @Autowired
    ModelMapper modelMapper;

    @Test
    public void userMapperTest() {
        User user = new User(1,
                "some@mail.ru",
                true,
                "username",
                "password",
                true,
                Role.ROLE_USER,
                LocalDateTime.now());

        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        assertEquals(user.getId(), userDTO.getId());
        assertEquals(user.getEmail(), userDTO.getEmail());
        assertEquals(user.getUsername(), userDTO.getUsername());
        assertEquals(user.isEmailVerified(), userDTO.isEmailVerified());
        assertEquals(user.isAccountNonLocked(), userDTO.isAccountNonLocked());
        assertEquals(user.getCreatedAt(), userDTO.getCreatedAt());
    }

    @Test
    public void postMapperTest(){
        Post post = new Post(2,
                "title",
                "content",
                new ArrayList<Comment>(),
                new ArrayList<Like>(),
                LocalDateTime.now());

        PostDTO postDTO = modelMapper.map(post, PostDTO.class);
        assertEquals(post.getId(), postDTO.getId());
        assertEquals(post.getTitle(), postDTO.getTitle());
        assertEquals(post.getContent(), postDTO.getContent());
        assertEquals(null, postDTO.getComments());
        assertEquals(null, postDTO.getLikes());
        assertEquals(post.getCreatedAt(), postDTO.getCreatedAt());
    }

    @Test
    public void commentMapperTest(){
        Post parentPost = new Post();
        parentPost.setId(3);

        Comment parentComment = new Comment();
        parentComment.setId(4);

        Comment comment = new Comment();
        comment.setId(5);
        comment.setParentComment(parentComment);
        comment.setParentPost(parentPost);
        comment.setDeleted(false);
        comment.setText("text");
        comment.setDepth(6);
        comment.setCreatedAt(LocalDateTime.now());

        CommentDTO commentDTO = modelMapper.map(comment, CommentDTO.class);
        assertEquals(comment.getId(), commentDTO.getId());
        assertEquals(comment.getDepth(), commentDTO.getDepth());
        assertEquals(comment.getCreatedAt(), commentDTO.getCreatedAt());
        assertEquals(comment.getParentPost().getId(), commentDTO.getParentPost().getId());
        assertEquals(comment.getParentComment().getId(), commentDTO.getParentComment().getId());
        assertEquals(comment.getText(), commentDTO.getText());
        assertEquals(comment.isDeleted(), commentDTO.isDeleted());
        assertEquals(comment.getReplies(), commentDTO.getReplies());
        assertEquals(comment.getLikes(), commentDTO.getLikes());
        assertEquals(comment.getCreatedAt(), commentDTO.getCreatedAt());
    }

    @Test
    public void likeMapperTest(){
        User user = new User();
        user.setId(7);

        Comment comment = new Comment();
        comment.setId(8);

        Post post = new Post();
        post.setId(9);

        Like like = new Like();
        like.setId(10);
        like.setUser(user);
        like.setComment(comment);
        like.setPost(post);
        like.setCreatedAt(LocalDateTime.now());

        LikeDTO likeDTO = modelMapper.map(like, LikeDTO.class);
        assertEquals(like.getUser().getId(), likeDTO.getUser().getId());
        assertEquals(like.getComment().getId(), likeDTO.getComment().getId());
        assertEquals(like.getPost().getId(), likeDTO.getPost().getId());
        assertEquals(like.getCreatedAt(), likeDTO.getCreatedAt());
    }
}