package kz.iitu.issuetracker.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import kz.iitu.issuetracker.feature.apiexception.ApiExceptionDetailHolder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
@ToString
public class ErrorResponse {
    private final String message;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final List<ApiExceptionDetailHolder> detailHolders;

    public ErrorResponse(String message, ApiExceptionDetailHolder detailHolder) {
        this(message, Collections.singletonList(detailHolder));
    }

    public ErrorResponse(String message, String detailMessage) {
        this(message, ApiExceptionDetailHolder.builder()
                .message(detailMessage)
                .build());
    }

    public ErrorResponse(List<String> detailMessages, String message) {
        this(message, detailMessages.stream()
                .map(detailMsg -> ApiExceptionDetailHolder.builder()
                        .message(detailMsg)
                        .build())
                .collect(Collectors.toList()));
    }
}
