package com.website.websites.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDTO {
	@NotNull
	private Long id;
	
	@Size(min = 5 , max = 35 , message = "5 ve 35 karakter arasında olmalı")
	@NotBlank
	@Pattern(regexp = "^[a-zA-Z]+$", message = "Kullanıcı adı sadece İngilizce harfler içerebilir.")
	private String username;

}
