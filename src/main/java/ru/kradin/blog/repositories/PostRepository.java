package ru.kradin.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kradin.blog.models.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

}