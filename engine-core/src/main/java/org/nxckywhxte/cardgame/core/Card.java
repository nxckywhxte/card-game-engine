package org.nxckywhxte.cardgame.core;

/**
 * Игральная карта.
 *
 * <p>Карта является неизменяемым объектом, который представляет одну игральную карту с определённой
 * мастью, достоинством и состоянием (открыта или закрыта).
 *
 * <p>Поскольку карта неизменяема, все операции, которые "изменяют" её состояние (например,
 * переворот), возвращают <b>новый</b> экземпляр карты, не изменяя исходный. Это гарантирует
 * потокобезопасность и предсказуемое поведение.
 *
 * <p>Поддерживаются как обычные карты, так и джокеры:
 *
 * <ul>
 *   <li>Обычная карта: {@code new Card(Suit.HEARTS, Rank.ACE, true)}
 *   <li>Красный джокер: {@code new Card(Suit.JOKER, Rank.RED_JOKER, true)}
 *   <li>Чёрный джокер: {@code new Card(Suit.JOKER, Rank.BLACK_JOKER, false)}
 * </ul>
 *
 * <h2>Важно: сравнение карт</h2>
 *
 * <p>Метод {@link #compareTo(Card)} определяет <b>естественный порядок сортировки</b> карт
 * (например, для раскладки в руке игрока), но <b>НЕ определяет правила сравнения в конкретных
 * играх</b>. Например, в Дураке красный джокер бьёт только красные карты, а чёрный — только чёрные.
 * Эта логика реализуется в модуле правил ({@code engine-rules-durak}), а не здесь.
 *
 * <p>Пример использования:
 *
 * <pre>{@code
 * Card card = new Card(Suit.HEARTS, Rank.ACE, false); // закрытая карта
 * Card revealed = card.reveal(); // теперь карта открыта
 * Card flipped = card.flip(); // карта перевёрнута (стала открытой)
 * boolean isRed = revealed.isRed(); // true
 * Suit.Color color = card.getColor(); // RED
 * }</pre>
 *
 * @param suit масть карты, не может быть {@code null}
 * @param rank достоинство карты, не может быть {@code null}
 * @param isFaceUp открыта ли карта (лицевой стороной вверх)
 * @author nxckywhxte
 * @since 0.0.1
 * @see Suit
 * @see Rank
 */
public record Card(Suit suit, Rank rank, boolean isFaceUp) implements Comparable<Card> {
  /**
   * Компактный конструктор для валидации входных данных.
   *
   * <p>Проверяет, что масть и достоинство не являются {@code null}. Выбрасывает {@link
   * IllegalArgumentException}, если одно из значений отсутствует.
   */
  public Card {
    if (suit == null) {
      throw new IllegalArgumentException("Suit cannot be null");
    }
    if (rank == null) {
      throw new IllegalArgumentException("Rank cannot be null");
    }
    if (suit.isJoker() && !rank.isJoker()) {
      throw new IllegalArgumentException("Joker suit must be paired with Joker rank, got: " + rank);
    }
    if (rank.isJoker() && !suit.isJoker()) {
      throw new IllegalArgumentException("Joker rank must be paired with Joker suit, got: " + suit);
    }
  }

  /**
   * Переворачивает карту (меняет состояние открыта/закрыта).
   *
   * <p>Поскольку карта неизменяема, возвращается новый экземпляр с инвертированным состоянием.
   *
   * @return новая карта с противоположным состоянием {@code isFaceUp}
   */
  public Card flip() {
    return new Card(this.suit, this.rank, !this.isFaceUp);
  }

  /**
   * Возвращает копию карты в открытом состоянии.
   *
   * <p>Удобно при раздаче карт, когда нужно показать карту игрокам.
   *
   * @return новая карта с {@code isFaceUp = true}
   */
  public Card reveal() {
    return new Card(this.suit, this.rank, true);
  }

  /**
   * Возвращает копию карты в закрытом состоянии.
   *
   * <p>Удобно при помещении карты в колоду рубашкой вверх.
   *
   * @return новая карта с {@code isFaceUp = false}
   */
  public Card conceal() {
    return new Card(this.suit, this.rank, false);
  }

  /**
   * Проверяет, является ли карта джокером.
   *
   * @return {@code true}, если достоинство карты — {@link Rank#RED_JOKER} или {@link
   *     Rank#BLACK_JOKER}
   */
  public boolean isJoker() {
    return rank.isJoker();
  }

