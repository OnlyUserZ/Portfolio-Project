package com.website.websites.dto;

import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UrunDTO {
	
	@NotNull
	private Set<Long> kategoriId;
	
	@NotBlank(message = "Ürün adı boş olamaz")
	@Size(max = 100 , min = 1 )
	private String name;
	
	@NotNull
	private double fiyat;
	
	@NotBlank
	private String Marka;
	

}
