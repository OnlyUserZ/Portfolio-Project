package com.website.websites.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.website.websites.entity.LikeUrun;

@Repository

public interface LikeUrunRepository extends JpaRepository<LikeUrun, Long>{
	
	boolean existsByUser_idAndUrun_id(Long userId , Long urunId);
	
	LikeUrun findByUserIdAndUrunId(Long userId , Long urunId);
	
	long countByUrunId(Long urunId);

}
