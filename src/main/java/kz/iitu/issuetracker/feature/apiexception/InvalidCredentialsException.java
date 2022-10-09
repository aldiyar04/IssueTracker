package kz.iitu.issuetracker.feature.apiexception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidCredentialsException extends ApiException {
    public InvalidCredentialsException() {
        super((List<ApiExceptionDetailHolder>) null);
    }
}
