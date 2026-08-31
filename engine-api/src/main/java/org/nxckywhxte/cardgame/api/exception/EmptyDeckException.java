package org.nxckywhxte.cardgame.api.exception;

import java.io.Serial;

public class EmptyDeckException extends CardGameException {

  @Serial private static final long serialVersionUID = 1L;

  /** Создаёт исключение с сообщением по умолчанию. */
  public EmptyDeckException() {
    super("Cannot draw card from empty deck");
  }

  /**
   * Создаёт исключение с указанным сообщением.
   *
   * @param message сообщение об ошибке
   */
  public EmptyDeckException(String message) {
    super(message);
  }

  /**
   * Создаёт исключение с сообщением и причиной.
   *
   * @param message сообщение об ошибке
   * @param cause причина исключения
   */
  public EmptyDeckException(String message, Throwable cause) {
    super(message, cause);
  }
}
