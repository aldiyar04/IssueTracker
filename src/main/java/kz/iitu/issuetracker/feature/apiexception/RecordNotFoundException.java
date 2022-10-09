package kz.iitu.issuetracker.feature.apiexception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RecordNotFoundException extends ApiException {
    public RecordNotFoundException(ApiExceptionDetailHolder detailHolder) {
        super(detailHolder);
    }

    public RecordNotFoundException(String message) {
        super(message);
    }
}
