package com.website.websites.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LikeDTO {
 @NotNull(message = "UserId boş olamaz")
 private Long userId;

 @NotNull(message = "CommentId boş olamaz")
 private Long commentId;
}

