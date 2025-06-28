package com.website.websites.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.website.websites.dto.CommentDTO;
import com.website.websites.entity.Comment;
import com.website.websites.service.CommentService;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

 private final CommentService commentService;

 
 @PostMapping
 public ResponseEntity<Comment> addComment(@Valid @RequestBody CommentDTO dto) {
     Comment comment = commentService.addComment(dto);
     return ResponseEntity.ok(comment);
 }

 @GetMapping("/topic/{topicId}")
 public ResponseEntity<Page<Comment>> getCommentsByTopic(
         @PathVariable Long topicId,
         @RequestParam(defaultValue = "0") int page,
         @RequestParam(defaultValue = "10") int size) {

     Page<Comment> comments = commentService.getCommentsByTopic(topicId, page, size);
     return ResponseEntity.ok(comments);
 }
 
 @DeleteMapping("/delete/{id}")
 public void deleteComment(@PathVariable Long id) {
 	commentService.deleteComment(id);
 }
}
