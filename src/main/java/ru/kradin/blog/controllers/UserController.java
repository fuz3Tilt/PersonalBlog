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
import ru.kradin.blog.dto.CommentCreateDTO;
import ru.kradin.blog.dto.EmailDTO;
import ru.kradin.blog.dto.PasswordDTO;
import ru.kradin.blog.dto.UserInfoDTO;
import ru.kradin.blog.exceptions.*;
import ru.kradin.blog.responses.ValidationErrorResponse;
import ru.kradin.blog.services.interfaces.CommentService;
import ru.kradin.blog.services.interfaces.LikeService;
import ru.kradin.blog.services.interfaces.UserInfoService;
import ru.kradin.blog.services.interfaces.UserVerificationService;
import ru.kradin.blog.utils.FieldErrorsUtil;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/user")
@Api(value = "User API", tags = {"User"})
public class UserController {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private UserVerificationService userVerificationService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private LikeService likeService;

    @GetMapping("/info")
    @ApiOperation(value = "Get info about current user")
    @ApiResponse(code = 200, message = "User info returned", response = UserInfoDTO.class)
    public UserInfoDTO getUserInfo(){
        return userInfoService.getUserInfo();
    }

    @PatchMapping("/info/email")
    @ApiOperation(value = "Change email")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Email updated successfully"),
            @ApiResponse(code = 400, message = "Validation error", response = ValidationErrorResponse.class)
    })
    public ResponseEntity< ? > changeEmail(@RequestBody @Valid EmailDTO emailDTO,
                                           BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return ResponseEntity.badRequest().body(new ValidationErrorResponse(FieldErrorsUtil.getErrors(bindingResult)));

        userInfoService.updateEmail(emailDTO);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/info/email/send-confirmation")
    @ApiOperation(value = "Send email verification link")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Email verification link sent"),
            @ApiResponse(code = 400, message = "Bad Request. The email address is already verified or the verification token already exists."),
            @ApiResponse(code = 404, message = "Not Found. The user does not have an email address.")
    })
    public ResponseEntity<?> sendVerificationEmail(){
        try {
            userVerificationService.sendVerificationEmail();
        } catch (UserDoesNotHaveEmailException e) {
            return new ResponseEntity<>("User does not have email", HttpStatus.NOT_FOUND);
        } catch (EmailAlreadyVerifiedException e) {
            return new ResponseEntity<>("Email already verified", HttpStatus.BAD_REQUEST);
        } catch (UserVerificationTokenAlreadyExistException e) {
            return new ResponseEntity<>("Verification token already exists", HttpStatus.CONFLICT);
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/info/password")
    @ApiOperation(value = "Change user password")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Password successfully changed"),
            @ApiResponse(code = 400, message = "Validation error", response = ValidationErrorResponse.class)
    })
    public ResponseEntity<?> changePassword(@RequestBody @Valid PasswordDTO passwordDTO,
                                         BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return ResponseEntity.badRequest().body(new ValidationErrorResponse(FieldErrorsUtil.getErrors(bindingResult)));

        userInfoService.updatePassword(passwordDTO);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/likes")
    @ApiOperation(value = "Toggle post or comment like")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Like status successfully toggled"),
            @ApiResponse(code = 400, message = "Invalid target parameter provided."),
            @ApiResponse(code = 404, message = "Not Found. The post or comment with the provided ID was not found.")
    })
    public ResponseEntity<?> toggleLike(@RequestParam("target") String target,
                                        @RequestParam("id") Long id) throws PostNotFoundException, CommentNotFoundException {
        switch (target) {
            case ("post"):
                likeService.togglePostLike(id);
                break;
            case ("comment"):
                likeService.toggleCommentLike(id);
                break;
            default:
                throw new IllegalArgumentException("Invalid target: " + target);
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/comments")
    @ApiOperation(value = "Add a comment to a post")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Comment successfully added"),
            @ApiResponse(code = 400, message = "Validation error", response = ValidationErrorResponse.class),
            @ApiResponse(code = 404, message = "Post not found")
    })
    public ResponseEntity<?> addComment(@RequestBody @Valid CommentCreateDTO commentCreateDTO,
                                        BindingResult bindingResult) throws PostNotFoundException, CommentNotFoundException {
        if (bindingResult.hasErrors())
            return ResponseEntity.badRequest().body(new ValidationErrorResponse(FieldErrorsUtil.getErrors(bindingResult)));

        commentService.addComment(commentCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/comments/{id}")
    @ApiOperation(value = "Delete a comment of current user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Comment successfully deleted"),
            @ApiResponse(code = 404, message = "Comment not found")
    })
    public ResponseEntity<?> deleteComment(@PathVariable("id") long id) throws CommentNotFoundException {
        commentService.deleteComment(id);
        return ResponseEntity.ok().build();
    }
}
