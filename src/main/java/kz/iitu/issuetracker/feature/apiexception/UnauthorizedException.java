package kz.iitu.issuetracker.feature.apiexception;

public class UnauthorizedException extends ApiException {
    public UnauthorizedException(String message) {
        super(message);
    }
}
