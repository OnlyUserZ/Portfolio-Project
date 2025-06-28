// Kenan Şeniz

package com.website.websites.service;

import lombok.RequiredArgsConstructor;

import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import com.website.websites.dto.CommentDTO;
import com.website.websites.entity.Comment;
import com.website.websites.entity.Topic;
import com.website.websites.entity.User;
import com.website.websites.exception.BadRequestException;
import com.website.websites.exception.ResourceNotFoundException;
import com.website.websites.repository.CommentRepository;
import com.website.websites.repository.TopicRepository;
import com.website.websites.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
@RequiredArgsConstructor

public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final TopicRepository topicRepository;    

    public Comment addComment(CommentDTO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User bulunamadı"));
        Topic topic = topicRepository.findById(dto.getTopicId())
                .orElseThrow(() -> new ResourceNotFoundException("Product bulunamadı"));

        Comment comment = Comment.builder()
                .user(user)
                .topic(topic)
                .text(dto.getText())
                .rating(dto.getRating())
                .build();

        return commentRepository.save(comment);
    }
    @Cacheable(value = "commentsByTopic", key = "#topicId + '-' + #page + '-' + #size")
    public Page<Comment> getCommentsByTopic(Long topicId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        return commentRepository.findByTopicId(topicId, pageable);
    }
    
    @CacheEvict(value = "deleteComment" , key = "#id")
    @Transactional
    
    public void deleteComment(Long id) {
    	Optional<Comment> comment = commentRepository.findById(id);
    	if(comment.isPresent()) {
    		Comment commentd = comment.get();
    		commentRepository.delete(commentd);
    	} else { throw new BadRequestException("Comment Bulunamadı"); }
    }
    
    @Transactional
    public void updateComment(Long id , CommentDTO dto) {
    	Optional<Comment> commentr = commentRepository.findById(id);
    	if(commentr.isPresent()) {
    		Comment comment = commentr.get();
    		comment.setRating(dto.getRating());
    		comment.setText(dto.getText());
    		
    		commentRepository.save(comment);
    		
    	} else { throw new RuntimeException("Bulamadık"); }
    	
    }
}             