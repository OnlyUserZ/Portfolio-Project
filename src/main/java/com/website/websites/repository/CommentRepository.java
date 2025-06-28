package com.website.websites.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.website.websites.entity.Comment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentRepository extends JpaRepository<Comment, Long> {
 Page<Comment> findByTopicId(Long TopicId, Pageable pageable);
}
