package ru.kradin.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kradin.blog.models.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}