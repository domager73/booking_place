package org.example.booking_place.service;

import lombok.AllArgsConstructor;
import org.example.booking_place.dto.BookingRequest;
import org.example.booking_place.exeption.HttpStatusException;
import org.example.booking_place.model.Booking;
import org.example.booking_place.model.User;
import org.example.booking_place.model.Workspace;
import org.example.booking_place.repository.BookingRepository;
import org.example.booking_place.repository.UserRepository;
import org.example.booking_place.repository.WorkspaceRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class BookingService {
    private BookingRepository bookingRepository;
    private WorkspaceRepository workspaceRepository;
    private UserRepository userRepository;

    public Booking createBooking(BookingRequest bookingRequest){
        if (bookingRequest.getStartTime().isEqual(bookingRequest.getEndTime()) ||
                bookingRequest.getStartTime().isAfter(bookingRequest.getEndTime())) {
            throw new HttpStatusException(
                    "Start time must be before end time",
                    HttpStatus.BAD_REQUEST);
        }

        List<Booking> conflictingBookings = bookingRepository
                .findByWorkspaceIdAndTimeRange(
                        bookingRequest.getWorkspaceId(),
                        bookingRequest.getStartTime(),
                        bookingRequest.getEndTime());

        if (!conflictingBookings.isEmpty()) {
            throw new HttpStatusException(
                    "Workspace is already booked for the selected time period",
                    HttpStatus.CONFLICT);
        }

        User user = userRepository.findById(bookingRequest.getUserId())
                .orElseThrow(() -> new HttpStatusException("User not found", HttpStatus.CONFLICT));

        Workspace workspace = workspaceRepository.findById(bookingRequest.getWorkspaceId())
                .orElseThrow(() -> new HttpStatusException("Workspace not found", HttpStatus.CONFLICT));

        return bookingRepository.save(bookingRequest.toBooking(user, workspace));
    }

    public void deleteBooking(Integer bookingId){
        bookingRepository.deleteById(bookingId);
    }

    public List<Booking> getAllBookingByUserId(Integer userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new HttpStatusException("User not found", HttpStatus.CONFLICT));

        return bookingRepository.findByUser_IdAndStartTimeAfter(userId, LocalDateTime.now());
    }

    public List<Booking> getAllBookingByWorkspaceId(Integer workspaceId){
        Workspace workspace = workspaceRepository.findById(workspaceId)
                .orElseThrow(() -> new HttpStatusException("Workspace not found", HttpStatus.CONFLICT));

        return bookingRepository.findByWorkspace_IdAndStartTimeAfter(workspaceId, LocalDateTime.now());
    }

    public List<Booking> getAllBooking(){
        return bookingRepository.findByStartTimeAfter(LocalDateTime.now());
    }
}
