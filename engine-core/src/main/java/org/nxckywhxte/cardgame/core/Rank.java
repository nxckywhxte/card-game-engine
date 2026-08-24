package org.nxckywhxte.cardgame.core;

/**
 * Достоинство игральной карты.
 *
 * <p>Включает все возможные достоинства карт, используемые в различных играх:
 *
 * <ul>
 *   <li>Числовые карты: от {@link #TWO} до {@link #TEN}
 *   <li>Фигурные карты: {@link #JACK}, {@link #QUEEN}, {@link #KING}
 *   <li>Туз: {@link #ACE}
 *   <li>Джокеры: {@link #RED_JOKER}, {@link #BLACK_JOKER}
 * </ul>
 *
 * <p>Числовые значения используются для сравнения карт и подсчёта очков:
 *
 * <ul>
 *   <li>2–10 — значение равно номиналу карты
 *   <li>Валет = 11, Дама = 12, Король = 13, Туз = 14
 *   <li>Красный джокер = 15, Чёрный джокер = 16
 * </ul>
 *
 * <p>Разные игры используют подмножество достоинств:
 *
 * <ul>
 *   <li>Дурак (24 карты): {@link #NINE} — {@link #ACE} (+ джокеры)
 *   <li>Дурак (36 карт): {@link #SIX} — {@link #ACE} (+ джокеры)
 *   <li>Дурак (52 карты): {@link #TWO} — {@link #ACE} (+ джокеры)
 *   <li>Клондайк: {@link #TWO} — {@link #ACE} (без джокеров)
 *   <li>Паук: {@link #TWO} — {@link #ACE} (без джокеров)
 * </ul>
 *
 * <h2>Важно: сравнение достоинств</h2>
 *
 * <p>Метод {@link #compareTo(Rank)} определяет <b>естественный порядок сортировки</b> достоинств,
 * но <b>НЕ определяет правила сравнения в конкретных играх</b>. Например, в Дураке красный джокер
 * бьёт только красные карты, а чёрный — только чёрные. Эта логика реализуется в модуле правил
 * ({@code engine-rules-durak}), а не здесь.
 *
 * <p>Пример использования:
 *
 * <pre>{@code
 * Rank rank = Rank.ACE;
 * int value = rank.getValue(); // 14
 * boolean isFace = rank.isFaceCard(); // false
 * Suit.Color color = rank.getColor(); // NONE (не джокер)
 * }</pre>
 *
 * @author nxckywhxte
 * @since 0.0.1
 * @see Card
 * @see Suit
 */
public enum Rank {
  /** Двойка (2). */
  TWO(2),

  /** Тройка (3). */
  THREE(3),

  /** Четвёрка (4). */
  FOUR(4),

  /** Пятёрка (5). */
  FIVE(5),

  /** Шестёрка (6). Минимальное достоинство в Дураке с колодой из 36 карт. */
  SIX(6),

  /** Семёрка (7). */
  SEVEN(7),

  /** Восьмёрка (8). */
  EIGHT(8),

  /** Девятка (9). Минимальное достоинство в Дураке с колодой из 24 карт. */
  NINE(9),

  /** Десятка (10). */
  TEN(10),

  /** Валет (11). Фигурная карта. */
  JACK(11),

  /** Дама (12). Фигурная карта. */
  QUEEN(12),

  /** Король (13). Фигурная карта. */
  KING(13),

  /** Туз (14). Старшая карта в большинстве игр. */
  ACE(14),

  /** Красный джокер (15). Бьёт все красные карты в Дураке. */
  RED_JOKER(15),

  /** Чёрный джокер (16). Бьёт все чёрные карты в Дураке. Старше красного джокера. */
  BLACK_JOKER(16);

  private final int value;

  Rank(int value) {
    this.value = value;
  }

  /**
   * Возвращает числовое значение достоинства карты.
   *
   * <p>Используется для сравнения карт и подсчёта очков. Чем больше значение, тем старше карта.
   *
   * @return числовое значение от 2 до 16
   */
  public int getValue() {
    return value;
  }

  /**
   * Возвращает цвет достоинства карты.
   *
   * <p>Для джокеров возвращает их цвет ({@link Suit.Color#RED} или {@link Suit.Color#BLACK}). Для
   * всех остальных достоинств возвращает {@link Suit.Color#NONE}, так как их цвет определяется
   * мастью, а не достоинством.
   *
   * @return цвет достоинства ({@link Suit.Color#RED}, {@link Suit.Color#BLACK} или {@link
   *     Suit.Color#NONE})
   */
  public Suit.Color getColor() {
    if (this == RED_JOKER) {
      return Suit.Color.RED;
    }
    if (this == BLACK_JOKER) {
      return Suit.Color.BLACK;
    }
    return Suit.Color.NONE;
  }

  /**
   * Проверяет, является ли достоинство фигурой (валет, дама или король).
   *
   * @return {@code true}, если это {@link #JACK}, {@link #QUEEN} или {@link #KING}
   */
  public boolean isFaceCard() {
    return this == JACK || this == QUEEN || this == KING;
  }

  /**
   * Проверяет, является ли достоинство джокером.
   *
   * @return {@code true}, если это {@link #RED_JOKER} или {@link #BLACK_JOKER}
   */
  public boolean isJoker() {
    return this == RED_JOKER || this == BLACK_JOKER;
  }

  /**
   * Проверяет, является ли достоинство красным джокером.
   *
   * @return {@code true}, если это {@link #RED_JOKER}
   */
  public boolean isRedJoker() {
    return this == RED_JOKER;
  }

  /**
   * Проверяет, является ли достоинство чёрным джокером.
   *
   * @return {@code true}, если это {@link #BLACK_JOKER}
   */
  public boolean isBlackJoker() {
    return this == BLACK_JOKER;
  }

  /**
   * Проверяет, является ли достоинство числовой картой (от 2 до 10).
   *
   * @return {@code true}, если это числовая карта
   */
  public boolean isNumeric() {
    return value >= 2 && value <= 10;
  }
}
