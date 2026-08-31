package org.nxckywhxte.cardgame.api;

import java.util.List;
import java.util.Optional;
import org.nxckywhxte.cardgame.api.exception.CardGameException;

/**
 * Правила карточной игры.
 *
 * <p>Интерфейс определяет логику конкретной игры: инициализацию, валидацию ходов, применение ходов,
 * проверку победы и получение допустимых ходов. Каждая игра (Дурак, Клондайк, Паук) реализует этот
 * интерфейс.
 *
 * <h2>Реализации</h2>
 *
 * <ul>
 *   <li>{@code KlondikeRules} — правила Клондайка (Косынки)
 *   <li>{@code DurakRules} — правила Дурака
 *   <li>{@code SpiderRules} — правила Паука
 * </ul>
 *
 * <h2>Отсутствие состояния</h2>
 *
 * <p>Правила игры не меняются во время игры. Реализации должны быть <b>без состояния</b>
 * (stateless) и могут использоваться как синглтоны. Все методы принимают текущее состояние игры и
 * возвращают результат, не изменяя входные данные.
 *
 * <h2>Валидация</h2>
 *
 * <p>Методы {@link #initialize} и {@link #applyMove} выполняют валидацию входных данных и бросают
 * исключения при некорректных аргументах:
 *
 * <ul>
 *   <li>{@link IllegalArgumentException} — некорректные аргументы
 *   <li>{@link org.nxckywhxte.cardgame.api.exception.CardGameException} — нарушение правил игры
 * </ul>
 *
 * <p>Пример использования:
 *
 * <pre>{@code
 * GameRules rules = new KlondikeRules();
 * GameState state = rules.initialize(deck, players);
 *
 * while (!state.isFinished()) {
 *     List<Move> allowed = rules.getAllowedMoves(state);
 *     Move chosen = chooseMove(allowed);
 *     state = rules.applyMove(state, chosen);
 * }
 * }</pre>
 *
 * <h2>Потокобезопасность</h2>
 *
 * <p>Поскольку правила игры не имеют состояния, они потокобезопасны.
 *
 * @author nxckywhxte
 * @since 0.0.1
 * @see GameState
 * @see Move
 */
public interface GameRules {
  /**
   * Возвращает уникальное имя игры.
   *
   * <p>Используется для логирования, выбора правил и идентификации игры. Примеры: "klondike",
   * "durak", "spider".
   *
   * @return уникальное имя игры
   */
  String getGameName();

  /**
   * Возвращает минимальное количество игроков для этой игры.
   *
   * @return минимальное количество игроков
   */
  int getMinimumPlayers();

  /**
   * Возвращает максимальное количество игроков для этой игры.
   *
   * @return максимальное количество игроков
   */
  int getMaximumPlayers();

  /**
   * Инициализирует новую игру с указанными колодой и игроками.
   *
   * <p>Создаёт начальное состояние игры: раздаёт карты игрокам, заполняет зоны, определяет козырь
   * (если применимо) и устанавливает текущего игрока.
   *
   * <p>Метод выполняет валидацию:
   *
   * <ul>
   *   <li>Количество игроков должно быть в диапазоне {@link #getMinimumPlayers()} .. {@link
   *       #getMaximumPlayers()}
   *   <li>Колода должна быть достаточного размера для игры
   * </ul>
   *
   * @param deck колода для игры
   * @param players список игроков
   * @return начальное состояние игры
   * @throws IllegalArgumentException если аргументы некорректны
   */
  GameState initialize(Deck deck, List<Player> players);

  /**
   * Проверяет, допустим ли ход в текущем состоянии игры.
   *
   * <p>Метод не изменяет состояние игры. Он только проверяет, соответствует ли ход правилам игры.
   *
   * @param state текущее состояние игры
   * @param move ход для проверки
   * @return {@code true}, если ход допустим
   * @throws IllegalArgumentException если {@code state == null} или {@code move == null}
   */
  boolean validateMove(GameState state, Move move);

  /**
   * Применяет ход к текущему состоянию игры.
   *
   * <p>Возвращает <b>новое</b> состояние игры после применения хода. Исходное состояние не
   * изменяется.
   *
   * <p>Если ход невалиден, бросается исключение. Перед вызовом рекомендуется проверить ход через
   * {@link #validateMove}.
   *
   * @param state текущее состояние игры
   * @param move ход для применения
   * @return новое состояние игры после хода
   * @throws IllegalArgumentException если {@code state == null} или {@code move == null}
   * @throws CardGameException если ход невалиден
   */
  GameState applyMove(GameState state, Move move);

  /**
   * Проверяет, есть ли победитель в текущем состоянии игры.
   *
   * <p>Возвращает победителя, если игра завершена и победитель определён. Если игра не завершена
   * или победитель не определён (например, ничья), возвращается пустой {@link Optional}.
   *
   * @param state текущее состояние игры
   * @return {@link Optional} с победителем или пустой
   * @throws IllegalArgumentException если {@code state == null}
   */
  Optional<Player> checkVictory(GameState state);

  /**
   * Возвращает список всех допустимых ходов в текущем состоянии игры.
   *
   * <p>Используется для предоставления вариантов ходов игроку или ИИ. Возвращённый список является
   * неизменяемым.
   *
   * <p>Если игра завершена или допустимых ходов нет, возвращается пустой список.
   *
   * @param state текущее состояние игры
   * @return неизменяемый список допустимых ходов
   * @throws IllegalArgumentException если {@code state == null}
   */
  List<Move> getAllowedMoves(GameState state);
}
