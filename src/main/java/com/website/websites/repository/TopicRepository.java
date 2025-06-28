package com.website.websites.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.website.websites.entity.Topic;

public interface TopicRepository extends JpaRepository<Topic, Long> {
}
