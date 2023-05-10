package ru.kradin.blog.controllers;

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
import ru.kradin.blog.services.interfaces.AdminService;
import ru.kradin.blog.services.interfaces.PostService;
import ru.kradin.blog.utils.FieldErrorsUtil;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    @Autowired
    PostService postService;

    @PostMapping("/posts")
    public ResponseEntity<?> createPost(@RequestBody @Valid PostUpdateCreateDTO postUpdateCreateDTO,
                                        BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return ResponseEntity.badRequest().body(FieldErrorsUtil.getErrors(bindingResult));

        postService.createPost(postUpdateCreateDTO);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/posts/{id}")
    public ResponseEntity<?> updatePost(@PathVariable("id") long id,
                                        @RequestBody @Valid PostUpdateCreateDTO postUpdateCreateDTO,
                                        BindingResult bindingResult) throws PostNotFoundException {
        if (bindingResult.hasErrors())
            return ResponseEntity.badRequest().body(FieldErrorsUtil.getErrors(bindingResult));

        postService.updatePost(id, postUpdateCreateDTO);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/posts/{id}")
    public ResponseEntity<?> deletePost(@PathVariable("id") long id) throws PostNotFoundException {
        postService.deletePostById(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/comments/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable("id") long id) throws CommentNotFoundException {
        adminService.deleteComment(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/users/ban")
    public ResponseEntity<?> toggleUserBan(@RequestParam("username") String username) throws UserNotFoundException {
        adminService.toggleUserBan(username);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/users/{id}/info")
    public UserInfoDTO getUserInfoById(@PathVariable("id") long id) throws UserNotFoundException {
        return adminService.getUserById(id);
    }

    @GetMapping("/users/info")
    public List<UserInfoDTO> getUserInfoList(@RequestParam("status") String status){
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
