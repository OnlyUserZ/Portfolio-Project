package com.website.websites.service;

import lombok.RequiredArgsConstructor;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.website.websites.dto.LikeDTO;
import com.website.websites.dto.LikeUrunDTO;
import com.website.websites.entity.Comment;
import com.website.websites.entity.Like;
import com.website.websites.entity.LikeUrun;
import com.website.websites.entity.Urun;
import com.website.websites.entity.User;
import com.website.websites.exception.ResourceNotFoundException;
import com.website.websites.exception.UrunNotFoundException;
import com.website.websites.exception.UserNotFoundException;
import com.website.websites.repository.CommentRepository;
import com.website.websites.repository.LikeRepository;
import com.website.websites.repository.LikeUrunRepository;
import com.website.websites.repository.UrunRepository;
import com.website.websites.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class LikeService {

 private final LikeRepository likeRepository;
 private final UserRepository userRepository;
 private final CommentRepository commentRepository;
 private final LikeUrunRepository likeUrunRepository;
 private final UrunRepository urunRepository;

 @CacheEvict(value = "CommentLikeCounts", key = "#dto.commentId")
 public Like likeComment(LikeDTO dto) {
     User user = userRepository.findById(dto.getUserId())
             .orElseThrow(() -> new ResourceNotFoundException("User bulunamadı"));
     Comment comment = commentRepository.findById(dto.getCommentId())
             .orElseThrow(() -> new ResourceNotFoundException("Comment bulunamadı"));
     
     Like existLike = likeRepository.findByUserIdAndCommentId(dto.getUserId(), dto.getCommentId());
     
     if (existLike != null) {
        likeRepository.delete(existLike);
        return null;
     }

     Like like = Like.builder()
             .user(user)
             .comment(comment)
             .build();

     return likeRepository.save(like);
 } 
 
 @Cacheable(value = "CommentLikeCounts" , key = "#CommentId" )
 public long getTotalLikesForComment(Long CommentId) {
	 return likeRepository.countByCommentId(CommentId);
 }
 
 @CacheEvict(value = "urunLikeCounts", key = "#dto.urunId")
 public LikeUrun likeUrun(LikeUrunDTO dto) {
	 User user = userRepository.findById(dto.getUserId()).orElseThrow(() -> new UserNotFoundException("Bulamadık"));
	 Urun urun = urunRepository.findById(dto.getUrunId()).orElseThrow(() -> new UrunNotFoundException("Bulamadık"));
	 
	 LikeUrun existLikeUrun = likeUrunRepository.findByUserIdAndUrunId(dto.getUserId(), dto.getUrunId());
	 
	 if(existLikeUrun != null) {
		 likeUrunRepository.delete(existLikeUrun);
		 return null;
	 } 
	 LikeUrun likeUrun = new LikeUrun();
	 likeUrun.setUser(user);
	 likeUrun.setUrun(urun);
	 
	 return likeUrunRepository.save(likeUrun);
	 
 }
 @Cacheable(value = "urunLikeCounts", key = "#urunId")
 public long getTotalLikesForUrun(Long urunId) {
     return likeUrunRepository.countByUrunId(urunId);
 }
}
