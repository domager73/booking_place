package org.example.booking_place.service;

import lombok.AllArgsConstructor;
import org.example.booking_place.dto.WorkspaceRequest;
import org.example.booking_place.exeption.HttpStatusException;
import org.example.booking_place.model.Workspace;
import org.example.booking_place.repository.BookingRepository;
import org.example.booking_place.repository.WorkspaceRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class WorkspaceService {
    private WorkspaceRepository workspaceRepository;
    private BookingRepository bookingRepository;

    public Workspace create(WorkspaceRequest workspaceRequest) {
        if (workspaceRepository.existsByName(workspaceRequest.getName())) {
            throw new HttpStatusException("Workspace already exist with name: " + workspaceRequest.getName(), HttpStatus.CONFLICT);
        }

        return workspaceRepository.save(workspaceRequest.toWorkspace());
    }

    public List<Workspace> getAll() {
        return workspaceRepository.findAll();
    }

    public Workspace update(WorkspaceRequest workspaceRequest) {
        if (!workspaceRepository.existsByName(workspaceRequest.getName())) {
            throw new HttpStatusException("Workspace is not exist with name: " + workspaceRequest.getName(), HttpStatus.CONFLICT);
        }

        return workspaceRepository.save(workspaceRequest.toWorkspace());
    }

    public void delete(Integer workspaceId) {
        if (!workspaceRepository.existsById(workspaceId)) {
            throw new HttpStatusException("Workspace is not exist with id: " + workspaceId, HttpStatus.CONFLICT);
        }

        bookingRepository.deleteAllByWorkspace_Id(workspaceId);

        workspaceRepository.deleteById(workspaceId);
    }
}
