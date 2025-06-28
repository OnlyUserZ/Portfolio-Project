//Testler ChatGPT'ye Yazdırılmıştır

package com.website.websites;

import com.website.websites.controller.UserController;
import com.website.websites.dto.UserDTO;
import com.website.websites.entity.User;
import com.website.websites.service.UserService;

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
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    void testGetUserById_shouldReturnUserAndStatus200() {
        // given
        Long userId = 1L;
        User mockUser = new User();
        mockUser.setId(userId);
        mockUser.setUsername("kenan");

        when(userService.getUserById(userId)).thenReturn(mockUser);

        // when
        ResponseEntity<User> response = userController.getUserById(userId);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(mockUser, response.getBody());
        verify(userService).getUserById(userId);
    }

    @Test
    void testUpdateUser_shouldReturnStatus200() {
        // given
        Long userId = 1L;
        UserDTO dto = new UserDTO();
        dto.setUsername("updatedName");

        // when
        ResponseEntity<Void> response = userController.updateUser(userId, dto);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(userService).updateUser(userId, dto);
    }

    @Test
    void testDeleteUser_shouldReturnStatus200() {
        // given
        Long userId = 1L;

        // when
        ResponseEntity<Void> response = userController.deleteUser(userId);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(userService).deleteUser(userId);
    }
}
