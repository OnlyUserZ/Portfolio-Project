package com.website.websites.entity;


import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Topic {
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long id;

 private String name;

 

 @OneToMany(mappedBy = "topic", fetch = FetchType.LAZY)
 private List<Comment> comments;
}

