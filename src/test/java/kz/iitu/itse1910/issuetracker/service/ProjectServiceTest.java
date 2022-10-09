package kz.iitu.itse1910.issuetracker.service;

import kz.iitu.issuetracker.dto.project.request.ProjectCreationReq;
import kz.iitu.issuetracker.dto.project.request.ProjectUpdateReq;
import kz.iitu.issuetracker.dto.project.response.ProjectDto;
import kz.iitu.issuetracker.entity.Project;
import kz.iitu.issuetracker.feature.apiexception.RecordAlreadyExistsException;
import kz.iitu.issuetracker.feature.apiexception.RecordNotFoundException;
import kz.iitu.issuetracker.repository.ProjectRepository;
import kz.iitu.issuetracker.service.ProjectService;
import kz.iitu.itse1910.issuetracker.testdata.ProjectTestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

class ProjectServiceTest {
    @Mock
    ProjectRepository projectRepository;
    @InjectMocks
    ProjectService underTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetProjects() {
        // given
        List<Project> projects = new ProjectTestData.Entity().getAllProjects();
        List<ProjectDto> projectDtos = new ProjectTestData.Dto().getAllProjectDtos();
        PageRequest pageRequest = PageRequest.of(0, Integer.MAX_VALUE);
        when(projectRepository.findAllByOrderByIdDesc(pageRequest)).thenReturn(new PageImpl<>(projects));

        // when
        List<ProjectDto> result = underTest.getProjects(pageRequest).getDtos();

        // assert
        assertThat(result).isEqualTo(projectDtos);
    }

    @Test
    void testGetById_caseSuccess() {
        // given
        long id = 1L;
        Project project = Project.builder().id(id).build();
        when(projectRepository.findById(id)).thenReturn(Optional.of(project));

        // when
        ProjectDto result = underTest.getById(id);

        // then
        assertThat(result.getId()).isEqualTo(id);
    }

    @Test
    void testGetById_caseNotFound() {
        // given
        long id = -1L;
        when(projectRepository.findById(id)).thenReturn(Optional.empty());

        // when, then
        assertThatThrownBy(() -> underTest.getById(id))
                .isInstanceOf(RecordNotFoundException.class);
    }

    @Test
    void testCreate_caseSuccess() {
        // given
        String name = "name";
        String description = "description";
        Project project = Project.builder()
                .name(name)
                .description(description)
                .build();
        ProjectDto projectDto = ProjectDto.builder()
                .name(name)
                .description(description)
                .build();
        when(projectRepository.existsByName(name)).thenReturn(false);
        when(projectRepository.save(any())).thenReturn(project);

        // when
        ProjectCreationReq creationReq = new ProjectCreationReq(name, description);
        ProjectDto result = underTest.create(creationReq);

        // then
        ArgumentCaptor<Project> projArgCaptor = ArgumentCaptor.forClass(Project.class);
        verify(projectRepository).save(projArgCaptor.capture());
        Project savedProject = projArgCaptor.getValue();
        assertThat(savedProject).isEqualTo(project);

        assertThat(result).isEqualTo(projectDto);
    }

    @Test
    void testCreate_shouldThrowRecordAlreadyExistsException_whenNameAlreadyExists() {
        // given
        String alreadyExistingName = "Taken Project Name";
        when(projectRepository.existsByName(alreadyExistingName)).thenReturn(true);

        // when, then
        ProjectCreationReq creationReq = new ProjectCreationReq(alreadyExistingName, anyString());
        assertThatThrownBy(() -> underTest.create(creationReq))
                .isInstanceOf(RecordAlreadyExistsException.class);
    }

    @Test
    void testUpdate_caseSuccess() {
        // given
        long id = 1L;
        String newName = "New Project Name";
        String newDescription = "New project description...";
        Project updatedProject = Project.builder()
                .id(id)
                .name(newName)
                .description(newDescription)
                .build();
        Project projectOldVersion = Project.builder().id(id).build();
        when(projectRepository.findById(id)).thenReturn(Optional.of(projectOldVersion));
        when(projectRepository.existsByName(newName)).thenReturn(false);
        when(projectRepository.save(any())).thenReturn(updatedProject);

        // when
        ProjectUpdateReq updateReq = ProjectUpdateReq.builder()
                .name(newName)
                .description(newDescription)
                .build();
        ProjectDto result = underTest.update(updateReq);

        // then
        ArgumentCaptor<Project> projArgCaptor = ArgumentCaptor.forClass(Project.class);
        verify(projectRepository).save(projArgCaptor.capture());
        Project savedProject = projArgCaptor.getValue();
        assertThat(savedProject).isEqualTo(updatedProject);

        assertThat(result.getId()).isEqualTo(id);
        assertThat(result.getName()).isEqualTo(newName);
        assertThat(result.getDescription()).isEqualTo(newDescription);
    }

    @Test
    void testUpdate_caseNotFound() {
        // given
        long id = -1L;
        when(projectRepository.findById(id)).thenReturn(Optional.empty());

        // when, then
        assertThatThrownBy(() -> underTest.update(any()))
                .isInstanceOf(RecordNotFoundException.class);
    }

    @Test
    void update_shouldThrowRecordAlreadyExistsException_whenNewNameAlreadyExists() {
        // given
        long id = 1L;
        String newName = "Taken Project Name";
        Project project = Project.builder().id(id).build();
        when(projectRepository.findById(id)).thenReturn(Optional.of(project));
        when(projectRepository.existsByName(newName)).thenReturn(true);

        // when, then
        ProjectUpdateReq updateReq = ProjectUpdateReq.builder()
                .id(1L)
                .name(newName)
                .build();
        assertThatThrownBy(() -> underTest.update(updateReq))
                .isInstanceOf(RecordAlreadyExistsException.class);
    }

    @Test
    void testDelete_caseSuccess() {
        // given
        long id = 1L;
        Project project = Project.builder().id(id).build();
        when(projectRepository.findById(id)).thenReturn(Optional.of(project));

        // when
        underTest.delete(id);

        // then
        ArgumentCaptor<Project> projArgCaptor = ArgumentCaptor.forClass(Project.class);
        verify(projectRepository).delete(projArgCaptor.capture());
        Project deletedProject = projArgCaptor.getValue();
        assertThat(deletedProject).isEqualTo(project);
    }

    @Test
    void testDelete_caseNotFound() {
        // given
        long id = -1L;
        when(projectRepository.findById(id)).thenReturn(Optional.empty());

        // when, then
        assertThatThrownBy(() -> underTest.delete(id))
                .isInstanceOf(RecordNotFoundException.class);
    }
}