package com.website.websites.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.website.websites.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
