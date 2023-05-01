package ru.kradin.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.kradin.blog.dto.CommentDTO;
import ru.kradin.blog.dto.LikeDTO;
import ru.kradin.blog.dto.PostDTO;
import ru.kradin.blog.exceptions.PostNotFoundException;
import ru.kradin.blog.services.interfaces.CommentService;
import ru.kradin.blog.services.interfaces.LikeService;
import ru.kradin.blog.services.interfaces.PostService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/visitor")
public class VisitorController {

    @Autowired
    PostService postService;

    @Autowired
    CommentService commentService;

    @Autowired
    LikeService likeService;

    @GetMapping("/posts")
    public List<PostDTO> getAllPosts(){
        return postService.getAllPosts();
    }

    @GetMapping("/posts/{id}")
    public PostDTO getPost(@PathVariable("id") long id) throws PostNotFoundException {
        return postService.getPostById(id);
    }

    @GetMapping("/likes")
    public List<LikeDTO> getPostLikes(@RequestParam("postId") long postId) throws PostNotFoundException {
        return likeService.getPostLikesByPostId(postId);
    }

    @GetMapping("/comments")
    public List<CommentDTO> getPostComments(@RequestParam("postId") long postId) throws PostNotFoundException {
        return commentService.getPostCommentsByPostId(postId);
    }
}
