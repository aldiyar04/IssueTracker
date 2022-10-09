package kz.iitu.issuetracker.dto.project.response;

import kz.iitu.issuetracker.feature.mapper.ProjectMapper;
import kz.iitu.issuetracker.entity.Project;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString
public class ProjectPaginatedResp {
    private final List<ProjectDto> dtos;
    private final int totalPages;

    public static ProjectPaginatedResp fromProjectPage(Page<Project> projectPage) {
        List<ProjectDto> projectDtos = projectPage.getContent().stream()
                .map(ProjectMapper.INSTANCE::entityToDto)
                .collect(Collectors.toList());
        return new ProjectPaginatedResp(projectDtos, projectPage.getTotalPages());
    }
}
