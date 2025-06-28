package com.website.websites.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.website.websites.entity.About;

@Repository
public interface AboutRepository extends JpaRepository<About,Long> {
	Optional<About> existsById(int id);
	boolean existsBy();
	

}
