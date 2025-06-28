package com.website.websites.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LikeUrunDTO {
	
	@NotNull
	private Long urunId;
	
	@NotNull
	private Long userId;
	

}
