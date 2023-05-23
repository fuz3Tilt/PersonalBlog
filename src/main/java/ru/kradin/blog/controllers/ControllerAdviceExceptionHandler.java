package ru.kradin.blog.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.kradin.blog.exceptions.CommentNotFoundException;
import ru.kradin.blog.exceptions.PostNotFoundException;
import ru.kradin.blog.exceptions.UserNotFoundException;
import ru.kradin.blog.exceptions.UserVerificationTokenNotFoundException;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@ControllerAdvice
public class ControllerAdviceExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFoundException(UserNotFoundException e) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(CommentNotFoundException.class)
    public ResponseEntity<?> handleCommentNotFoundException(CommentNotFoundException e) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<?> handlePostNotFoundException(PostNotFoundException e) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(UserVerificationTokenNotFoundException.class)
    public ResponseEntity< ? > handleUserVerificationTokenNotFoundException(UserVerificationTokenNotFoundException e) {
        return ResponseEntity.notFound().build();
    }
}
