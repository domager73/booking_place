package org.example.booking_place.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.booking_place.exeption.HttpStatusException;
import org.example.booking_place.model.Booking;
import org.example.booking_place.model.User;
import org.example.booking_place.model.Workspace;
import org.example.booking_place.utils.Const;
import org.springframework.http.HttpStatus;

import java.time.Duration;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingRequest {
    private Integer userId;

    private Integer workspaceId;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    public Booking toBooking(User user, Workspace workspace){
        validateBookingDuration();

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setWorkspace(workspace);
        booking.setEndTime(endTime);
        booking.setStartTime(startTime);

        return booking;
    }

    private void validateBookingDuration() {
        if (startTime == null || endTime == null) {
            throw new HttpStatusException("Start time and end time must be specified",
                    HttpStatus.BAD_REQUEST);
        }

        if (startTime.isAfter(endTime)) {
            throw new HttpStatusException("End time must be after start time",
                    HttpStatus.BAD_REQUEST);
        }

        Duration duration = Duration.between(startTime, endTime);
        if (duration.toHours() > Const.MAX_BOOKING_HOURS) {
            throw new HttpStatusException("Booking duration cannot exceed 2 hours",
                    HttpStatus.BAD_REQUEST);
        }
    }
}
