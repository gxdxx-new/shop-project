package com.gxdxx.shop.repository;

import com.gxdxx.shop.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}