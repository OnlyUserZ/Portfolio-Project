//Testler ChatGPT'ye Yazdırılmıştır

package com.website.websites;

import com.website.websites.controller.AboutController;
import com.website.websites.dto.AboutDTO;
import com.website.websites.entity.About;
import com.website.websites.service.AboutService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AboutControllerTest {

    @Mock
    private AboutService aboutService;

    @InjectMocks
    private AboutController aboutController;

    @Test
    void testGetAbout_shouldReturnDTO() {
        // given
        AboutDTO dto = new AboutDTO();
        dto.setId(1L);
        dto.setText("Hakkımızda yazısı");
        dto.setTitle("Hakkımızda");
        dto.setUrl("https://example.com/about");

        when(aboutService.getAbout()).thenReturn(dto);

        // when
        AboutDTO result = aboutController.getAbout();

        // then
        assertNotNull(result);
        assertEquals("Hakkımızda yazısı", result.getText());
        assertEquals("Hakkımızda", result.getTitle());
        assertEquals("https://example.com/about", result.getUrl());
        verify(aboutService).getAbout();
    }

    @Test
    void testCreateAbout_shouldReturnCreatedEntity() {
        // given
        AboutDTO dto = new AboutDTO();
        dto.setText("Yeni içerik");
        dto.setTitle("Yeni Başlık");
        dto.setUrl("https://example.com/new-about");

        About about = new About();
        about.setId(1L);
        about.setText("Yeni içerik");
        about.setTitle("Yeni Başlık");
        about.setUrl("https://example.com/new-about");

        when(aboutService.createAbout(dto)).thenReturn(about);

        // when
        About result = aboutController.createAbout(dto);

        // then
        assertNotNull(result);
        assertEquals("Yeni içerik", result.getText());
        assertEquals("Yeni Başlık", result.getTitle());
        assertEquals("https://example.com/new-about", result.getUrl());
        verify(aboutService).createAbout(dto);
    }

    @Test
    void testUpdateAbout_shouldReturnUpdatedDTO() {
        // given
        Long id = 1L;
        AboutDTO dto = new AboutDTO();
        dto.setId(id);
        dto.setText("Güncellenmiş içerik");
        dto.setTitle("Güncellenmiş Başlık");
        dto.setUrl("https://example.com/updated-about");

        when(aboutService.updateAbout(dto)).thenReturn(dto);

        // when
        AboutDTO result = aboutController.updateAbout(id, dto);

        // then
        assertNotNull(result);
        assertEquals("Güncellenmiş içerik", result.getText());
        assertEquals("Güncellenmiş Başlık", result.getTitle());
        assertEquals("https://example.com/updated-about", result.getUrl());
        verify(aboutService).updateAbout(dto);
    }

    @Test
    void testUpdateAbout_shouldThrow_whenIdMismatch() {
        // given
        Long pathId = 1L;
        AboutDTO dto = new AboutDTO();
        dto.setId(2L); // Uyuşmayan ID

        // then
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            aboutController.updateAbout(pathId, dto);
        });

        assertEquals("ID path parametresi ile DTO ID uyuşmuyor", thrown.getMessage());
        verify(aboutService, never()).updateAbout(any());
    }

    @Test
    void testDeleteAbout_shouldCallService() {
        // given
        Long id = 1L;

        // when
        aboutController.deleteAbout(id);

        // then
        verify(aboutService).deleteAbout(id);
    }
}
