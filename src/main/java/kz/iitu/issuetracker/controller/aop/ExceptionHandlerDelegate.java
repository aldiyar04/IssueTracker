package kz.iitu.issuetracker.controller.aop;

import kz.iitu.issuetracker.dto.ErrorResponse;
import kz.iitu.issuetracker.feature.apiexception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.ArrayList;
import java.util.List;

// Used by CustomExceptionHandler
@Component
@SuppressWarnings({"unchecked","rawtypes"})
public class ExceptionHandlerDelegate {
    ResponseEntity<Object> handleAllExceptions(Exception ex) {
        ex.printStackTrace();
        ErrorResponse error = new ErrorResponse("Server Error", ex.getLocalizedMessage());
        return new ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    ResponseEntity<Object> handleApiExceptions(ApiException ex) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        String message;
        if (ex instanceof RecordNotFoundException) {
            httpStatus = HttpStatus.NOT_FOUND;
            message = "Record Not Found";
        } else if (ex instanceof RecordAlreadyExistsException) {
            message = "Record Already Exists";
        } else if (ex instanceof InvalidCredentialsException) {
            message = "Invalid Credentials";
        } else if (ex instanceof UnauthorizedException) {
            httpStatus = HttpStatus.UNAUTHORIZED;
            message = "Unauthorized";
        } else {
            message = "Invalid Use of API";
        }
        ErrorResponse error = new ErrorResponse(message, ex.getDetailHolders());
        return new ResponseEntity(error, httpStatus);
    }

    ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        List<String> details = new ArrayList<>();
        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            details.add(error.getDefaultMessage());
        }
        ErrorResponse error = new ErrorResponse(details, "Validation Failed");
        return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
    }
}
