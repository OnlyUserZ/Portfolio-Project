package com.website.websites.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long id;

 @Column(length = 1000)
 private String text;

 private int rating; // 1-5 arası

 @ManyToOne(fetch = FetchType.LAZY)
 @JoinColumn(name = "user_id")
 private User user;

 @ManyToOne(fetch = FetchType.LAZY)
 @JoinColumn(name = "Topic_id")
 private Topic topic;

 @OneToMany(mappedBy = "comment", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
 private List<Like> likes;
}
