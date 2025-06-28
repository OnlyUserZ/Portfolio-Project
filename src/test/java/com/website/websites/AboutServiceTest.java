//Testler ChatGPT'ye Yazdırılmıştır

package com.website.websites;

import com.website.websites.dto.AboutDTO;
import com.website.websites.entity.About;
import com.website.websites.repository.AboutRepository;
import com.website.websites.service.AboutService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AboutServiceTest {

    @Mock
    private AboutRepository aboutRepository;

    @InjectMocks
    private AboutService aboutService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createAbout_shouldSaveAbout_whenNoExistingAbout() {
        // given
        when(aboutRepository.count()).thenReturn(0L);

        AboutDTO dto = new AboutDTO();
        dto.setText("Hakkımızda metni");
        dto.setTitle("Başlık");
        dto.setUrl("https://example.com");

        About about = new About();
        about.setId(1L);
        about.setText(dto.getText());
        about.setTitle(dto.getTitle());
        about.setUrl(dto.getUrl());

        when(aboutRepository.save(any(About.class))).thenReturn(about);

        // when
        About result = aboutService.createAbout(dto);

        // then
        assertNotNull(result);
        assertEquals(dto.getText(), result.getText());
    }

    @Test
    void createAbout_shouldThrowException_whenAboutExists() {
        when(aboutRepository.count()).thenReturn(1L);
        AboutDTO dto = new AboutDTO();

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            aboutService.createAbout(dto);
        });

        assertEquals("Zaten About Var", ex.getMessage());
    }

    @Test
    void getAbout_shouldReturnDTO_whenExists() {
        About about = new About();
        about.setId(1L);
        about.setText("text");
        about.setTitle("title");
        about.setUrl("url");

        when(aboutRepository.findAll()).thenReturn(List.of(about));

        AboutDTO dto = aboutService.getAbout();

        assertNotNull(dto);
        assertEquals("text", dto.getText());
    }

    @Test
    void getAbout_shouldThrowException_whenNotFound() {
        when(aboutRepository.findAll()).thenReturn(List.of());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            aboutService.getAbout();
        });

        assertEquals("About bulunamadı", ex.getMessage());
    }

    @Test
    void updateAbout_shouldUpdateAndReturnDTO_whenFound() {
        About about = new About();
        about.setId(1L);
        about.setText("old");
        about.setTitle("old");
        about.setUrl("old");

        AboutDTO dto = new AboutDTO();
        dto.setId(1L);
        dto.setText("new");
        dto.setTitle("new");
        dto.setUrl("new");

        when(aboutRepository.findById(1L)).thenReturn(Optional.of(about));
        when(aboutRepository.save(any(About.class))).thenReturn(about);

        AboutDTO result = aboutService.updateAbout(dto);

        assertEquals("new", result.getText());
    }

    @Test
    void updateAbout_shouldThrowException_whenNotFound() {
        AboutDTO dto = new AboutDTO();
        dto.setId(2L);

        when(aboutRepository.findById(2L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            aboutService.updateAbout(dto);
        });

        assertEquals("About bulunamadı", ex.getMessage());
    }

    @Test
    void deleteAbout_shouldDelete_whenFound() {
        About about = new About();
        about.setId(1L);

        when(aboutRepository.findById(1L)).thenReturn(Optional.of(about));

        aboutService.deleteAbout(1L);

        verify(aboutRepository, times(1)).delete(about);
    }

    @Test
    void deleteAbout_shouldThrowException_whenNotFound() {
        when(aboutRepository.findById(5L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            aboutService.deleteAbout(5L);
        });

        assertEquals("bulamadık", ex.getMessage());
    }
}
