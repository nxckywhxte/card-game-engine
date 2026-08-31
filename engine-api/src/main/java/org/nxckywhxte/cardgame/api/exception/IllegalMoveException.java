package org.nxckywhxte.cardgame.api.exception;

import java.io.Serial;
import org.nxckywhxte.cardgame.api.Move;

public class IllegalMoveException extends CardGameException {
  @Serial private static final long serialVersionUID = 1L;

  /**
   * Создаёт исключение с указанием хода и причины.
   *
   * @param move невалидный ход
   * @param reason причина, по которой ход невалиден
   */
  public IllegalMoveException(Move move, String reason) {
    super("Illegal move: " + move + ", reason: " + reason);
  }

  /**
   * Создаёт исключение с указанным сообщением.
   *
   * @param message сообщение об ошибке
   */
  public IllegalMoveException(String message) {
    super(message);
  }

  /**
   * Создаёт исключение с сообщением и причиной.
   *
   * @param message сообщение об ошибке
   * @param cause причина исключения
   */
  public IllegalMoveException(String message, Throwable cause) {
    super(message, cause);
  }
}
