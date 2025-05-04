package org.example.booking_place.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.booking_place.model.Workspace;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class WorkspaceRequest {
    private String name;

    public Workspace toWorkspace(){
        Workspace workspace = new Workspace();
        workspace.setName(name);

        return workspace;
    }
}
