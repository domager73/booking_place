package org.example.booking_place;

import org.example.booking_place.dto.UserRequest;
import org.example.booking_place.exeption.HttpStatusException;
import org.example.booking_place.model.User;
import org.example.booking_place.repository.UserRepository;
import org.example.booking_place.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void signIn_Success() {
        UserRequest request = new UserRequest("test@email.com", "password", "Test User");
        when(userRepository.findUserByEmail(any())).thenReturn(null);
        when(userRepository.save(any())).thenReturn(new User(1, "test@email.com", "password", "Test User", "user"));

        User result = userService.singIn(request);

        assertNotNull(result);
        assertEquals("test@email.com", result.getEmail());
        verify(userRepository, times(1)).save(any());
    }

    @Test
    void signIn_WhenUserExists_ThrowsException() {
        UserRequest request = new UserRequest("test@email.com", "password", "Test User");
        when(userRepository.findUserByEmail(any())).thenReturn(new User());

        HttpStatusException exception = assertThrows(HttpStatusException.class,
                () -> userService.singIn(request));

        assertEquals("User already exist with email: test@email.com", exception.getMessage());
    }

    @Test
    void logIn_Success() {
        UserRequest request = new UserRequest("test@email.com", "password", null);
        User user = new User(1, "test@email.com", "password", "Test User", "user");
        when(userRepository.findUserByEmail(any())).thenReturn(user);

        User result = userService.logIn(request);

        assertNotNull(result);
        assertEquals("test@email.com", result.getEmail());
    }

    @Test
    void logIn_WhenUserNotFound_ThrowsException() {
        UserRequest request = new UserRequest("test@email.com", "password", null);
        when(userRepository.findUserByEmail(any())).thenReturn(null);

        HttpStatusException exception = assertThrows(HttpStatusException.class,
                () -> userService.logIn(request));

        assertEquals("User is not exist with email: test@email.com", exception.getMessage());
    }
}