package com.website.websites.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.website.websites.dto.LikeDTO;
import com.website.websites.dto.LikeUrunDTO;
import com.website.websites.entity.Like;
import com.website.websites.entity.LikeUrun;
import com.website.websites.service.LikeService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/like")
@RequiredArgsConstructor
public class LikeController {
	
	private final LikeService likeService;
	
	@PostMapping
	public ResponseEntity<Like> addLike(@Valid @RequestBody LikeDTO dto) {
		Like like = likeService.likeComment(dto);
		
		if(like == null) {
			return ResponseEntity.ok(null);
		}
	return ResponseEntity.ok(like);
	}
	
	@PostMapping("/likeUrun")
	public ResponseEntity<LikeUrun> addLikeUrun(@Valid @RequestBody LikeUrunDTO dto) {
		LikeUrun likeUrun = likeService.likeUrun(dto);
		if(likeUrun == null) {
			return ResponseEntity.ok(null);
		} return ResponseEntity.ok(likeUrun);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Long> getTotalLikesForComment(@PathVariable Long id ) {
		Long totalLike = likeService.getTotalLikesForComment(id);		
		return ResponseEntity.ok(totalLike);
	}
	
	
	



}
