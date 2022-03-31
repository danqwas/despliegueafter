package com.afterdrawing.backendapi.controller;
import com.afterdrawing.backendapi.core.entity.User;
import org.hibernate.annotations.Parameter;
import org.modelmapper.ModelMapper;
import com.afterdrawing.backendapi.core.entity.Project;
import com.afterdrawing.backendapi.core.service.ProjectService;
import com.afterdrawing.backendapi.core.service.UserService;
import com.afterdrawing.backendapi.resource.ProjectResource;
import com.afterdrawing.backendapi.resource.SaveProjectResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ApplicationController {
    @Autowired
    public ProjectService projectService;

    @Autowired
    public UserService userService;
    @Autowired
    private ModelMapper mapper;
    /**Projects realizados*/


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


    /**usuarios realizados*/

    @GetMapping("/users")
    public ResponseEntity<List<User>> findAllUsers(){
        try {
            List<User> users = userService.getAllUsers();
            if (!users.isEmpty())
                return ResponseEntity.ok(users);
            else
                return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    @GetMapping(value = "/users/{userId}")
    public ResponseEntity<User> findUserById(@PathVariable("userId") Long userId) {
        try {
            Optional<User> user = userService.getUserById(userId);
            return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    @PostMapping("/users")
    public ResponseEntity<User> insertUser( @RequestBody User user) {
        try {
            User userNew = userService.saveUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(userNew);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    @PutMapping(value = "/users/{userId}")
    public ResponseEntity<User> updateUser(
            @PathVariable("userId") Long userId, @Valid @RequestBody User user) {
        User user1 = userService.updateUser(userId, user);
        return  ResponseEntity.ok(user1);
    }
    @DeleteMapping(value = "/users/{userId}")
    public ResponseEntity<User> deleteUser(@PathVariable("userId") Long userId) {
        try {
            Optional<User> userDelete = userService.getUserById(userId);
            if (userDelete.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            userService.deleteUser(userId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
