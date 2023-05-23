package ru.kradin.blog.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
@Api(value = "Visitor API", tags = {"Visitor"})
public class VisitorController {

    @Autowired
    PostService postService;

    @Autowired
    CommentService commentService;

    @Autowired
    LikeService likeService;

    @GetMapping("/posts")
    @ApiOperation(value = "Get all posts")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved all posts")
    })
    public List<PostDTO> getAllPosts(){
        return postService.getAllPosts();
    }

    @GetMapping("/posts/{id}")
    @ApiOperation(value = "Get post by ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved the post"),
            @ApiResponse(code = 404, message = "Post not found")
    })
    public PostDTO getPost(@PathVariable("id") long id) throws PostNotFoundException {
        return postService.getPostById(id);
    }

    @GetMapping("/likes")
    @ApiOperation(value = "Get likes for a post")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved likes for the post"),
            @ApiResponse(code = 404, message = "Post not found")
    })
    public List<LikeDTO> getPostLikes(@RequestParam("postId") long postId) throws PostNotFoundException {
        return likeService.getPostLikesByPostId(postId);
    }

    @GetMapping("/comments")
    @ApiOperation(value = "Get comments for a post")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved comments for the post"),
            @ApiResponse(code = 404, message = "Post not found")
    })
    public List<CommentDTO> getPostComments(@RequestParam("postId") long postId) throws PostNotFoundException {
        return commentService.getPostCommentsByPostId(postId);
    }
}
