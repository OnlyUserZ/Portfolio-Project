package com.website.websites.entity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor

public class Urun {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	private String marka;
	
	private double fiyat;
	
	@ManyToMany
	@JoinTable(name = "Urun_Kategori",
	   joinColumns = @JoinColumn(name = "urun_id"),
	   inverseJoinColumns = @JoinColumn(name = "kategori_id")
			)
	private Set<Kategori> kategoriler;
	
	@OneToMany(mappedBy = "urun" , cascade = CascadeType.ALL )
	private Set<LikeUrun> likes;

}
