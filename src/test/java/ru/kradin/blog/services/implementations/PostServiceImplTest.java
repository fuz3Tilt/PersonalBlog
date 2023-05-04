package ru.kradin.blog.services.implementations;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.TestPropertySource;
import ru.kradin.blog.dto.PostUpdateCreateDTO;
import ru.kradin.blog.dto.PostDTO;
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

        PostUpdateCreateDTO postUpdateCreateDTO1 = new PostUpdateCreateDTO("title1", "content1");
        PostUpdateCreateDTO postUpdateCreateDTO2 = new PostUpdateCreateDTO("title2", "content2");
        PostUpdateCreateDTO postUpdateCreateDTO3 = new PostUpdateCreateDTO("title3", "content3");
        PostUpdateCreateDTO postUpdateCreateDTO4 = new PostUpdateCreateDTO("title4", "content4");
        postService.createPost(postUpdateCreateDTO1);
        postService.createPost(postUpdateCreateDTO2);
        postService.createPost(postUpdateCreateDTO3);
        postService.createPost(postUpdateCreateDTO4);

        List<PostDTO> postDTOList = postService.getAllPosts();
        assertThat(postDTOList.size()).isEqualTo(4);

        long id1 = postDTOList.get(0).getId();
        PostUpdateCreateDTO postUpdateDTO1 = new PostUpdateCreateDTO("updated title1", "updated content1");
        postService.updatePost(id1, postUpdateDTO1);

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
        PostUpdateCreateDTO postUpdateDTO2 = new PostUpdateCreateDTO( "updated title2", "updated content2");
        postService.updatePost(id2, postUpdateDTO2);

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
        PostUpdateCreateDTO postUpdateDTO3 = new PostUpdateCreateDTO("updated title3", "updated content3");
        postService.updatePost(id3, postUpdateDTO3);

        PostDTO postDTO3 = postService.getPostById(id3);
        assertThat(postDTO3.getTitle()).isEqualTo("updated title3");
        assertThat(postDTO3.getContent()).isEqualTo("updated content3");

        postService.deletePostById(id3);

        try {
            postService.getPostById(id3);
            fail("PostNotFoundException expected");
        } catch (PostNotFoundException e) {
            postDTOList = postService.getAllPosts();
            assertThat(postDTOList.size()).isEqualTo(1);
        }

        long id4 = postDTOList.get(0).getId();
        PostUpdateCreateDTO postUpdateDTO4 = new PostUpdateCreateDTO("updated title4", "updated content4");
        postService.updatePost(id4, postUpdateDTO4);

        PostDTO postDTO4 = postService.getPostById(id4);
        assertThat(postDTO4.getTitle()).isEqualTo("updated title4");
        assertThat(postDTO4.getContent()).isEqualTo("updated content4");

        postService.deletePostById(id4);

        try {
            postService.getPostById(id4);
            fail("PostNotFoundException expected");
        } catch (PostNotFoundException e) {
            postDTOList = postService.getAllPosts();
            assertThat(postDTOList.size()).isEqualTo(0);
        }

        postService.getAllPosts().stream().forEach(post -> {
            try {
                postService.deletePostById(post.getId());
            } catch (PostNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
    }

}