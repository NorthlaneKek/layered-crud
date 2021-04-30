package com.example.demo.application;

import com.example.demo.domain.ServiceError;
import com.example.demo.domain.exception.EmailAlreadyExistsException;
import com.example.demo.domain.exception.InvalidEmailException;
import com.example.demo.domain.exception.ProfileNotFoundException;
import com.example.demo.domain.exception.ServiceErrorNotFoundException;
import com.example.demo.domain.repository.ServiceErrorRepository;
import com.example.demo.presentation.presenter.ErrorOccurred;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    private final ServiceErrorRepository repository;

    public RestExceptionHandler(ServiceErrorRepository repository) {
        this.repository = repository;
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    protected ResponseEntity<ErrorOccurred> handleEmailAlreadyExists(EmailAlreadyExistsException ex) {
        ServiceError error = ServiceError.emailAlreadyExists();
        repository.save(error);
        return buildResponseEntity(error, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(InvalidEmailException.class)
    protected ResponseEntity<ErrorOccurred> handleInvalidEmail(InvalidEmailException ex) {
        ServiceError error = ServiceError.invalidEmail();
        repository.save(error);
        return buildResponseEntity(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProfileNotFoundException.class)
    protected ResponseEntity<ErrorOccurred> handleEntityNotFound(ProfileNotFoundException ex) {
        ServiceError error = ServiceError.profileNotFoundException();
        repository.save(error);
        return buildResponseEntity(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ServiceErrorNotFoundException.class)
    protected ResponseEntity<ErrorOccurred> handleServiceErrorNotFound(ServiceErrorNotFoundException ex) {
        ServiceError error = ServiceError.serviceErrorNotFound();
        repository.save(error);
        return buildResponseEntity(error, HttpStatus.NOT_FOUND);
    }

    private ResponseEntity<ErrorOccurred> buildResponseEntity(ServiceError error, HttpStatus status) {
        return new ResponseEntity<>(new ErrorOccurred(error.getMsg()), status);
    }
}
