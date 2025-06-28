package com.website.websites.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class User {
	
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long id;

 private String username;

 @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
 private List<Comment> comments;

 @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
 private List<Like> likes;
 
}
