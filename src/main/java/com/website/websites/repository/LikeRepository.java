package com.website.websites.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.website.websites.entity.Like;

public interface LikeRepository extends JpaRepository<Like, Long> {
	
 boolean existsByUserIdAndCommentId(Long userId, Long commentId);
 
 Like findByUserIdAndCommentId(Long userId , Long commentId);
 
 long countByCommentId(Long commentId);
}
