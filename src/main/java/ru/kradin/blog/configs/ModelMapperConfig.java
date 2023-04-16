package ru.kradin.blog.configs;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.kradin.blog.dto.CommentDTO;
import ru.kradin.blog.dto.LikeDTO;
import ru.kradin.blog.dto.PostDTO;
import ru.kradin.blog.dto.UserDTO;
import ru.kradin.blog.models.Comment;
import ru.kradin.blog.models.Like;
import ru.kradin.blog.models.Post;
import ru.kradin.blog.models.User;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setSkipNullEnabled(true)
                .setFieldMatchingEnabled(true);

        createPostMapping(modelMapper);
        createCommentMapping(modelMapper);
        createLikeMapping(modelMapper);
        createUserMapping(modelMapper);

        return modelMapper;
    }

    private void createPostMapping(ModelMapper modelMapper) {
        TypeMap<Post, PostDTO> typeMap = modelMapper.createTypeMap(Post.class, PostDTO.class);
        typeMap.addMapping(Post::getId, PostDTO::setId);
        typeMap.addMapping(Post::getTitle, PostDTO::setTitle);
        typeMap.addMapping(Post::getContent, PostDTO::setContent);
        typeMap.addMapping(Post::getCreatedAt, PostDTO::setCreatedAt);
        typeMap.addMappings(mapper -> {
            mapper.map(src -> mapCommentList(modelMapper, src.getComments()), PostDTO::setComments);
            mapper.map(src -> mapLikeList(modelMapper, src.getLikes()), PostDTO::setLikes);
        });
    }

    private void createCommentMapping(ModelMapper modelMapper) {
        TypeMap<Comment, CommentDTO> typeMap = modelMapper.createTypeMap(Comment.class, CommentDTO.class);
        typeMap.addMapping(Comment::getId, CommentDTO::setId);
        typeMap.addMapping(Comment::getText, CommentDTO::setText);
        typeMap.addMapping(Comment::getDepth, CommentDTO::setDepth);
        typeMap.addMapping(Comment::getParentPost, CommentDTO::setParentPost);
        typeMap.addMappings(mapper -> {
            mapper.map(src -> src.getParentComment() != null ? modelMapper.map(src.getParentComment(), CommentDTO.class) : null, CommentDTO::setParentComment);
            mapper.map(src -> mapCommentList(modelMapper, src.getReplies()), CommentDTO::setReplies);
            mapper.map(src -> mapLikeList(modelMapper, src.getLikes()), CommentDTO::setLikes);
            mapper.map(src -> src.isDeleted(), CommentDTO::setDeleted);
            mapper.map(src -> src.getCreatedAt(), CommentDTO::setCreatedAt);
            mapper.map(src -> modelMapper.map(src.getUser(), UserDTO.class), CommentDTO::setUser);
        });
    }

    private void createLikeMapping(ModelMapper modelMapper) {
        TypeMap<Like, LikeDTO> typeMap = modelMapper.createTypeMap(Like.class, LikeDTO.class);
        typeMap.addMapping(src -> src.getPost() != null ? modelMapper.map(src.getPost(), PostDTO.class) : null,
                LikeDTO::setPost);
        typeMap.addMapping(src -> src.getComment() != null ? modelMapper.map(src.getComment(), CommentDTO.class) : null,
                LikeDTO::setComment);
        typeMap.addMapping(Like::getUser, LikeDTO::setUser);
        typeMap.addMapping(Like::getCreatedAt, LikeDTO::setCreatedAt);
    }

    private void createUserMapping(ModelMapper modelMapper) {
        TypeMap<User, UserDTO> typeMap = modelMapper.createTypeMap(User.class, UserDTO.class);
        typeMap.addMapping(User::getId, UserDTO::setId);
        typeMap.addMapping(User::getUsername, UserDTO::setUsername);
        typeMap.addMapping(User::getEmail, UserDTO::setEmail);
        typeMap.addMapping(User::isEmailVerified, UserDTO::setEmailVerified);
        typeMap.addMapping(User::isAccountNonLocked, UserDTO::setAccountNonLocked);
        typeMap.addMapping(User::getRole, UserDTO::setRole);
        typeMap.addMapping(User::getCreatedAt, UserDTO::setCreatedAt);
    }

    private List<CommentDTO> mapCommentList(ModelMapper modelMapper, List<Comment> comments) {
        return comments.stream()
                .map(comment -> modelMapper.map(comment, CommentDTO.class))
                .collect(Collectors.toList());
    }

    private List<LikeDTO> mapLikeList(ModelMapper modelMapper, List<Like> likes) {
        return likes.stream()
                .map(like -> modelMapper.map(like, LikeDTO.class))
                .collect(Collectors.toList());
    }
}
