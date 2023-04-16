package ru.kradin.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kradin.blog.models.Comment;
import ru.kradin.blog.models.User;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByParentPost_IdAndDepthNull(long id);

    Optional<Comment> findByUserAndId(User user, long id);
}