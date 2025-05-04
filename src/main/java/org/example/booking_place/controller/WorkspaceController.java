package org.example.booking_place.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.booking_place.dto.UserRequest;
import org.example.booking_place.dto.WorkspaceRequest;
import org.example.booking_place.model.User;
import org.example.booking_place.model.Workspace;
import org.example.booking_place.service.WorkspaceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/workspace")
@Tag(name = "Управление комнатами", description = "API для работы с комнатами")
public class WorkspaceController {
    private WorkspaceService workspaceService;

    @PostMapping
    @Operation(
            summary = "Добавить новое место",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Место успешно создан"),
                    @ApiResponse(responseCode = "409", description = "Неверные данные"),
                    @ApiResponse(responseCode = "500", description = "Ошибка сервера")
            }
    )
    public Workspace create(@Valid @RequestBody WorkspaceRequest workspaceRequest) {
        return workspaceService.create(workspaceRequest);
    }

    @DeleteMapping("/{workspaceId}")
    @Operation(
            summary = "Удалить место",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Место успешно удалено"),
                    @ApiResponse(responseCode = "409", description = "Неверные данные"),
                    @ApiResponse(responseCode = "500", description = "Ошибка сервера")
            }
    )
    public ResponseEntity<Void> delete(@PathVariable Integer workspaceId) {
        workspaceService.delete(workspaceId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    @Operation(
            summary = "Обновление место",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Место успешно обновлено"),
                    @ApiResponse(responseCode = "409", description = "Неверные данные"),
                    @ApiResponse(responseCode = "500", description = "Ошибка сервера")
            }
    )
    public ResponseEntity<Workspace> update(@Valid @RequestBody WorkspaceRequest workspaceRequest) {
        return ResponseEntity.ok(workspaceService.update(workspaceRequest));
    }

    @GetMapping
    @Operation(
            summary = "Получить все места",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Место успешно получено"),
                    @ApiResponse(responseCode = "409", description = "Неверные данные"),
                    @ApiResponse(responseCode = "500", description = "Ошибка сервера")
            }
    )
    public ResponseEntity<List<Workspace>> update() {
        return ResponseEntity.ok(workspaceService.getAll());
    }
}
