package ru.kradin.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kradin.blog.models.Like;

public interface LikeRepository extends JpaRepository<Like, Long> {
}