package ru.kradin.blog.services.implementations;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.TestPropertySource;
import ru.kradin.blog.dto.PostCreateDTO;
import ru.kradin.blog.dto.PostDTO;
import ru.kradin.blog.dto.PostUpdateDTO;
import ru.kradin.blog.exceptions.PostNotFoundException;
import ru.kradin.blog.services.interfaces.PostService;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class PostServiceImplTest {

    @Autowired
    PostService postService;

    @Test
    public void testPostService() throws PostNotFoundException {
        Authentication auth = new TestingAuthenticationToken("username", "password", "ROLE_ADMIN");
        SecurityContextHolder.getContext().setAuthentication(auth);

        PostCreateDTO postCreateDTO1 = new PostCreateDTO("title1", "content1");
        PostCreateDTO postCreateDTO2 = new PostCreateDTO("title2", "content2");
        PostCreateDTO postCreateDTO3 = new PostCreateDTO("title3", "content3");
        PostCreateDTO postCreateDTO4 = new PostCreateDTO("title4", "content4");
        postService.createPost(postCreateDTO1);
        postService.createPost(postCreateDTO2);
        postService.createPost(postCreateDTO3);
        postService.createPost(postCreateDTO4);

        List<PostDTO> postDTOList = postService.getAllPosts();
        assertThat(postDTOList.size()).isEqualTo(4);

        long id1 = postDTOList.get(0).getId();
        PostUpdateDTO postUpdateDTO1 = new PostUpdateDTO(id1, "updated title1", "updated content1");
        postService.updatePost(postUpdateDTO1);

        PostDTO postDTO1 = postService.getPostById(id1);
        assertThat(postDTO1.getTitle()).isEqualTo("updated title1");
        assertThat(postDTO1.getContent()).isEqualTo("updated content1");

        postService.deletePostById(id1);

        try {
            postService.getPostById(id1);
            fail("PostNotFoundException expected");
        } catch (PostNotFoundException e) {
            postDTOList = postService.getAllPosts();
            assertThat(postDTOList.size()).isEqualTo(3);
        }

        long id2 = postDTOList.get(1).getId();
        PostUpdateDTO postUpdateDTO2 = new PostUpdateDTO(id2, "updated title2", "updated content2");
        postService.updatePost(postUpdateDTO2);

        PostDTO postDTO2 = postService.getPostById(id2);
        assertThat(postDTO2.getTitle()).isEqualTo("updated title2");
        assertThat(postDTO2.getContent()).isEqualTo("updated content2");

        postService.deletePostById(id2);

        try {
            postService.getPostById(id2);
            fail("PostNotFoundException expected");
        } catch (PostNotFoundException e) {
            postDTOList = postService.getAllPosts();
            assertThat(postDTOList.size()).isEqualTo(2);
        }

        long id3 = postDTOList.get(0).getId();
        PostUpdateDTO postUpdateDTO3 = new PostUpdateDTO(id3, "updated title3", "updated content3");
        postService.updatePost(postUpdateDTO3);

        PostDTO postDTO3 = postService.getPostById(id3);
        assertThat(postDTO3.getTitle()).isEqualTo("updated title3");
        assertThat(postDTO3.getContent()).isEqualTo("updated content3");

        postService.deletePostById(id3);

        try {
            postService.getPostById(id3);
            fail("PostNotFoundException expected");
        } catch (PostNotFoundException e) {
            // Получение всех постов и проверка их количества
            postDTOList = postService.getAllPosts();
            assertThat(postDTOList.size()).isEqualTo(1);
        }

        long id4 = postDTOList.get(0).getId();
        PostUpdateDTO postUpdateDTO4 = new PostUpdateDTO(id4, "updated title4", "updated content4");
        postService.updatePost(postUpdateDTO4);

        PostDTO postDTO4 = postService.getPostById(id4);
        assertThat(postDTO4.getTitle()).isEqualTo("updated title4");
        assertThat(postDTO4.getContent()).isEqualTo("updated content4");

        postService.deletePostById(id4);

        try {
            postService.getPostById(id4);
            fail("PostNotFoundException expected");
        } catch (PostNotFoundException e) {
            // Получение всех постов и проверка их количества
            postDTOList = postService.getAllPosts();
            assertThat(postDTOList.size()).isEqualTo(0);
        }
    }

}