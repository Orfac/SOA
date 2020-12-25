package ru.orfac.lab2spring.config;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.orfac.lab2spring.exceptions.NotFoundException;
import ru.orfac.lab2spring.exceptions.RequestHandlingException;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.xml.bind.JAXBException;
import javax.xml.bind.UnmarshalException;
import java.util.List;
import java.util.Objects;

@ControllerAdvice
public class RestErrorHandler
    extends ResponseEntityExceptionHandler {

  @ExceptionHandler(value
      = {ValidationException.class, RuntimeException.class, RequestHandlingException.class})
  protected ResponseEntity<Object> handleRuntimeExceptions(
      RuntimeException ex, WebRequest request
  ) {
    return handleBadRequestExceptions(ex, request);
  }

  @ExceptionHandler(value = {UnmarshalException.class})
  protected ResponseEntity<Object> handleJaxbExceptions(JAXBException ex, WebRequest request) {
    return handleBadRequestExceptions(ex, request);
  }

  @NotNull
  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      final MethodArgumentNotValidException ex,
      final HttpHeaders headers,
      final HttpStatus status,
      final WebRequest request
  ) {
    String bodyOfResponse = surroundErrorMessages(ex.getBindingResult().getAllErrors());
    return handleExceptionInternal(ex, bodyOfResponse,
        new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
  }

  @ExceptionHandler(value
      = {NotFoundException.class})
  protected ResponseEntity<Object> handleNotFoundExceptions(
      RuntimeException ex, WebRequest request
  ) {
    String bodyOfResponse = ex.getMessage();
    return handleExceptionInternal(ex, bodyOfResponse,
        new HttpHeaders(), HttpStatus.NOT_FOUND, request);
  }

  private ResponseEntity<Object> handleBadRequestExceptions(
      final Exception ex,
      final WebRequest request
  ) {
    String bodyOfResponse = ex.getMessage();
    return handleExceptionInternal(ex, bodyOfResponse,
        new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
  }

  private String surroundErrorMessages(final List<ObjectError> allErrors) {
    StringBuilder sb = new StringBuilder();
    for (ObjectError error : allErrors) {
      String field = ((DefaultMessageSourceResolvable) Objects
          .requireNonNull(error.getArguments())[0]).getDefaultMessage();
      sb.append(field).append(": ")
          .append(error.getDefaultMessage());
    }
    return sb.toString();
  }
}