  /**
   * Возвращает цвет карты.
   *
   * <p>Для обычных карт цвет определяется мастью. Для джокеров — достоинством:
   *
   * <ul>
   *   <li>{@link Rank#RED_JOKER} → {@link Suit.Color#RED}
   *   <li>{@link Rank#BLACK_JOKER} → {@link Suit.Color#BLACK}
   * </ul>
   *
   * @return цвет карты (никогда не возвращает {@link Suit.Color#NONE})
   */
  public Suit.Color getColor() {
    // Сначала проверяем цвет достоинства (для джокеров)
    Suit.Color rankColor = rank.getColor();
    if (rankColor != Suit.Color.NONE) {
      return rankColor;
    }
    // Иначе используем цвет масти
    return suit.getColor();
  }

  /**
   * Проверяет, является ли карта красной.
   *
   * <p>Для обычных карт цвет определяется мастью. Для джокеров — достоинством:
   *
   * <ul>
   *   <li>{@link Rank#RED_JOKER} — красный
   *   <li>{@link Rank#BLACK_JOKER} — чёрный
   * </ul>
   *
   * @return {@code true}, если карта красная
   */
  public boolean isRed() {
    if (rank.isRedJoker()) {
      return true;
    }
    if (rank.isBlackJoker()) {
      return false;
    }
    return suit.isRed();
  }

  /**
   * Проверяет, является ли карта чёрной.
   *
   * @return {@code true}, если карта чёрная
   * @see #isRed()
   */
  public boolean isBlack() {
    return !isRed();
  }

  /**
   * Создаёт карту лицом вверх (открытую).
   *
   * @param suit масть карты
   * @param rank достоинство карты
   * @return новая открытая карта
   */
  public static Card faceUp(Suit suit, Rank rank) {
    return new Card(suit, rank, true);
  }

  /**
   * Создаёт карту лицом вниз (закрытую).
   *
   * @param suit масть карты
   * @param rank достоинство карты
   * @return новая закрытая карта
   */
  public static Card faceDown(Suit suit, Rank rank) {
    return new Card(suit, rank, false);
  }

  /**
   * Возвращает числовое значение достоинства карты.
   *
   * @return числовое значение от 2 до 16
   */
  public int getValue() {
    return rank.getValue();
  }

  /**
   * Проверяет, является ли карта фигурой (валет, дама, король).
   *
   * @return {@code true}, если это фигурная карта
   */
  public boolean isFaceCard() {
    return rank.isFaceCard();
  }

  /**
   * Проверяет, является ли карта числовой (от 2 до 10).
   *
   * @return {@code true}, если это числовая карта
   */
  public boolean isNumeric() {
    return rank.isNumeric();
  }

  /**
   * Сравнивает эту карту с другой по достоинству, затем по масти, затем по состоянию.
   *
   * <p>Порядок сравнения:
   *
   * <ol>
   *   <li>Достоинство карты ({@link Rank})
   *   <li>Масть карты ({@link Suit})
   *   <li>Состояние "открыта/закрыта" ({@code isFaceUp}): закрытые карты ({@code false}) идут перед
   *       открытыми ({@code true})
   * </ol>
   *
   * <p>Этот метод полностью согласован с {@link #equals(Object)}: результат {@code compareTo} равен
   * нулю тогда и только тогда, когда карты равны по {@code equals}. Это гарантирует корректную
   * работу со всеми стандартными коллекциями ({@code TreeSet}, {@code TreeMap}, {@code
   * Collections.sort} и т.д.).
   *
   * <p><b>Важно:</b> этот метод определяет естественный порядок сортировки, но не определяет
   * правила сравнения в конкретных играх. Например, в Дураке джокеры бьют только карты своего
   * цвета, что реализуется в модуле правил ({@code engine-rules-durak}).
   *
   * @param other другая карта для сравнения
   * @return отрицательное число, если текущая карта меньше, 0 — если равны, положительное — если
   *     больше
   */
  @Override
  public int compareTo(Card other) {
    int rankComparison = this.rank.compareTo(other.rank);
    if (rankComparison != 0) {
      return rankComparison;
    }
    int suitComparison = this.suit.compareTo(other.suit);
    if (suitComparison != 0) {
      return suitComparison;
    }
    return Boolean.compare(this.isFaceUp, other.isFaceUp);
  }
}
