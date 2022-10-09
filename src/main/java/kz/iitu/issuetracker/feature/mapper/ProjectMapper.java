package kz.iitu.issuetracker.feature.mapper;

import kz.iitu.issuetracker.entity.User;
import kz.iitu.issuetracker.dto.project.request.ProjectCreationReq;
import kz.iitu.issuetracker.dto.project.response.ProjectDto;
import kz.iitu.issuetracker.entity.BaseEntity;
import kz.iitu.issuetracker.entity.Project;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProjectMapper {
    ProjectMapper INSTANCE = Mappers.getMapper(ProjectMapper.class);

    @Mapping(target = ProjectDto.Field.LEAD_DEV_ID, source = Project.Field.LEAD_DEV + "." + BaseEntity.Field.ID)
    @Mapping(target = ProjectDto.Field.LEAD_DEV_USERNAME, source = Project.Field.LEAD_DEV + "." + User.Field.USERNAME)
    ProjectDto entityToDto(Project entity);

    Project creationReqToEntity(ProjectCreationReq creationReq);
}
