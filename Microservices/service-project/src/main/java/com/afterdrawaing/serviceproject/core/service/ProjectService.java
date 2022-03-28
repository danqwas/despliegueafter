package com.afterdrawaing.serviceproject.core.service;

import com.afterdrawaing.serviceproject.core.entity.Project;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface ProjectService {
    List<Project> getAllProjects();

    Optional<Project> getProjectById(Long projectId);



    Project updateProject(Long projectId, Project projectRequest);

    Project saveProject(Project project);

    ResponseEntity<?> deleteProject(Long projectId);
}
