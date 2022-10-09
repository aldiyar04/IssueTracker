package kz.iitu.itse1910.issuetracker.testdata;

import kz.iitu.issuetracker.dto.project.response.ProjectDto;
import kz.iitu.issuetracker.entity.Project;
import kz.iitu.issuetracker.feature.mapper.ProjectMapper;
import lombok.AccessLevel;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

public class ProjectTestData {
    @Getter
    public static class Entity {
        @Getter(AccessLevel.NONE)
        private final List<Project> projects;

        private final Project project1 = Project.builder()
                .id(1L)
                .name("Text Editor")
                .description("Notepad style application that can open, edit, and save text documents.")
                .build();
        private final Project project2 = Project.builder()
                .id(2L)
                .name("RSS Feed Creator")
                .description("A program which can read in text from other sources and put it in RSS or Atom news format for syndication.")
                .build();
        private final Project project3 = Project.builder()
                .id(3L)
                .name("Text to HTML Generator")
                .description("Converts text files into web HTML files and stylizes them. Great for making online documentation of standard text documentation")
                .build();

        public Entity() {
            this.projects = List.of(project1, project2, project3);
        }

        public List<Project> getAllProjects() {
            return projects;
        }
    }

    public static class Dto {
        private final List<ProjectDto> projectDtos;

        public Dto() {
            Entity entities = new Entity();
            this.projectDtos = entities.getAllProjects().stream()
                    .map(ProjectMapper.INSTANCE::entityToDto)
                    .collect(Collectors.toList());
        }

        public List<ProjectDto> getAllProjectDtos() {
            return projectDtos;
        }
    }
}
