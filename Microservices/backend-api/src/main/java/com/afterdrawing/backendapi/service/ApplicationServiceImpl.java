package com.afterdrawing.backendapi.service;

import com.afterdrawing.backendapi.core.entity.Project;
import com.afterdrawing.backendapi.core.entity.User;
import com.afterdrawing.backendapi.core.repository.ProjectRepository;
import com.afterdrawing.backendapi.core.repository.UserRepository;
import com.afterdrawing.backendapi.core.service.ProjectService;
import com.afterdrawing.backendapi.core.service.UserService;
import com.afterdrawing.backendapi.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class ApplicationServiceImpl implements ProjectService, UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProjectRepository projectRepository;
    /**Desarrollo de servicion Users*/

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }



    @Override
    public User updateUser(Long userId, User userRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        user.setUserName(userRequest.getUserName());
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setPassword(userRequest.getPassword());

        return userRepository.save(user);
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public ResponseEntity<?> deleteUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        userRepository.delete(user);
        return ResponseEntity.ok().build();
    }
    /**Desarrollo de servicio Projects*/
    @Override
    public Page<Project> getAllProjects(Pageable pageable) {
        return projectRepository.findAll(pageable);
    }

    @Override
    public Page<Project> getAllProjectsByUserId(Long userId, Pageable pageable) {
        return projectRepository.findByUserId(userId, pageable);
    }

    @Override
    public Project getProjectByUserIdAndProjectId(Long userId, Long projectId) {
        return projectRepository.findByIdAndUserId(projectId,userId).
                orElseThrow(()-> new ResourceNotFoundException(
                "Project not found with Id" +projectId +
                        "and  UserId" +userId)
        );
    }

    @Override
    public Project getProjectById(Long projectId) {
        return projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project", "Id", projectId));
    }

    @Override
    public Project updateProject(Long projectId, Project projectRequest,Long userId) {

        Project project = projectRepository.findById(projectId)
                .orElseThrow(()-> new ResourceNotFoundException("Project","id",projectId));
        project.setTitle(projectRequest.getTitle());
        project.setDescription(projectRequest.getDescription());

        return projectRepository.save(project);
    }

    @Override
    public Project saveProject(Project project,Long userId) {
        User user = userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "Id", userId));
        project.setUser(user);
        return projectRepository.save(project);
    }

    @Override
    public ResponseEntity<?> deleteProject(Long projectId,Long userId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(()-> new ResourceNotFoundException("Project","id",projectId));
        projectRepository.delete(project);
        return ResponseEntity.ok().build();
    }
}
