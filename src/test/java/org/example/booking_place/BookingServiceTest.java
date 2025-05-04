package org.example.booking_place;

import org.example.booking_place.dto.BookingRequest;
import org.example.booking_place.exeption.HttpStatusException;
import org.example.booking_place.model.Booking;
import org.example.booking_place.model.User;
import org.example.booking_place.model.Workspace;
import org.example.booking_place.repository.BookingRepository;
import org.example.booking_place.repository.UserRepository;
import org.example.booking_place.repository.WorkspaceRepository;
import org.example.booking_place.service.BookingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private WorkspaceRepository workspaceRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private BookingService bookingService;

    @Test
    void createBooking_Success() {
        LocalDateTime now = LocalDateTime.now();
        BookingRequest request = new BookingRequest(1, 1, now, now.plusHours(1));
        
        when(bookingRepository.findByWorkspaceIdAndTimeRange(any(), any(), any()))
                .thenReturn(Collections.emptyList());
        when(userRepository.findById(any())).thenReturn(Optional.of(new User()));
        when(workspaceRepository.findById(any())).thenReturn(Optional.of(new Workspace()));
        when(bookingRepository.save(any())).thenReturn(new Booking());

        Booking result = bookingService.createBooking(request);

        assertNotNull(result);
        verify(bookingRepository, times(1)).save(any());
    }

    @Test
    void createBooking_WhenTimeInvalid_ThrowsException() {
        LocalDateTime now = LocalDateTime.now();
        BookingRequest request = new BookingRequest(1, 1, now, now.minusHours(1));

        HttpStatusException exception = assertThrows(HttpStatusException.class,
                () -> bookingService.createBooking(request));

        assertEquals("Start time must be before end time", exception.getMessage());
    }

    @Test
    void createBooking_WhenWorkspaceBooked_ThrowsException() {
        LocalDateTime now = LocalDateTime.now();
        BookingRequest request = new BookingRequest(1, 1, now, now.plusHours(1));
        
        when(bookingRepository.findByWorkspaceIdAndTimeRange(any(), any(), any()))
                .thenReturn(List.of(new Booking()));

        HttpStatusException exception = assertThrows(HttpStatusException.class,
                () -> bookingService.createBooking(request));

        assertEquals("Workspace is already booked for the selected time period", exception.getMessage());
    }

    @Test
    void deleteBooking_Success() {
        doNothing().when(bookingRepository).deleteById(any());
        
        assertDoesNotThrow(() -> bookingService.deleteBooking(1));
        verify(bookingRepository, times(1)).deleteById(1);
    }

    @Test
    void getAllBookingByUserId_Success() {
        when(userRepository.findById(any())).thenReturn(Optional.of(new User()));
        when(bookingRepository.findByUser_IdAndStartTimeBefore(any(), any()))
                .thenReturn(List.of(new Booking()));

        List<Booking> result = bookingService.getAllBookingByUserId(1);

        assertFalse(result.isEmpty());
        verify(bookingRepository, times(1)).findByUser_IdAndStartTimeBefore(any(), any());
    }
}