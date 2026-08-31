package org.nxckywhxte.cardgame.api;

/**
 * Состояние карточной игры с операциями создания новых состояний.
 *
 * <p>Этот интерфейс расширяет {@link ReadableGameState} и добавляет операции для создания
 * <b>новых</b> состояний игры. Он следует принципу иммутабельности: все операции возвращают новые
 * экземпляры, не изменяя исходный.
 *
 * <h2>Когда использовать</h2>
 *
 * <ul>
 *   <li>Правила игры ({@code GameRules.applyMove}) для применения ходов
 *   <li>Инициализация игры ({@code GameRules.initialize})
 *   <li>Любой код, который создаёт новые состояния на основе существующих
 * </ul>
 *
 * <h2>Разделение интерфейсов</h2>
 *
 * <p>Код, который только <b>читает</b> состояние, должен зависеть от {@link ReadableGameState}.
 * Код, который <b>создаёт новые состояния</b>, зависит от этого интерфейса.
 *
 * <p>Пример использования:
 *
 * <pre>{@code
 * GameState state = rules.initialize(deck, players);
 * GameState updated = state.withZone("tableau-0", newZone);
 * GameState next = state.withCurrentPlayer("player-2");
 * }</pre>
 *
 * <h2>Иммутабельность</h2>
 *
 * <p>Все методы {@code with*} возвращают <b>новые</b> экземпляры {@code GameState}, не изменяя
 * исходный. Это критично для отмены ходов, симуляций и сетевой игры.
 *
 * <h2>Потокобезопасность</h2>
 *
 * <p>Поскольку состояние игры неизменяемо, оно потокобезопасно.
 *
 * @author nxckywhxte
 * @since 0.0.1
 * @see ReadableGameState
 */
public interface GameState {
  /**
   * Возвращает новое состояние с обновлённым текущим игроком.
   *
   * <p>Исходное состояние не изменяется.
   *
   * @param playerId идентификатор нового текущего игрока
   * @return новое состояние с обновлённым текущим игроком
   * @throws IllegalArgumentException если {@code playerId == null} или игрок не найден
   */
  GameState withCurrentPlayer(String playerId);

  /**
   * Возвращает новое состояние с обновлённой зоной.
   *
   * <p>Если зона с указанным идентификатором не существует, она будет добавлена. Исходное состояние
   * не изменяется.
   *
   * @param zoneId идентификатор зоны
   * @param zone новая зона
   * @return новое состояние с обновлённой зоной
   * @throws IllegalArgumentException если {@code zoneId == null} или {@code zone == null}
   */
  GameState withZone(String zoneId, Zone zone);

  /**
   * Возвращает новое состояние с обновлённым игроком.
   *
   * <p>Игрок идентифицируется по {@link Player#getId()}. Если игрок с таким идентификатором
   * существует, он будет заменён; иначе — добавлен. Исходное состояние не изменяется.
   *
   * @param player обновлённый игрок
   * @return новое состояние с обновлённым игроком
   * @throws IllegalArgumentException если {@code player == null}
   */
  GameState withPlayer(Player player);

  /**
   * Возвращает новое состояние с обновлённой колодой.
   *
   * <p>Исходное состояние не изменяется.
   *
   * @param deck новая колода
   * @return новое состояние с обновлённой колодой
   * @throws IllegalArgumentException если {@code deck == null}
   */
  GameState withDeck(Deck deck);

  /**
   * Возвращает новое состояние с обновлённым статусом завершения.
   *
   * <p>Используется для установки финального состояния игры. Исходное состояние не изменяется.
   *
   * @param finished новый статус завершения
   * @param winner победитель (может быть {@code null} для ничьей)
   * @return новое состояние с обновлённым статусом
   */
  GameState withFinished(boolean finished, Player winner);
}
