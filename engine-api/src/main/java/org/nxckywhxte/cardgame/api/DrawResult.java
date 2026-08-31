package org.nxckywhxte.cardgame.api;

import java.util.List;
import org.nxckywhxte.cardgame.core.Card;

/**
 * Результат взятия карт из колоды.
 *
 * <p>Используется для сохранения иммутабельности колоды: вместо того чтобы мутировать колоду,
 * операция взятия возвращает пару (взятые карты, новая колода).
 *
 * <p>Пример использования:
 *
 * <pre>{@code
 * DrawResult result = deck.draw();
 * Card card = result.firstCard();
 * Deck remaining = result.deck();
 * }</pre>
 *
 * @param cards взятые карты в порядке взятия (от верха колоды)
 * @param deck новая колода без взятых карт
 * @author nxckywhxte
 * @since 0.0.1
 * @see Deck
 */
public record DrawResult(List<Card> cards, Deck deck) {
  /** Компактный конструктор для валидации. */
  public DrawResult {
    if (cards == null) {
      throw new IllegalArgumentException("Cards list cannot be null");
    }
    if (deck == null) {
      throw new IllegalArgumentException("Deck cannot be null");
    }
  }

  /**
   * Возвращает первую взятую карту.
   *
   * <p>Удобно при взятии одной карты через {@link Deck#draw()}.
   *
   * @return первая карта из списка
   * @throws IllegalStateException если список карт пуст
   */
  public Card firstCard() {
    if (cards.isEmpty()) {
      throw new IllegalStateException("No cards were drawn");
    }
    return cards.getFirst();
  }

  /**
   * Возвращает единственную взятую карту.
   *
   * <p>Используется, когда ожидается ровно одна карта.
   *
   * @return единственная карта
   * @throws IllegalStateException если карт не одна
   */
  public Card singleCard() {
    if (cards.size() != 1) {
      throw new IllegalStateException("Expected exactly one card, got: " + cards.size());
    }
    return cards.getFirst();
  }
}
