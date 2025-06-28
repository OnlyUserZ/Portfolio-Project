package com.website.websites.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "likes",
    uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "comment_id"})})

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Like {
	
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long id;

 @ManyToOne(fetch = FetchType.LAZY)
 @JoinColumn(name = "user_id")
 private User user;

 @ManyToOne(fetch = FetchType.LAZY)
 @JoinColumn(name = "comment_id")
 private Comment comment;
 
 @ManyToOne
 @JoinColumn(name = "urun_id")
 private Urun urunler;
}
