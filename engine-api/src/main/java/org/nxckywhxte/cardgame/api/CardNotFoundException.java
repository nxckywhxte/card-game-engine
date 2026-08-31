package org.nxckywhxte.cardgame.api;

import java.io.Serial;
import org.nxckywhxte.cardgame.core.Card;

/**
 * Исключение, возникающее при попытке удалить карту, которой нет в зоне.
 *
 * <p>Это исключение является unchecked, так как попытка удалить несуществующую карту обычно
 * является ошибкой программирования или логики игры.
 *
 * @author nxckywhxte
 * @since 0.0.1
 * @see Zone#removeCard(Card)
 */
public class CardNotFoundException extends RuntimeException {
  @Serial private static final long serialVersionUID = 1L;

  /**
   * Создаёт исключение с указанием карты, которая не найдена.
   *
   * @param card карта, которая не найдена
   */
  public CardNotFoundException(Card card) {
    super("Card not found in zone: " + card);
  }

  /**
   * Создаёт исключение с указанным сообщением.
   *
   * @param message сообщение об ошибке
   */
  public CardNotFoundException(String message) {
    super(message);
  }

  /**
   * Создаёт исключение с сообщением и причиной.
   *
   * @param message сообщение об ошибке
   * @param cause причина исключения
   */
  public CardNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }
}
