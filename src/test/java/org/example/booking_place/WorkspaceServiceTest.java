package org.example.booking_place;

import org.example.booking_place.dto.WorkspaceRequest;
import org.example.booking_place.exeption.HttpStatusException;
import org.example.booking_place.model.Workspace;
import org.example.booking_place.repository.BookingRepository;
import org.example.booking_place.repository.WorkspaceRepository;
import org.example.booking_place.service.WorkspaceService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WorkspaceServiceTest {

    @Mock
    private WorkspaceRepository workspaceRepository;

    @Mock
    private BookingRepository bookingRepository;

    @InjectMocks
    private WorkspaceService workspaceService;

    @Test
    void createWorkspace_Success() {
        WorkspaceRequest request = new WorkspaceRequest("Workspace 1");
        when(workspaceRepository.existsByName(any())).thenReturn(false);
        when(workspaceRepository.save(any())).thenReturn(new Workspace(1, "Workspace 1"));

        Workspace result = workspaceService.create(request);

        assertNotNull(result);
        assertEquals("Workspace 1", result.getName());
        verify(workspaceRepository, times(1)).save(any());
    }

    @Test
    void createWorkspace_WhenExists_ThrowsException() {
        WorkspaceRequest request = new WorkspaceRequest("Workspace 1");
        when(workspaceRepository.existsByName(any())).thenReturn(true);

        HttpStatusException exception = assertThrows(HttpStatusException.class,
                () -> workspaceService.create(request));

        assertEquals("Workspace already exist with name: Workspace 1", exception.getMessage());
    }

    @Test
    void deleteWorkspace_Success() {
        when(workspaceRepository.existsById(1)).thenReturn(true);
        doNothing().when(bookingRepository).deleteAllByWorkspace_Id(1);
        doNothing().when(workspaceRepository).deleteById(1);

        assertDoesNotThrow(() -> workspaceService.delete(1));
        verify(bookingRepository, times(1)).deleteAllByWorkspace_Id(1);
        verify(workspaceRepository, times(1)).deleteById(1);
    }
}