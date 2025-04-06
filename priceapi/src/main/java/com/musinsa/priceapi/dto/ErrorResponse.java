package com.musinsa.priceapi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
  private final int status;
  private final String message;
  private final String error;
  private final String path;
  private final String timestamp;

  public ErrorResponse(int status, String message, String error, String path, String timestamp) {
    this.status = status;
    this.message = message;
    this.error = error;
    this.path = path;
    this.timestamp = timestamp;
  }

  public static ErrorResponse of(int status, String message, String error, String path) {
    return new ErrorResponse(
        status, message, error, path, java.time.LocalDateTime.now().toString());
  }

  public int getStatus() {
    return status;
  }

  public String getMessage() {
    return message;
  }

  public String getError() {
    return error;
  }

  public String getPath() {
    return path;
  }

  public String getTimestamp() {
    return timestamp;
  }
}
