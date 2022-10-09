package kz.iitu.issuetracker.controller;

import kz.iitu.issuetracker.service.ProjectService;
import kz.iitu.issuetracker.dto.project.request.ProjectCreationReq;
import kz.iitu.issuetracker.dto.project.request.ProjectUpdateReq;
import kz.iitu.issuetracker.dto.project.response.ProjectDto;
import kz.iitu.issuetracker.dto.project.response.ProjectPaginatedResp;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/projects")
@PreAuthorize("hasRole('Admin')")
@ResponseStatus(HttpStatus.OK)
@AllArgsConstructor
public class ProjectController {
    private final ProjectService projectService;

    @GetMapping
    @PreAuthorize("@userSecurity.isLoggedIn(authentication)")
    public ProjectPaginatedResp getProjects(Pageable pageable) {
        return projectService.getProjects(pageable);
    }

    @GetMapping("/{id}")
    @PreAuthorize("@userSecurity.isLoggedIn(authentication)")
    public ProjectDto getProjectById(@PathVariable("id") long id) {
        return projectService.getById(id);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('Admin', 'Manager')")
    @ResponseStatus(HttpStatus.CREATED)
    public ProjectDto createProject(@RequestBody ProjectCreationReq creationReq) {
        return projectService.create(creationReq);
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('Admin', 'Manager') || @userSecurity.isLeadDevOfProject(authentication, #updateReq.id)")
    public ProjectDto updateProject(@RequestBody ProjectUpdateReq updateReq) {
        return projectService.update(updateReq);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('Admin')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProject(@PathVariable("id") long id) {
        projectService.delete(id);
    }
}
