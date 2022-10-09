package kz.iitu.issuetracker.dto.project.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class ProjectCreationReq {
    private final String name;
    private final String description;
}
