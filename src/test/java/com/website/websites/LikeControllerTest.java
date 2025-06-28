//Testler ChatGPT'ye Yazdırılmıştır

package com.website.websites;

import com.website.websites.controller.LikeController;
import com.website.websites.dto.LikeDTO;
import com.website.websites.dto.LikeUrunDTO;
import com.website.websites.entity.Like;
import com.website.websites.entity.LikeUrun;
import com.website.websites.service.LikeService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LikeControllerTest {

    @Mock
    private LikeService likeService;

    @InjectMocks
    private LikeController likeController;

    @Test
    void testAddLike_shouldReturnLike() {
        // given
        LikeDTO dto = new LikeDTO();
        Like like = new Like();
        when(likeService.likeComment(dto)).thenReturn(like);

        // when
        ResponseEntity<Like> response = likeController.addLike(dto);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(like, response.getBody());
        verify(likeService).likeComment(dto);
    }

    @Test
    void testAddLike_shouldReturnNullBody_whenLikeIsNull() {
        // given
        LikeDTO dto = new LikeDTO();
        when(likeService.likeComment(dto)).thenReturn(null);

        // when
        ResponseEntity<Like> response = likeController.addLike(dto);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody());
        verify(likeService).likeComment(dto);
    }

    @Test
    void testAddLikeUrun_shouldReturnLikeUrun() {
        // given
        LikeUrunDTO dto = new LikeUrunDTO();
        LikeUrun likeUrun = new LikeUrun();
        when(likeService.likeUrun(dto)).thenReturn(likeUrun);

        // when
        ResponseEntity<LikeUrun> response = likeController.addLikeUrun(dto);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(likeUrun, response.getBody());
        verify(likeService).likeUrun(dto);
    }

    @Test
    void testAddLikeUrun_shouldReturnNullBody_whenLikeUrunIsNull() {
        // given
        LikeUrunDTO dto = new LikeUrunDTO();
        when(likeService.likeUrun(dto)).thenReturn(null);

        // when
        ResponseEntity<LikeUrun> response = likeController.addLikeUrun(dto);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody());
        verify(likeService).likeUrun(dto);
    }

    @Test
    void testGetTotalLikesForComment_shouldReturnCount() {
        // given
        Long commentId = 1L;
        Long expectedCount = 10L;
        when(likeService.getTotalLikesForComment(commentId)).thenReturn(expectedCount);

        // when
        ResponseEntity<Long> response = likeController.getTotalLikesForComment(commentId);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedCount, response.getBody());
        verify(likeService).getTotalLikesForComment(commentId);
    }
}
