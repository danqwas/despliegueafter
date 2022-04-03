package com.afterdrawing.backendapi.controller;

import com.afterdrawing.backendapi.core.entity.Project;
import com.afterdrawing.backendapi.core.service.ProjectService;
import com.afterdrawing.backendapi.core.service.UserService;
import com.afterdrawing.backendapi.resource.ProjectResource;
import com.afterdrawing.backendapi.resource.SaveProjectResource;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ProjectController {
    @Autowired
    public UserService userService;
    @Autowired
    public ProjectService projectService;

    @Autowired
    private ModelMapper mapper;

    @GetMapping("/users/{userId}/projects")
    public Page<ProjectResource> getAllProjectssByUserId(@PathVariable(name = "userId") Long userId, Pageable pageable) {
        List<ProjectResource> projects = projectService.getAllProjectsByUserId(userId,pageable)
                .getContent().stream().map(this::convertToResource).collect(Collectors.toList());
        int count = projects.size();
        return new PageImpl<>(projects, pageable, count);
    }

    @GetMapping("/projects/{projectId}")
    public ProjectResource getPostById(
            @PathVariable(name = "projectId") Long projectId) {
        return convertToResource(projectService.getProjectById(projectId));
    }
    @GetMapping("/users/{userId}/projects/{projectId}")
    public ProjectResource getProjectByIdAndUserId(@PathVariable(name = "userId") Long userId,
                                                   @PathVariable(name = "projectId") Long projectId) {
        return convertToResource(projectService.getProjectByUserIdAndProjectId(userId, projectId));
    }
    @PostMapping("/users/{userId}/projects")
    public ProjectResource createProject(@PathVariable(name = "userId") Long userId,
                                         @Valid @RequestBody SaveProjectResource resource) {
        return convertToResource(projectService.saveProject( convertToEntity(resource),userId));

    }
    @PutMapping("/users/{userId}/projects/{projectId}")
    public ProjectResource updateProject(@PathVariable(name = "userId") Long userId,
                                         @PathVariable(name = "projectId") Long projectId,
                                         @Valid @RequestBody SaveProjectResource resource) {
        return convertToResource(projectService.updateProject(projectId, convertToEntity(resource), userId));
    }

    @DeleteMapping("/users/{userId}/projects/{projectId}")
    public ResponseEntity<?> deleteProject(@PathVariable(name = "userId") Long userId,
                                           @PathVariable(name = "projectId") Long projectId) {
        return projectService.deleteProject(projectId, userId);
    }
    @GetMapping("/projects")
    public Page<ProjectResource> getAllProjects(Pageable pageable) {
        Page<Project> projects = projectService.getAllProjects(pageable);
        List<ProjectResource> resources = projects.getContent().stream().map(this::convertToResource).collect(Collectors.toList());

        return new PageImpl<ProjectResource>(resources,pageable , resources.size());
    }

    private Project convertToEntity(SaveProjectResource resource) {
        return mapper.map(resource, Project.class);
    }

    private ProjectResource convertToResource(Project entity) {
        return mapper.map(entity, ProjectResource.class);
    }



}
