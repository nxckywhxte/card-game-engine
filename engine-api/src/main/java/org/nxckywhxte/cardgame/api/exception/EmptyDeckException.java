package org.nxckywhxte.cardgame.api.exception;

import java.io.Serial;

/**
 * Исключение, возникающее при попытке взять карту из пустой колоды.
 *
 * <p>Это исключение является unchecked и наследуется от {@link CardGameException}. Оно возникает,
 * когда вызывается {@code Deck.draw()} или {@code Deck.drawMany()} на колоде без достаточного
 * количества карт.
 *
 * <p>Для случаев, когда пустая колода является ожидаемой частью игрового процесса, рекомендуется
 * использовать {@code Deck.tryDraw()}, который возвращает {@code Optional} вместо выбрасывания
 * исключения.
 *
 * @author nxckywhxte
 * @since 0.0.1
 * @see CardGameException
 */
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
