package ru.kradin.blog.services.implementations;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kradin.blog.dto.CommentCreateDTO;
import ru.kradin.blog.dto.CommentDTO;
import ru.kradin.blog.exceptions.CommentNotFoundException;
import ru.kradin.blog.exceptions.PostNotFoundException;
import ru.kradin.blog.models.Comment;
import ru.kradin.blog.models.Post;
import ru.kradin.blog.models.User;
import ru.kradin.blog.repositories.CommentRepository;
import ru.kradin.blog.repositories.PostRepository;
import ru.kradin.blog.services.interfaces.CommentService;
import ru.kradin.blog.services.interfaces.AuthenticatedUserService;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    private static final Logger log = LoggerFactory.getLogger(CommentServiceImpl.class);

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    AuthenticatedUserService authenticatedUserService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<CommentDTO> getPostCommentsByPostId(long id) {
        List<Comment> comments = commentRepository.findByParentPost_IdAndDepthNull(id);
        List<CommentDTO> commentDTOS = modelMapper.map(comments, new TypeToken<List<CommentDTO>>() {}.getType());
        return commentDTOS;
    }

    @Override
    @Transactional
    @PreAuthorize("isAuthenticated()")
    public void addCommentToPost(Authentication authentication, CommentCreateDTO commentCreateDTO) throws PostNotFoundException {
        User user = authenticatedUserService.getCurentUser(authentication);
        Post parentPost = postRepository.findById(commentCreateDTO.getParentPostId()).orElseThrow(() -> new PostNotFoundException());
        Comment comment = new Comment();
        comment.setParentPost(parentPost);
        comment.setUser(user);
        comment.setText(commentCreateDTO.getText());
        comment.setDeleted(false);
        comment.setCreatedAt(LocalDateTime.now());
        comment.setDepth(0);
        commentRepository.save(comment);
        log.info("User {} added comment to post with id {}",user.getUsername(),comment.getParentPost().getId());
    }

    @Override
    @Transactional
    @PreAuthorize("isAuthenticated()")
    public void addCommentToComment(Authentication authentication, CommentCreateDTO commentCreateDTO) throws PostNotFoundException, CommentNotFoundException {
        User user = authenticatedUserService.getCurentUser(authentication);
        Post parentPost = postRepository.findById(commentCreateDTO.getParentPostId()).orElseThrow(() -> new PostNotFoundException());
        Comment parentComment = commentRepository.findById(commentCreateDTO.getParentCommentId()).orElseThrow(() -> new CommentNotFoundException());
        Comment comment = new Comment();
        comment.setParentPost(parentPost);
        comment.setParentComment(parentComment);
        comment.setUser(user);
        comment.setText(commentCreateDTO.getText());
        comment.setDeleted(false);
        comment.setCreatedAt(LocalDateTime.now());
        comment.setDepth(parentComment.getDepth()+1);
        commentRepository.save(comment);
        log.info("User {} added comment to comment with id {}", user.getUsername(), comment.getParentComment().getId());
    }

    @Override
    @Transactional
    @PreAuthorize("isAuthenticated()")
    public void deleteComment(Authentication authentication, long commentId) throws CommentNotFoundException {
        User user = authenticatedUserService.getCurentUser(authentication);
        Comment comment = commentRepository.findByUserAndId(user, commentId).orElseThrow(() -> new CommentNotFoundException());
        comment.setDeleted(true);
        commentRepository.save(comment);
        log.info("User {} deleted comment with id {}", user.getUsername(), comment.getId());
    }
}
