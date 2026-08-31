package org.nxckywhxte.cardgame.api.exception;

import java.io.Serial;
import org.nxckywhxte.cardgame.core.Card;

/**
 * Исключение, возникающее при попытке удалить карту, которой нет в зоне.
 *
 * <p>Это исключение является unchecked и наследуется от {@link CardGameException}. Оно возникает,
 * когда вызывается {@code Zone.removeCard()} с картой, которая отсутствует в зоне.
 *
 * <p>Для безопасного удаления рекомендуется сначала проверить наличие карты через {@code
 * Zone.contains()} или использовать другие методы зоны.
 *
 * @author nxckywhxte
 * @since 0.0.1
 * @see CardGameException
 */
public class CardNotFoundException extends CardGameException {
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
