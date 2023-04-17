package ru.kradin.blog.configs;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
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

import java.util.Collections;
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
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);

        modelMapper.addMappings(new PropertyMap<Post, PostDTO>() {
            @Override
            protected void configure() {
                skip(destination.getComments());
                skip(destination.getLikes());
            }
        });

        return modelMapper;
    }
}
