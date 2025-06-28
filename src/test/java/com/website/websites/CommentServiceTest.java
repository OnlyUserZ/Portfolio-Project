//Testler ChatGPT'ye Yazdırılmıştır

package com.website.websites;

import com.website.websites.dto.CommentDTO;
import com.website.websites.entity.Comment;
import com.website.websites.entity.Topic;
import com.website.websites.entity.User;
import com.website.websites.exception.BadRequestException;
import com.website.websites.exception.ResourceNotFoundException;
import com.website.websites.repository.CommentRepository;
import com.website.websites.repository.TopicRepository;
import com.website.websites.repository.UserRepository;
import com.website.websites.service.CommentService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TopicRepository topicRepository;

    @InjectMocks
    private CommentService commentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addComment_shouldSaveComment_whenUserAndTopicExist() {
        // Arrange
        User user = new User();
        user.setId(1L);
        Topic topic = new Topic();
        topic.setId(2L);

        CommentDTO dto = CommentDTO.builder()
                .userId(1L)
                .topicId(2L)
                .text("Harika bir yorum")
                .rating(5)
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(topicRepository.findById(2L)).thenReturn(Optional.of(topic));

        Comment comment = Comment.builder()
                .user(user)
                .topic(topic)
                .text(dto.getText())
                .rating(dto.getRating())
                .build();

        when(commentRepository.save(any(Comment.class))).thenReturn(comment);

        // Act
        Comment saved = commentService.addComment(dto);

        // Assert
        assertNotNull(saved);
        assertEquals("Harika bir yorum", saved.getText());
    }

    @Test
    void addComment_shouldThrowException_whenUserNotFound() {
        CommentDTO dto = CommentDTO.builder()
                .userId(1L)
                .topicId(2L)
                .text("yorum")
                .rating(4)
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () -> {
            commentService.addComment(dto);
        });

        assertEquals("User bulunamadı", ex.getMessage());
    }

    @Test
    void deleteComment_shouldDelete_whenExists() {
        Comment comment = new Comment();
        comment.setId(1L);

        when(commentRepository.findById(1L)).thenReturn(Optional.of(comment));

        commentService.deleteComment(1L);

        verify(commentRepository, times(1)).delete(comment);
    }

    @Test
    void deleteComment_shouldThrow_whenNotExists() {
        when(commentRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(BadRequestException.class, () -> {
            commentService.deleteComment(999L);
        });
    }

    @Test
    void updateComment_shouldUpdate_whenExists() {
        Comment comment = new Comment();
        comment.setId(1L);
        comment.setText("Eski yorum");
        comment.setRating(3);

        CommentDTO dto = CommentDTO.builder()
                .text("Yeni yorum")
                .rating(5)
                .build();

        when(commentRepository.findById(1L)).thenReturn(Optional.of(comment));
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);

        commentService.updateComment(1L, dto);

        assertEquals("Yeni yorum", comment.getText());
        assertEquals(5, comment.getRating());
    }

    @Test
    void updateComment_shouldThrow_whenNotExists() {
        CommentDTO dto = CommentDTO.builder().text("x").rating(1).build();
        when(commentRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            commentService.updateComment(999L, dto);
        });
    }
}
