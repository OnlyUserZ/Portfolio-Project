package com.website.websites.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AboutDTO {

	@NotNull
	private Long id;
	
	@NotBlank
	@Size(max = 4000 , min = 1 , message = "1 ile 4000 arasında karakter gir")
	private String text;
	
	@NotBlank
	@Size(max = 100 , min = 1 , message = "1 ile 100 arasında karakter gir")
	private String title;
	
	private String url;
}
