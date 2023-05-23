package ru.kradin.blog.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kradin.blog.dto.PostUpdateCreateDTO;
import ru.kradin.blog.dto.UserInfoDTO;
import ru.kradin.blog.exceptions.CommentNotFoundException;
import ru.kradin.blog.exceptions.PostNotFoundException;
import ru.kradin.blog.exceptions.UserNotFoundException;
import ru.kradin.blog.responses.ValidationErrorResponse;
import ru.kradin.blog.services.interfaces.AdminService;
import ru.kradin.blog.services.interfaces.PostService;
import ru.kradin.blog.utils.FieldErrorsUtil;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@Api(value = "Admin API", tags = {"Admin"})
public class AdminController {

    @Autowired
    AdminService adminService;

    @Autowired
    PostService postService;

    @PostMapping("/posts")
    @ApiOperation(value = "Create a new post")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Post created successfully"),
            @ApiResponse(code = 400, message = "Validation error", response = ValidationErrorResponse.class)
    })
    public ResponseEntity<?> createPost(@RequestBody @Valid PostUpdateCreateDTO postUpdateCreateDTO,
                                        BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return ResponseEntity.badRequest().body(new ValidationErrorResponse(FieldErrorsUtil.getErrors(bindingResult)));

        postService.createPost(postUpdateCreateDTO);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/posts/{id}")
    @ApiOperation(value = "Update a post")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Post updated successfully"),
            @ApiResponse(code = 400, message = "Validation error", response = ValidationErrorResponse.class),
            @ApiResponse(code = 404, message = "Post not found")
    })
    public ResponseEntity<?> updatePost(@PathVariable("id") long id,
                                        @RequestBody @Valid PostUpdateCreateDTO postUpdateCreateDTO,
                                        BindingResult bindingResult) throws PostNotFoundException {
        if (bindingResult.hasErrors())
            return ResponseEntity.badRequest().body(new ValidationErrorResponse(FieldErrorsUtil.getErrors(bindingResult)));

        postService.updatePost(id, postUpdateCreateDTO);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/posts/{id}")
    @ApiOperation(value = "Delete a post")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Post deleted successfully"),
            @ApiResponse(code = 404, message = "Post not found")
    })
    public ResponseEntity<?> deletePost(@PathVariable("id") long id) throws PostNotFoundException {
        postService.deletePostById(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/comments/{id}")
    @ApiOperation(value = "Delete a comment")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Comment deleted successfully"),
            @ApiResponse(code = 404, message = "Comment not found")
    })
    public ResponseEntity<?> deleteComment(@PathVariable("id") long id) throws CommentNotFoundException {
        adminService.deleteComment(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/users/ban")
    @ApiOperation(value = "Toggle user ban")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User ban toggled"),
            @ApiResponse(code = 404, message = "User not found")
    })
    public ResponseEntity<?> toggleUserBan(@RequestParam("username") String username) throws UserNotFoundException {
        adminService.toggleUserBan(username);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/users/{id}/info")
    @ApiOperation(value = "Get user info")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User info returned"),
            @ApiResponse(code = 404, message = "User not found")
    })
    public UserInfoDTO getUserInfoById(@PathVariable("id") long id) throws UserNotFoundException {
        return adminService.getUserById(id);
    }

    @GetMapping("/users/info")
    @ApiOperation(value = "Get users info")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List of users info returned"),
            @ApiResponse(code = 400, message = "Invalid status")
    })
    public List<UserInfoDTO> getUserInfoList(@RequestParam(name = "status", required = false, defaultValue = "all") String status){
        switch (status){
            case ("all"):
                return adminService.getAllUsers();
            case("banned"): 
                return adminService.getBannedUsers();
            case("active"):
                return adminService.getActiveUsers();
            default:
                throw new IllegalArgumentException("Invalid status: " + status);
        }
    }
}
