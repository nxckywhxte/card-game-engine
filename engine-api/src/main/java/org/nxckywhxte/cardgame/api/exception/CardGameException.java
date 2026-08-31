package org.nxckywhxte.cardgame.api.exception;

import java.io.Serial;

/**
 * Базовое исключение библиотеки карточных игр.
 *
 * <p>Все специфичные исключения библиотеки наследуются от этого класса, что позволяет ловить все
 * ошибки библиотеки одним catch-блоком.
 */
public class CardGameException extends RuntimeException {
  @Serial private static final long serialVersionUID = 1L;

  public CardGameException(String message) {
    super(message);
  }

  public CardGameException(String message, Throwable cause) {
    super(message, cause);
  }
}
