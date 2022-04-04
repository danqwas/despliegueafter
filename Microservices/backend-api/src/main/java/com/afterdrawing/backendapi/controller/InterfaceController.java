package com.afterdrawing.backendapi.controller;

import com.afterdrawing.backendapi.core.entity.Interface;
import com.afterdrawing.backendapi.core.entity.Project;
import com.afterdrawing.backendapi.core.service.InterfaceService;
import com.afterdrawing.backendapi.core.service.ProjectService;
import com.afterdrawing.backendapi.core.service.UserService;
import com.afterdrawing.backendapi.resource.InterfaceResource;
import com.afterdrawing.backendapi.resource.SaveInterfaceResource;
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
public class InterfaceController {

    @Autowired
    private ModelMapper mapper;
    @Autowired
    public UserService userService;

    @Autowired
    public ProjectService projectService;

    @Autowired
    public InterfaceService interfaceService;

    @PostMapping("/users/{userId}/projects/{projectId}/interfaces")
    public InterfaceResource createInterface( @PathVariable(name = "userId") Long userId,
                                              @PathVariable(name = "projectId") Long projectId,
                                              @Valid @RequestBody SaveInterfaceResource resource){
        return convertToResourceInterface(interfaceService.saveInterface( convertToEntityInterface(resource), userId,projectId ));
    }
    @GetMapping("/projects/{projectId}/interfaces")
    public Page<InterfaceResource> getAllInterfacesByProjectId(
            @PathVariable(name = "projectId") Long projectId,
            Pageable pageable) {
        Page<Interface> interfacePage = interfaceService.getAllInterfacesByProjectId(projectId, pageable);
        List<InterfaceResource> resources = interfacePage.getContent().stream().map(this::convertToResourceInterface).collect(Collectors.toList());
        return new PageImpl<>(resources, pageable, resources.size());
    }
    @GetMapping("/users/{userId}/interfaces")
    public Page<InterfaceResource> getAllInterfacesByUserId(
            @PathVariable(name = "userId") Long userId,
            Pageable pageable) {
        Page<Interface> interfacePage = interfaceService.getAllInterfacesByUserId(userId, pageable);
        List<InterfaceResource> resources = interfacePage.getContent().stream().map(this::convertToResourceInterface).collect(Collectors.toList());
        return new PageImpl<>(resources, pageable, resources.size());
    }


    @GetMapping("/interfaces/{interfaceId}")
    public InterfaceResource getInterfaceById(
            @PathVariable(name = "interfaceId") Long interfaceId) {
        return convertToResourceInterface(interfaceService.getInterfaceById(interfaceId));
    }
    @PutMapping("/interfaces/{interfaceId}")
    public InterfaceResource updateInterface(
            @PathVariable(name = "interfaceId") Long interfaceId,
            @Valid @RequestBody SaveInterfaceResource resource) {
        return convertToResourceInterface(interfaceService.updateInterface(interfaceId, convertToEntityInterface(resource)));
    }
    @DeleteMapping("/interfaces/{interfaceId}")
    public ResponseEntity<?> deleteInterface(
            @PathVariable(name = "interfaceId") Long interfaceId) {
        return interfaceService.deleteInterface(interfaceId);
    }
    private Interface convertToEntityInterface(SaveInterfaceResource resource){
        return mapper.map(resource, Interface.class);
    }
    private InterfaceResource convertToResourceInterface(Interface entity){
        return mapper.map(entity, InterfaceResource.class);
    }

}
