package me.mziccard.please.common;

import java.io.IOException;

/**
 * Exception class for HTTP not found.
 */
public class ResourceNotFoundException extends IOException {

  /**
   * {@code ResourceNotFoundException} class constructor.
   *
   * @param message   Exception's message string
   */
  public ResourceNotFoundException(String message) {
    super(message);
  }

}