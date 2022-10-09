package kz.iitu.issuetracker.dto.project.request;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class ProjectUpdateReq {
    private final Long id;
    private final String name;
    private final String description;
}
