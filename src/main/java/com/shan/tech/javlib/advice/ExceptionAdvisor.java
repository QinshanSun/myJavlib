package com.shan.tech.javlib.advice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.shan.tech.javlib.model.enums.ResponseCodeEnum;
import com.shan.tech.javlib.model.exception.NoFoundException;
import com.shan.tech.javlib.model.response.ErrorResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ExceptionAdvisor extends ResponseEntityExceptionHandler {

  private static final Logger LOGGER = LogManager.getLogger(ExceptionAdvisor.class);

  @ExceptionHandler(JsonProcessingException.class)
  public ResponseEntity<Object> handleJsonProcessingException(JsonProcessingException ex, WebRequest request) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
  }

  @ResponseBody
  @ExceptionHandler(NoFoundException.class)
  @ResponseStatus(HttpStatus.ACCEPTED)
  public ErrorResponse handleNoFoundException(NoFoundException ex) {
    return new ErrorResponse(ResponseCodeEnum.NOT_FOUND.name(), ex.getMessage());
  }
}
