package org.nxckywhxte.cardgame.api.exception;

import java.io.Serial;

/**
 * Базовое исключение библиотеки карточных игр.
 *
 * <p>Все специфичные исключения библиотеки наследуются от этого класса, что позволяет ловить все
 * ошибки библиотеки одним блоком {@code catch}:
 *
 * <pre>{@code
 * try {
 *     // операции с библиотекой
 * } catch (CardGameException e) {
 *     // обработка любой ошибки библиотеки
 * }
 * }</pre>
 *
 * <p>Это исключение является unchecked (наследует {@link RuntimeException}), так как большинство
 * ошибок библиотеки связаны с некорректным использованием API и должны исправляться в коде, а не
 * обрабатываться во время выполнения.
 *
 * @author nxckywhxte
 * @since 0.0.1
 */
public class CardGameException extends RuntimeException {
  @Serial private static final long serialVersionUID = 1L;

  /**
   * Создаёт исключение с указанным сообщением.
   *
   * @param message сообщение об ошибке
   */
  public CardGameException(String message) {
    super(message);
  }

  /**
   * Создаёт исключение с сообщением и причиной.
   *
   * @param message сообщение об ошибке
   * @param cause причина исключения
   */
  public CardGameException(String message, Throwable cause) {
    super(message, cause);
  }
}
