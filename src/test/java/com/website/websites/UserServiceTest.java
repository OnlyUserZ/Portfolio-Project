//Testler ChatGPT'ye Yazdırılmıştır

package com.website.websites;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import com.website.websites.dto.UserDTO;
import com.website.websites.entity.User;
import com.website.websites.repository.UserRepository;
import com.website.websites.service.UserService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void testUpdateUser_successfullyUpdatesUsername() {
        // given
        Long userId = 1L;
        User existingUser = new User();
        existingUser.setId(userId);
        existingUser.setUsername("oldUsername");

        UserDTO dto = new UserDTO();
        dto.setUsername("newUsername");

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));

        // when
        userService.updateUser(userId, dto);

        // then
        assertEquals("newUsername", existingUser.getUsername());
        verify(userRepository).save(existingUser); // save çağrıldı mı kontrol
    }

    @Test
    void testUpdateUser_userNotFound_throwsException() {
        // given
        Long userId = 99L;
        UserDTO dto = new UserDTO();
        dto.setUsername("any");

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // when & then
        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            userService.updateUser(userId, dto);
        });

        assertEquals("User Bulunamadı", ex.getMessage());
        verify(userRepository, never()).save(any());
    }
}
