package kz.iitu.issuetracker.feature.apiexception;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ApiExceptionDetailHolder {
    private final String field;
    private final String message;
}
