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
    public void postServiceTest() throws PostNotFoundException {
        Authentication auth = new TestingAuthenticationToken("username", "password", "ROLE_ADMIN");
        SecurityContextHolder.getContext().setAuthentication(auth);

        PostCreateDTO postCreateDTO1 = new PostCreateDTO("title1","content1");
        PostCreateDTO postCreateDTO2 = new PostCreateDTO("title2","content2");
        PostCreateDTO postCreateDTO3 = new PostCreateDTO("title3","content3");
        PostCreateDTO postCreateDTO4 = new PostCreateDTO("title4","content5");

        postService.createPost(postCreateDTO1);
        postService.createPost(postCreateDTO2);
        postService.createPost(postCreateDTO3);
        postService.createPost(postCreateDTO4);

        List<PostDTO> postDTOList = postService.getAllPosts();
        assertThat(postDTOList.size()==4);

        long id = postDTOList.get(0).getId();

        PostUpdateDTO postUpdateDTO = new PostUpdateDTO(id, "updated title","updated content");

        postService.updatePost(postUpdateDTO);

        PostDTO postDTO = postService.getPostById(id);

        assertEquals("updated title",postDTO.getTitle());
        assertEquals("updated content",postDTO.getContent());

        postService.deletePostById(id);

        try {
            postService.getPostById(id);
            fail("PostNotFoundException expected");
        } catch (PostNotFoundException e) {
            postDTOList = postService.getAllPosts();
            assertThat(postDTOList.size() == 3);
        }
    }

}