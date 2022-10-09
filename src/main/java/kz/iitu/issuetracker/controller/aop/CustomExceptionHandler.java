package kz.iitu.issuetracker.controller.aop;

import kz.iitu.issuetracker.feature.apiexception.ApiException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@SuppressWarnings({"unchecked","rawtypes"})
@AllArgsConstructor
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
    // WHY USE DELEGATE AND NOT WRITE THE EXCEPTION HANDLING LOGIC RIGHT HERE?
    // Because CustomExceptionHandler needs to be advised to log error responses by RequestResponseLoggingAspect.
    // But Spring AOP does not support aspects advising other aspects,
    // so a usual class, the delegate, is advised instead.
    private final ExceptionHandlerDelegate delegate;

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        return delegate.handleAllExceptions(ex);
    }

    @ExceptionHandler({ApiException.class})
    public final ResponseEntity<Object> handleApiExceptions(ApiException ex, WebRequest request) {
        return delegate.handleApiExceptions(ex);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        return delegate.handleMethodArgumentNotValid(ex);
    }
}
