package org.nxckywhxte.cardgame.api;

import java.util.List;
import org.nxckywhxte.cardgame.core.Card;

/**
 * Ход игрока в карточной игре.
 *
 * <p>Ход описывает действие игрока, которое изменяет состояние игры: перемещение карт между зонами.
 * Ход содержит идентификаторы игрока и зон, а также список карт, участвующих в ходе.
 *
 * <h2>Почему идентификаторы, а не объекты</h2>
 *
 * <p>Ход использует <b>идентификаторы</b> игрока и зон ({@code String}), а не сами объекты. Это
 * обеспечивает:
 *
 * <ul>
 *   <li><b>Актуальность:</b> идентификаторы всегда ссылаются на текущее состояние игры
 *   <li><b>Сериализуемость:</b> легко передавать по сети и сохранять
 *   <li><b>Гибкость:</b> соответствуют ключам в {@code GameState.getZones()}
 * </ul>
 *
 * <h2>Иммутабельность</h2>
 *
 * <p>Ход является <b>полностью неизменяемым</b>. Он описывает действие, но не изменяет состояние.
 * Применение хода выполняется методом {@code GameRules.applyMove()}, который возвращает новое
 * состояние игры.
 *
 * <h2>Инварианты</h2>
 *
 * <p>Следующие условия всегда истинны для любой реализации {@code Move}:
 *
 * <ul>
 *   <li>{@code getPlayerId() != null && !getPlayerId().isEmpty()}
 *   <li>{@code getCards() != null && !getCards().isEmpty()}
 *   <li>{@code getSourceZoneId() != null && !getSourceZoneId().isEmpty()}
 *   <li>{@code getTargetZoneId() != null && !getTargetZoneId().isEmpty()}
 * </ul>
 *
 * <p>Пример использования:
 *
 * <pre>{@code
 * Move move = MoveFactory.create(
 *     "player-1",
 *     List.of(card),
 *     "tableau-3",
 *     "foundation-0"
 * );
 * boolean valid = rules.validateMove(state, move);
 * }</pre>
 *
 * <h2>Потокобезопасность</h2>
 *
 * <p>Поскольку ход неизменяем, он потокобезопасен.
 *
 * @author nxckywhxte
 * @since 0.0.1
 * @see Player
 * @see Zone
 * @see GameState
 */
public interface Move {
  /**
   * Возвращает идентификатор игрока, который делает ход.
   *
   * <p>Идентификатор соответствует {@link Player#getId()} и используется для поиска игрока в {@code
   * GameState}.
   *
   * @return идентификатор игрока
   */
  String getPlayerId();

  /**
   * Возвращает список карт, участвующих в ходе.
   *
   * <p>В большинстве ходов участвует одна карта, но в некоторых играх (например, Паук) ход может
   * включать последовательность карт.
   *
   * <p>Возвращённый список является неизменяемым.
   *
   * @return неизменяемый список карт в порядке их перемещения
   */
  List<Card> getCards();

  /**
   * Возвращает идентификатор исходной зоны, откуда берутся карты.
   *
   * <p>Идентификатор соответствует ключу в {@code GameState.getZones()}.
   *
   * @return идентификатор исходной зоны
   */
  String getSourceZoneId();

  /**
   * Возвращает идентификатор целевой зоны, куда кладутся карты.
   *
   * <p>Идентификатор соответствует ключу в {@code GameState.getZones()}.
   *
   * @return идентификатор целевой зоны
   */
  String getTargetZoneId();

  /**
   * Возвращает количество карт, участвующих в ходе.
   *
   * @return количество карт
   */
  default int cardCount() {
    return getCards().size();
  }

  /**
   * Проверяет, является ли ход одиночным (участвует одна карта).
   *
   * @return {@code true}, если в ходе участвует ровно одна карта
   */
  default boolean isSingleCard() {
    return cardCount() == 1;
  }

  /**
   * Возвращает единственную карту хода.
   *
   * <p>Удобно для ходов с одной картой. Перед вызовом рекомендуется проверить {@link
   * #isSingleCard()}.
   *
   * @return единственная карта хода
   * @throws IllegalStateException если в ходе не ровно одна карта
   */
  default Card singleCard() {
    List<Card> cards = getCards();
    if (cards.size() != 1) {
      throw new IllegalStateException("Expected exactly one card, got: " + cards.size());
    }
    return cards.getFirst();
  }

  /**
   * Возвращает человекочитаемое описание хода для логирования.
   *
   * @return описание хода
   */
  default String getDescription() {
    return "Player "
        + getPlayerId()
        + " moves "
        + cardCount()
        + " card(s) from '"
        + getSourceZoneId()
        + "' to '"
        + getTargetZoneId()
        + "'";
  }
}
