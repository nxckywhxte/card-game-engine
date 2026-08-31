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
 * <h2>Потокобезопасность</h2>
 *
 * <p>Поскольку {@code DrawResult} является неизменяемым {@code record}, он потокобезопасен.
 *
 * @param cards взятые карты в порядке взятия (от верха колоды)
 * @param deck новая колода без взятых карт
 * @author nxckywhxte
 * @since 0.0.1
 * @see Deck
 */
public record DrawResult(List<Card> cards, Deck deck) {

  /** Компактный конструктор для валидации и защитного копирования. */
  public DrawResult {
    if (cards == null) {
      throw new IllegalArgumentException("Cards list cannot be null");
    }
    if (deck == null) {
      throw new IllegalArgumentException("Deck cannot be null");
    }
    // Делаем список неизменяемым для защиты от мутации
    cards = List.copyOf(cards);
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
   * @throws IllegalStateException если карт не ровно одна
   */
  public Card singleCard() {
    if (cards.size() != 1) {
      throw new IllegalStateException("Expected exactly one card, got: " + cards.size());
    }
    return cards.getFirst();
  }
}
