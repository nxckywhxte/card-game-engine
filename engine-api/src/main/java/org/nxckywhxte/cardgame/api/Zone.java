package org.nxckywhxte.cardgame.api;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import org.nxckywhxte.cardgame.api.exception.CardNotFoundException;
import org.nxckywhxte.cardgame.core.Card;

/**
 * Зона для хранения карт в игре.
 *
 * <p>Зона представляет любую область, где могут находиться карты: рука игрока, стол, сброс,
 * фундамент, табло и т.д.
 *
 * <h2>Иммутабельность</h2>
 *
 * <p>Зона является <b>полностью неизменяемой</b>. Все операции, которые "изменяют" зону (добавление
 * или удаление карт), возвращают <b>новые</b> экземпляры зон, не изменяя исходную. Это критично для
 * реализации отмены ходов, симуляций и сетевой игры.
 *
 * <h2>Порядок карт</h2>
 *
 * <p>Карты в зоне упорядочены. "Верхняя" карта — последняя в списке. Для зон, где порядок не важен
 * (например, сброс), порядок можно игнорировать.
 *
 * <p>Пример использования:
 *
 * <pre>{@code
 * Zone hand = ZoneFactory.empty();
 * Zone updated = hand.addCard(card);
 * Optional<Card> topCard = updated.peek();
 * }</pre>
 *
 * <h2>Потокобезопасность</h2>
 *
 * <p>Поскольку зона неизменяема, она потокобезопасна.
 *
 * @author nxckywhxte
 * @since 0.0.1
 * @see Card
 * @see Deck
 */
public interface Zone {
  /**
   * Возвращает количество карт в зоне.
   *
   * @return количество карт
   */
  int size();

  /**
   * Проверяет, пуста ли зона.
   *
   * @return {@code True}, если в зоне нет карт
   */
  boolean isEmpty();

  /**
   * Возвращает неизменяемый список всех карт в зоне от верха к низу.
   *
   * <p>Первый элемент списка - верхняя карта, последняя нижняя.
   *
   * @return неизменяемый список карт
   */
  List<Card> getCards();

  /**
   * Проверяет, содержится ли карта в зоне.
   *
   * @param card карта для поиска
   * @return {@code true}, если карта есть в зоне
   */
  boolean contains(Card card);

  /**
   * Добавляет карту наверх зоны.
   *
   * <p>Возвращает <b>новую</b> зону с добавленной картой. Исходная зона не изменяется.
   *
   * @param card карта для добавления
   * @return новая зона с добавленной картой
   * @throws IllegalArgumentException если {@code card == null}
   */
  Zone addCard(Card card);

  /**
   * Удаляет карту из зоны.
   *
   * <p>Возвращает <b>новую</b> зону без указанной карты. Исходная зона не изменяется.
   *
   * @param card карта для удаления
   * @return новая зона без удалённой карты
   * @throws CardNotFoundException если карты нет в зоне
   * @throws IllegalArgumentException если {@code card == null}
   */
  Zone removeCard(Card card);

  /**
   * Возвращает верхнюю карту зоны, не удаляя её.
   *
   * @return {@link Optional} с верхней картой или пустой, если зона пуста
   */
  Optional<Card> peek();

  /**
   * Находит первую карту, удовлетворяющую условию.
   *
   * <p>Поиск идёт от верха зоны к низу.
   *
   * @param predicate условие для поиска
   * @return {@link Optional} с найденной картой или пустой, если не найдена
   * @throws IllegalArgumentException если {@code predicate == null}
   */
  Optional<Card> findCard(Predicate<Card> predicate);

  /**
   * Возвращает новую зону, содержащую только карты, удовлетворяющие условию.
   *
   * <p>Полезно для фильтрации карт, например, выбрать все красные карты.
   *
   * @param predicate условие для фильтрации
   * @return новая зона с отфильтрованными картами
   * @throws IllegalArgumentException если {@code predicate == null}
   */
  Zone filter(Predicate<Card> predicate);
}
