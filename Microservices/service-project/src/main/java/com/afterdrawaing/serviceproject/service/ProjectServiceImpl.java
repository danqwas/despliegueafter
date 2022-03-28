package com.afterdrawaing.serviceproject.service;

import com.afterdrawaing.serviceproject.core.entity.Project;
import com.afterdrawaing.serviceproject.core.repository.ProjectRepository;
import com.afterdrawaing.serviceproject.core.service.ProjectService;
import com.afterdrawaing.serviceproject.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;
    @Override
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    @Override
    public Optional<Project> getProjectById(Long projectId) {
        return projectRepository.findById(projectId);
    }

    @Override
    public Project updateProject(Long projectId, Project projectRequest) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(()-> new ResourceNotFoundException("Project","id",projectId));
         project.setTitle(projectRequest.getTitle());
         project.setDescription(projectRequest.getDescription());

         return projectRepository.save(project);
    }

    @Override
    public Project saveProject(Project project) {
        return projectRepository.save(project);
    }

    @Override
    public ResponseEntity<?> deleteProject(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(()-> new ResourceNotFoundException("Project","id",projectId));
        projectRepository.delete(project);
        return ResponseEntity.ok().build();
    }
}
