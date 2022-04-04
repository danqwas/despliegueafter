package com.afterdrawing.backendapi.controller;

import com.afterdrawing.backendapi.core.entity.Element;
import com.afterdrawing.backendapi.core.entity.Interface;
import com.afterdrawing.backendapi.core.service.ElementService;
import com.afterdrawing.backendapi.core.service.InterfaceService;
import com.afterdrawing.backendapi.core.service.ProjectService;
import com.afterdrawing.backendapi.core.service.UserService;
import com.afterdrawing.backendapi.resource.ElementResource;
import com.afterdrawing.backendapi.resource.InterfaceResource;
import com.afterdrawing.backendapi.resource.SaveElementResource;
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
public class ElementController {
    @Autowired
    private ModelMapper mapper;
    @Autowired
    public UserService userService;

    @Autowired
    public ProjectService projectService;

    @Autowired
    public InterfaceService interfaceService;
    @Autowired
    public ElementService elementService;

    @PostMapping("/users/{userId}/projects/{projectId}/interfaces/{interfaceId}/elements")
    public ElementResource createElement(@PathVariable(name = "userId") Long userId,
                                             @PathVariable(name = "projectId") Long projectId,
                                           @PathVariable(name = "interfaceId") Long interfaceId,
                                             @Valid @RequestBody SaveElementResource resource){
        return convertToResourceElement(elementService.saveElement( convertToEntityElement(resource), userId,projectId,interfaceId));
    }

    @GetMapping("/projects/{projectId}/elements")
    public Page<ElementResource> getAllElementsByProjectId(
            @PathVariable(name = "projectId") Long projectId,
            Pageable pageable) {
        Page<Element> elementPage = elementService.getAllElementsByProjectId(projectId, pageable);
        List<ElementResource> resources = elementPage.getContent().stream().map(this::convertToResourceElement).collect(Collectors.toList());
        return new PageImpl<>(resources, pageable, resources.size());
    }
    @GetMapping("/users/{userId}/elements")
    public Page<ElementResource> getAllElementsByUserId(
            @PathVariable(name = "userId") Long userId,
            Pageable pageable) {
        Page<Element> elementPage = elementService.getAllElementsByUserId(userId, pageable);
        List<ElementResource> resources = elementPage.getContent().stream().map(this::convertToResourceElement).collect(Collectors.toList());
        return new PageImpl<>(resources, pageable, resources.size());
    }
    @GetMapping("/interfaces/{interfaceId}/elements")
    public Page<ElementResource> getAllElementsByInterfaceId(
            @PathVariable(name = "interfaceId") Long interfaceId,
            Pageable pageable) {
        Page<Element> elementPage = elementService.getAllElementsByInterfaceId(interfaceId, pageable);
        List<ElementResource> resources = elementPage.getContent().stream().map(this::convertToResourceElement).collect(Collectors.toList());
        return new PageImpl<>(resources, pageable, resources.size());
    }

    @GetMapping("/elements/{elementId}")
    public ElementResource getElementById(
            @PathVariable(name = "elementId") Long elementId) {
        return convertToResourceElement(elementService.getElementById(elementId));
    }

    @PutMapping("/elements/{elementId}")
    public ElementResource updateElement(
            @PathVariable(name = "elementId") Long elementId,
            @Valid @RequestBody SaveElementResource resource) {
        return convertToResourceElement(elementService.updateElement(elementId, convertToEntityElement(resource)));
    }
    @DeleteMapping("/elements/{elementId}")
    public ResponseEntity<?> deleteElement(
            @PathVariable(name = "elementId") Long elementId) {
        return elementService.deleteElement(elementId);
    }
    private Element convertToEntityElement(SaveElementResource resource){
        return mapper.map(resource, Element.class);
    }
    private ElementResource convertToResourceElement(Element entity){
        return mapper.map(entity, ElementResource.class);
    }
}
