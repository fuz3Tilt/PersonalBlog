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

import javax.validation.Valid;

@RestController
@RequestMapping("/api/user")
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
            return new ResponseEntity<>(bindingResult.getAllErrors(), HttpStatus.BAD_REQUEST);

        userInfoService.updateEmail(emailDTO);

        return new ResponseEntity<>(HttpStatus.OK);
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
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/info/password")
    public ResponseEntity<?> changePassword(@RequestBody @Valid PasswordDTO passwordDTO,
                                         BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return new ResponseEntity<>(bindingResult.getAllErrors(), HttpStatus.BAD_REQUEST);

        userInfoService.updatePassword(passwordDTO);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/like")
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
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/comment")
    public ResponseEntity<?> addComment(@RequestBody @Valid CommentCreateDTO commentCreateDTO,
                                        BindingResult bindingResult) throws PostNotFoundException, CommentNotFoundException {
        if (bindingResult.hasErrors())
            return new ResponseEntity<>(bindingResult.getAllErrors(), HttpStatus.BAD_REQUEST);

        if (commentCreateDTO.getParentCommentId()==0) {
            commentService.addCommentToPost(commentCreateDTO);
        } else {
            commentService.addCommentToComment(commentCreateDTO);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/comment")
    public ResponseEntity<?> deleteComment(@RequestParam("id") long id) throws CommentNotFoundException {
        commentService.deleteComment(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
