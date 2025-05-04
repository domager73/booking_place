package org.example.booking_place.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.booking_place.dto.BookingRequest;
import org.example.booking_place.model.Booking;
import org.example.booking_place.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/booking")
@Tag(name = "Управление бронированием", description = "API для работы с бронированием")
public class BookingController {
    private BookingService bookingService;

    @PostMapping
    @Operation(
            summary = "Добавить новое бронирование",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Бронирование успешно создано"),
                    @ApiResponse(responseCode = "409", description = "Неверные данные"),
                    @ApiResponse(responseCode = "500", description = "Ошибка сервера")
            }
    )
    public Booking create(@Valid @RequestBody BookingRequest bookingRequest) {
        return bookingService.createBooking(bookingRequest);
    }

    @DeleteMapping("/{bookingId}")
    @Operation(
            summary = "Удалить бронь",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Бронь успешно удалена"),
                    @ApiResponse(responseCode = "409", description = "Неверные данные"),
                    @ApiResponse(responseCode = "500", description = "Ошибка сервера")
            }
    )
    public ResponseEntity<Void> delete(@PathVariable Integer bookingId) {
        bookingService.deleteBooking(bookingId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/by-user/{userId}")
    @Operation(
            summary = "Показ всех броней для пользователя",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Все успешно прошло"),
                    @ApiResponse(responseCode = "409", description = "Неверные данные"),
                    @ApiResponse(responseCode = "500", description = "Ошибка сервера")
            }
    )
    public ResponseEntity<List<Booking>> getAllByUser(@PathVariable Integer userId) {
        return ResponseEntity.ok(bookingService.getAllBookingByUserId(userId));
    }

    @GetMapping("/by-workspace/{workspaceId}")
    @Operation(
            summary = "Показ всех броней для места",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Все успешно прошло"),
                    @ApiResponse(responseCode = "409", description = "Неверные данные"),
                    @ApiResponse(responseCode = "500", description = "Ошибка сервера")
            }
    )
    public ResponseEntity<List<Booking>> getAllByWorkspace(@PathVariable Integer workspaceId) {
        return ResponseEntity.ok(bookingService.getAllBookingByWorkspaceId(workspaceId));
    }

    @GetMapping
    @Operation(
            summary = "Показ всех броней",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Все успешно прошло"),
                    @ApiResponse(responseCode = "409", description = "Неверные данные"),
                    @ApiResponse(responseCode = "500", description = "Ошибка сервера")
            }
    )
    public ResponseEntity<List<Booking>> getAll() {
        return ResponseEntity.ok(bookingService.getAllBooking());
    }
}
