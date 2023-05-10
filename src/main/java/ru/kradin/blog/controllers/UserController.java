package ru.kradin.blog.controllers;

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
import ru.kradin.blog.services.interfaces.CommentService;
import ru.kradin.blog.services.interfaces.LikeService;
import ru.kradin.blog.services.interfaces.UserInfoService;
import ru.kradin.blog.services.interfaces.UserVerificationService;
import ru.kradin.blog.utils.FieldErrorsUtil;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/user")
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
    public UserInfoDTO getUserInfo(){
        return userInfoService.getUserInfo();
    }

    @PatchMapping("/info/email")
    public ResponseEntity< ? > changeEmail(@RequestBody @Valid EmailDTO emailDTO,
                                           BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return ResponseEntity.badRequest().body(FieldErrorsUtil.getErrors(bindingResult));

        userInfoService.updateEmail(emailDTO);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/info/email/send-confirmation")
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
    public ResponseEntity<?> changePassword(@RequestBody @Valid PasswordDTO passwordDTO,
                                         BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return ResponseEntity.badRequest().body(FieldErrorsUtil.getErrors(bindingResult));

        userInfoService.updatePassword(passwordDTO);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/likes")
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
    public ResponseEntity<?> addComment(@RequestBody @Valid CommentCreateDTO commentCreateDTO,
                                        BindingResult bindingResult) throws PostNotFoundException, CommentNotFoundException {
        if (bindingResult.hasErrors())
            return ResponseEntity.badRequest().body(FieldErrorsUtil.getErrors(bindingResult));

        commentService.addComment(commentCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/comments/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable("id") long id) throws CommentNotFoundException {
        commentService.deleteComment(id);
        return ResponseEntity.ok().build();
    }
}
