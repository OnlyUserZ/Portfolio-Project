package com.website.websites.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.website.websites.entity.Urun;

@Repository
public interface UrunRepository extends JpaRepository<Urun, Long> {
 
}
