package com.shan.tech.javlib.model.response;

import java.io.Serializable;

public class ErrorResponse implements Serializable {
  private static final long serialVersionUID = 1L;

  private String code;

  private String message;


  public ErrorResponse(String code, String message) {
    this.code = code;
    this.message = message;

  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  @Override
  public String toString() {
    return "ErrorResponse{" +
            "code='" + code + '\'' +
            ", message='" + message + '\'' +
            '}';
  }
}
