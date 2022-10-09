package kz.iitu.issuetracker.service;

import kz.iitu.issuetracker.dto.project.request.ProjectCreationReq;
import kz.iitu.issuetracker.dto.project.request.ProjectUpdateReq;
import kz.iitu.issuetracker.dto.project.response.ProjectDto;
import kz.iitu.issuetracker.dto.project.response.ProjectPaginatedResp;
import kz.iitu.issuetracker.entity.Project;
import kz.iitu.issuetracker.feature.apiexception.ApiExceptionDetailHolder;
import kz.iitu.issuetracker.feature.apiexception.RecordAlreadyExistsException;
import kz.iitu.issuetracker.feature.apiexception.RecordNotFoundException;
import kz.iitu.issuetracker.feature.mapper.ProjectMapper;
import kz.iitu.issuetracker.repository.ProjectRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;

    @Transactional(readOnly = true)
    public ProjectPaginatedResp getProjects(Pageable pageable) {
        Page<Project> projectPage = projectRepository.findAllByOrderByIdDesc(pageable);
        return ProjectPaginatedResp.fromProjectPage(projectPage);
    }

    @Transactional(readOnly = true)
    public ProjectDto getById(long id) {
        Project project = getByIdOrThrowNotFound(id);
        return toDto(project);
    }

    public ProjectDto create(ProjectCreationReq creationReq) {
        throwIfNameAlreadyTaken(creationReq.getName());
        Project project = toEntity(creationReq);
        Project savedProject = projectRepository.save(project);
        return toDto(savedProject);
    }

    public ProjectDto update(ProjectUpdateReq updateReq) {
        Project project = getByIdOrThrowNotFound(updateReq.getId());

        String newName = updateReq.getName();
        String newDescription = updateReq.getDescription();

        if (StringUtils.hasText(newName)) {
            throwIfNameAlreadyTaken(newName, updateReq.getId());
            project.setName(newName);
        }
        if (StringUtils.hasText(newDescription)) {
            project.setDescription(newDescription);
        }
        Project updatedProject = projectRepository.save(project);
        return toDto(updatedProject);
    }

    public void delete(Long id) {
        Project project = getByIdOrThrowNotFound(id);
        projectRepository.delete(project);
    }

    private ProjectDto toDto(Project project) {
        return ProjectMapper.INSTANCE.entityToDto(project);
    }

    private Project toEntity(ProjectCreationReq creationReq) {
        return ProjectMapper.INSTANCE.creationReqToEntity(creationReq);
    }

    private void throwIfNameAlreadyTaken(String projectName) {
        if (projectRepository.existsByName(projectName)) {
            throwProjectAlreadyExists(projectName);
        }
    }

    private void throwIfNameAlreadyTaken(String newProjectName, Long projectId) {
        Optional<Project> projectOptional = projectRepository.findByName(newProjectName);
        if (projectOptional.isEmpty()) {
            return;
        }
        Project project = projectOptional.get();
        if (!Objects.equals(project.getId(), projectId)) {
            throwProjectAlreadyExists(newProjectName);
        }
    }

    private void throwProjectAlreadyExists(String projectName) {
        ApiExceptionDetailHolder exceptionDetailHolder = ApiExceptionDetailHolder.builder()
                .field(ProjectDto.Field.NAME)
                .message("Project with name '" + projectName + "' already exists")
                .build();
        throw new RecordAlreadyExistsException(exceptionDetailHolder);
    }

    private Project getByIdOrThrowNotFound(long id) {
        ApiExceptionDetailHolder exDetailHolder = ApiExceptionDetailHolder.builder()
                .field(ProjectDto.Field.ID)
                .message("Project with id " + id + " does not exist")
                .build();
        return projectRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(exDetailHolder));
    }
}
