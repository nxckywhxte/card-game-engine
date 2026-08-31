package org.nxckywhxte.cardgame.api;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Состояние карточной игры в конкретный момент времени.
 *
 * <p>Состояние игры описывает полную картину происходящего: игроков, зоны с картами, колоду,
 * текущего игрока и статус игры. Это центральный интерфейс, который связывает все компоненты игры.
 *
 * <h2>Иммутабельность</h2>
 *
 * <p>Состояние игры является <b>полностью неизменяемым</b>. Это критично для:
 *
 * <ul>
 *   <li><b>Отмены ходов:</b> сохраняем предыдущие состояния
 *   <li><b>Симуляций:</b> проигрываем варианты ходов параллельно
 *   <li><b>Сетевой игры:</b> передаём состояния между клиентами
 *   <li><b>Сохранения/загрузки:</b> сериализуем состояние в любой момент
 * </ul>
 *
 * <p>Все методы, которые "изменяют" состояние, возвращают <b>новые</b> экземпляры {@code
 * GameState}, не изменяя исходный.
 *
 * <h2>Идентификаторы зон</h2>
 *
 * <p>Зоны хранятся в {@code Map<String, Zone>}, где ключ — идентификатор зоны. Каждая игра
 * определяет свои идентификаторы:
 *
 * <ul>
 *   <li><b>Клондайк:</b> "foundation-0".."foundation-3", "tableau-0".."tableau-6", "stock", "waste"
 *   <li><b>Дурак:</b> "hand-0", "hand-1", "table", "discard", "trump"
 *   <li><b>Паук:</b> "tableau-0".."tableau-9", "stock", "completed"
 * </ul>
 *
 * <p>Пример использования:
 *
 * <pre>{@code
 * GameState state = rules.initialize(deck, players);
 * Player current = state.getCurrentPlayer().orElseThrow();
 * Zone hand = state.getZone("hand-0").orElseThrow();
 * boolean finished = state.isFinished();
 * }</pre>
 *
 * <h2>Потокобезопасность</h2>
 *
 * <p>Поскольку состояние игры неизменяемо, оно потокобезопасно.
 *
 * @author nxckywhxte
 * @since 0.0.1
 * @see Player
 * @see Zone
 * @see Deck
 * @see Move
 */
public interface GameState {
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
   * Возвращает новое состояние с обновлённым текущим игроком.
   *
   * @param playerId идентификатор нового текущего игрока
   * @return новое состояние с обновлённым текущим игроком
   * @throws IllegalArgumentException если {@code playerId == null} или игрок не найден
   */
  GameState withCurrentPlayer(String playerId);

  /**
   * Возвращает новое состояние с обновлённой зоной.
   *
   * <p>Если зона с указанным идентификатором не существует, она будет добавлена.
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
   * существует, он будет заменён; иначе — добавлен.
   *
   * @param player обновлённый игрок
   * @return новое состояние с обновлённым игроком
   * @throws IllegalArgumentException если {@code player == null}
   */
  GameState withPlayer(Player player);
}
