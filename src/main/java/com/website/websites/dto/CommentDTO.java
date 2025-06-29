package com.website.websites.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@Builder
public class CommentDTO {
 @NotBlank(message = "Yorum metni boş olamaz")
 @Size(max = 1000, message = "Yorum 1000 karakterden fazla olamaz")
 private String text;

 @Min(value = 1, message = "Puan 1 ile 5 arasında olmalı")
 @Max(value = 5, message = "Puan 1 ile 5 arasında olmalı")
 private int rating;

 @NotNull(message = "UserId boş olamaz")
 private Long userId;

 @NotNull(message = "ProductId boş olamaz")
 private Long topicId;
}
