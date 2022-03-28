package com.afterdrawaing.serviceproject.controller;

import com.afterdrawaing.serviceproject.core.entity.Project;
import com.afterdrawaing.serviceproject.core.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ProjectController {
    @Autowired
    public ProjectService projectService;

    @GetMapping("/projects")
    public ResponseEntity<List<Project>> findAllProjects(){
        try{
            List<Project> projects = projectService.getAllProjects();
            if (!projects.isEmpty())
                return ResponseEntity.ok(projects);
            else
                return ResponseEntity.noContent().build();
        }  catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }
    @GetMapping(value = "/projects/{projectId}")
    public ResponseEntity<Project> findProjectById(@PathVariable("projectId") Long projectId) {
        try {
            Optional<Project> user = projectService.getProjectById(projectId);
            return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    @PostMapping("/projects")
    public ResponseEntity<Project> insertProject( @RequestBody Project project) {
        try {
            Project project1 = projectService.saveProject(project);
            return ResponseEntity.status(HttpStatus.CREATED).body(project1);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    @PutMapping(value = "/projects/{projectId}")
    public ResponseEntity<Project> updateProject(
            @PathVariable("projectId") Long projectId, @Valid @RequestBody Project project) {
        try {
            Optional<Project> project1 = projectService.getProjectById(projectId);
            if (project1.isEmpty())
                return ResponseEntity.notFound().build();
            projectService.saveProject(project);
            project.setId(projectId);


            return ResponseEntity.ok(project);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    @DeleteMapping(value = "/projects/{projectId}")
    public ResponseEntity<Project> deleteProject(@PathVariable("projectId") Long projectId) {
        try {
            Optional<Project> projectDelete = projectService.getProjectById(projectId);
            if (projectDelete.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            projectService.deleteProject(projectId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
