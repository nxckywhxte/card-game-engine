package org.nxckywhxte.cardgame.api;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Состояние карточной игры (только чтение).
 *
 * <p>Этот интерфейс предоставляет доступ к состоянию игры <b>только для чтения</b>. Он следует
 * принципу разделения интерфейсов (ISP): код, который только читает состояние, зависит от этого
 * интерфейса, а не от полного {@link GameState}.
 *
 * <h2>Когда использовать</h2>
 *
 * <ul>
 *   <li>Отображение состояния игры в пользовательском интерфейсе
 *   <li>Валидация ходов ({@code GameRules.validateMove})
 *   <li>Проверка победы ({@code GameRules.checkVictory})
 *   <li>Получение допустимых ходов ({@code GameRules.getAllowedMoves})
 * </ul>
 *
 * <h2>Иммутабельность</h2>
 *
 * <p>Состояние игры является <b>полностью неизменяемым</b>. Все возвращаемые коллекции являются
 * неизменяемыми.
 *
 * <h2>Инварианты</h2>
 *
 * <p>Следующие условия всегда истинны для любой реализации:
 *
 * <ul>
 *   <li>{@code getPlayers() != null && !getPlayers().isEmpty()}
 *   <li>{@code getZones() != null}
 *   <li>{@code getCurrentPlayer()} возвращает пустой {@link Optional}, если игра завершена
 *   <li>{@code getWinner()} возвращает непустой {@link Optional}, только если {@code isFinished()
 *       == true}
 * </ul>
 *
 * <p>Пример использования:
 *
 * <pre>{@code
 * ReadableGameState state = rules.initialize(deck, players);
 * Player current = state.getCurrentPlayer().orElseThrow();
 * Zone hand = state.getZone("hand-0").orElseThrow();
 * }</pre>
 *
 * <h2>Потокобезопасность</h2>
 *
 * <p>Поскольку состояние игры неизменяемо, оно потокобезопасно.
 *
 * @author nxckywhxte
 * @since 0.0.1
 * @see GameState
 */
public interface ReadableGameState {
  /**
   * Возвращает список всех игроков в порядке их очереди.
   *
   * <p>Возвращённый список является неизменяемым.
   *
   * @return неизменяемый список игроков
   */
  List<Player> getPlayers();

  /**
   * Возвращает игрока по идентификатору.
   *
   * @param playerId идентификатор игрока
   * @return {@link Optional} с игроком или пустой, если игрок не найден
   * @throws IllegalArgumentException если {@code playerId == null}
   */
  Optional<Player> getPlayer(String playerId);

  /**
   * Возвращает текущего игрока, чья очередь делать ход.
   *
   * <p>Если игра завершена или текущий игрок не определён, возвращается пустой {@link Optional}.
   *
   * @return {@link Optional} с текущим игроком или пустой
   */
  Optional<Player> getCurrentPlayer();

  /**
   * Возвращает все зоны игры в виде неизменяемой карты.
   *
   * <p>Ключ — идентификатор зоны, значение — зона с картами.
   *
   * @return неизменяемая карта зон
   */
  Map<String, Zone> getZones();

  /**
   * Возвращает зону по идентификатору.
   *
   * @param zoneId идентификатор зоны
   * @return {@link Optional} с зоной или пустой, если зона не найдена
   * @throws IllegalArgumentException если {@code zoneId == null}
   */
  Optional<Zone> getZone(String zoneId);

  /**
   * Возвращает колоду игры, если она есть.
   *
   * <p>В некоторых играх колода может отсутствовать или быть пустой. Возвращается {@link Optional}
   * для безопасной обработки.
   *
   * @return {@link Optional} с колодой или пустой, если колоды нет
   */
  Optional<Deck> getDeck();

  /**
   * Проверяет, завершена ли игра.
   *
   * @return {@code true}, если игра завершена
   */
  boolean isFinished();

  /**
   * Возвращает победителя игры, если игра завершена.
   *
   * <p>Если игра не завершена или победитель не определён (например, ничья), возвращается пустой
   * {@link Optional}.
   *
   * @return {@link Optional} с победителем или пустой
   */
  Optional<Player> getWinner();

  /**
   * Возвращает количество игроков в игре.
   *
   * @return количество игроков
   */
  default int getPlayerCount() {
    return getPlayers().size();
  }

  /**
   * Возвращает количество зон в игре.
   *
   * @return количество зон
   */
  default int getZoneCount() {
    return getZones().size();
  }

  /**
   * Проверяет, есть ли колода в игре.
   *
   * @return {@code true}, если колода присутствует
   */
  default boolean hasDeck() {
    return getDeck().isPresent();
  }

  /**
   * Проверяет, определён ли победитель.
   *
   * @return {@code true}, если победитель определён
   */
  default boolean hasWinner() {
    return getWinner().isPresent();
  }

  /**
   * Проверяет, завершена ли игра (синоним для {@link #isFinished()}).
   *
   * @return {@code true}, если игра завершена
   */
  default boolean isGameOver() {
    return isFinished();
  }
}
