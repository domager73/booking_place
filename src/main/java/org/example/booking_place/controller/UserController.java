package org.example.booking_place.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.booking_place.dto.UserRequest;
import org.example.booking_place.model.User;
import org.example.booking_place.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
@Tag(name = "Управление пользователями", description = "API для работы с пользователями")
public class UserController {
    private UserService userService;

    @PostMapping("/sign-in")
    @Operation(
            summary = "Добавить нового пользователя",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Пользователь успешно создан"),
                    @ApiResponse(responseCode = "409", description = "Неверные данные"),
                    @ApiResponse(responseCode = "500", description = "Ошибка сервера")
            }
    )
    public User signIn(@Valid @RequestBody UserRequest userRequest) {
        return userService.singIn(userRequest);
    }

    @PostMapping("/login")
    @Operation(
            summary = "Вход пользователя",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Пользователь успешно вошел"),
                    @ApiResponse(responseCode = "409", description = "Неверные данные"),
                    @ApiResponse(responseCode = "500", description = "Ошибка сервера")
            }
    )
    public ResponseEntity<User> logIn(@Valid @RequestBody UserRequest user) {
        return ResponseEntity.ok(userService.logIn(user));
    }
}
