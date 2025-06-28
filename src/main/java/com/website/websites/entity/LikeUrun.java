package com.website.websites.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor

@Table(name = "likesUrun",
uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "urun_id"})})

public class LikeUrun {
	
	   @Id
	   @GeneratedValue(strategy = GenerationType.IDENTITY)
       private Long id;
	   
	   @ManyToOne
	   @JoinColumn(name = "user_id")
	   private User user;
	   
	   @ManyToOne
	   @JoinColumn(name = "urun_id")
	   private Urun urun;
}
