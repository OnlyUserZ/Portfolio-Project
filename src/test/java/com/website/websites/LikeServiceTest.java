//Testler ChatGPT'ye Yazdırılmıştır

package com.website.websites;

import com.website.websites.dto.LikeDTO;
import com.website.websites.dto.LikeUrunDTO;
import com.website.websites.entity.*;
import com.website.websites.exception.ResourceNotFoundException;
import com.website.websites.exception.UrunNotFoundException;
import com.website.websites.exception.UserNotFoundException;
import com.website.websites.repository.*;
import com.website.websites.service.LikeService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LikeServiceTest {

    @InjectMocks
    private LikeService likeService;

    @Mock private LikeRepository likeRepository;
    @Mock private UserRepository userRepository;
    @Mock private CommentRepository commentRepository;
    @Mock private LikeUrunRepository likeUrunRepository;
    @Mock private UrunRepository urunRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // ----------- LIKE COMMENT -----------

    @Test
    void likeComment_shouldLikeComment_whenNotLikedBefore() {
        
        LikeDTO dto = new LikeDTO(1L, 2L);
        User user = new User(); user.setId(1L);
        Comment comment = new Comment(); comment.setId(2L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(commentRepository.findById(2L)).thenReturn(Optional.of(comment));
        when(likeRepository.findByUserIdAndCommentId(1L, 2L)).thenReturn(null);

        
        Like result = likeService.likeComment(dto);

        
        assertNotNull(result);
        assertEquals(user, result.getUser());
        assertEquals(comment, result.getComment());
        verify(likeRepository).save(any(Like.class));
    }

    @Test
    void likeComment_shouldRemoveLike_whenAlreadyLiked() {
        LikeDTO dto = new LikeDTO(1L, 2L);
        Like existing = new Like();
        when(userRepository.findById(1L)).thenReturn(Optional.of(new User()));
        when(commentRepository.findById(2L)).thenReturn(Optional.of(new Comment()));
        when(likeRepository.findByUserIdAndCommentId(1L, 2L)).thenReturn(existing);

        Like result = likeService.likeComment(dto);

        assertNull(result);
        verify(likeRepository).delete(existing);
    }

    @Test
    void likeComment_shouldThrowException_whenUserNotFound() {
        LikeDTO dto = new LikeDTO(1L, 2L);
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> likeService.likeComment(dto));
    }

    @Test
    void likeComment_shouldThrowException_whenCommentNotFound() {
        LikeDTO dto = new LikeDTO(1L, 2L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(new User()));
        when(commentRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> likeService.likeComment(dto));
    }

    // ----------- TOTAL LIKE COMMENT -----------

    @Test
    void getTotalLikesForComment_shouldReturnCount() {
        when(likeRepository.countByCommentId(2L)).thenReturn(10L);
        long count = likeService.getTotalLikesForComment(2L);
        assertEquals(10L, count);
    }

    // ----------- LIKE URUN -----------

    @Test
    void likeUrun_shouldLike_whenNotLikedBefore() {
        LikeUrunDTO dto = new LikeUrunDTO(1L, 2L);
        User user = new User(); user.setId(1L);
        Urun urun = new Urun(); urun.setId(2L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(urunRepository.findById(2L)).thenReturn(Optional.of(urun));
        when(likeUrunRepository.findByUserIdAndUrunId(1L, 2L)).thenReturn(null);

        LikeUrun result = likeService.likeUrun(dto);

        assertNotNull(result);
        assertEquals(user, result.getUser());
        assertEquals(urun, result.getUrun());
        verify(likeUrunRepository).save(any(LikeUrun.class));
    }

    @Test
    void likeUrun_shouldUnlike_whenAlreadyLiked() {
        LikeUrunDTO dto = new LikeUrunDTO(1L, 2L);
        LikeUrun existing = new LikeUrun();
        when(userRepository.findById(1L)).thenReturn(Optional.of(new User()));
        when(urunRepository.findById(2L)).thenReturn(Optional.of(new Urun()));
        when(likeUrunRepository.findByUserIdAndUrunId(1L, 2L)).thenReturn(existing);

        LikeUrun result = likeService.likeUrun(dto);

        assertNull(result);
        verify(likeUrunRepository).delete(existing);
    }

    @Test
    void likeUrun_shouldThrow_whenUserNotFound() {
        LikeUrunDTO dto = new LikeUrunDTO(1L, 2L);
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> likeService.likeUrun(dto));
    }

    @Test
    void likeUrun_shouldThrow_whenUrunNotFound() {
        LikeUrunDTO dto = new LikeUrunDTO(1L, 2L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(new User()));
        when(urunRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(UrunNotFoundException.class, () -> likeService.likeUrun(dto));
    }

    // ----------- TOTAL LIKE URUN -----------

    @Test
    void getTotalLikesForUrun_shouldReturnCount() {
        when(likeUrunRepository.countByUrunId(5L)).thenReturn(42L);
        long count = likeService.getTotalLikesForUrun(5L);
        assertEquals(42L, count);
    }
}

