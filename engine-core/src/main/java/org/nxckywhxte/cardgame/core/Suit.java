package org.nxckywhxte.cardgame.core;

/**
 * Масть игральной карты.
 *
 * <p>В стандартной колоде 52 карты существует четыре масти: червы (♥), бубны (♦), кресты (♣) и пики
 * (♠). Дополнительно поддерживается специальная масть {@link #JOKER} для джокеров, используемых в
 * некоторых вариантах игры в Дурака (колода из 54 карт).
 *
 * <p>Каждая масть имеет цвет (красный или чёрный), который используется в играх с чередованием
 * цветов, таких как Клондайк (Косынка). Для джокеров цвет определяется через их достоинство ({@link
 * Rank#RED_JOKER} или {@link Rank#BLACK_JOKER}), а масть {@link #JOKER} имеет цвет {@link
 * Color#NONE}.
 *
 * <p>Пример использования:
 *
 * <pre>{@code
 * Suit suit = Suit.HEARTS;
 * boolean isRed = suit.isRed(); // true
 * }</pre>
 *
 * @author nxckywhxte
 * @since 0.0.1
 * @see Card
 * @see Rank
 */
public enum Suit {
  /** Червы (♥) - красная масть. */
  HEARTS(Color.RED),
  /** Бубны (♦) - красная масть. */
  DIAMONDS(Color.RED),
  /** Кресты (♣) - черная масть. */
  CLUBS(Color.BLACK),
  /** Пики (♠) - черная масть. */
  SPADES(Color.BLACK),
  /** Специальная масть для джокеров. Цвет определяется через {@link Rank}. */
  JOKER(Color.NONE);
  private final Color color;

  Suit(Color color) {
    this.color = color;
  }

  /**
   * Возвращает цвет масти.
   *
   * @return цвет масти ({@link Color#RED}, {@link Color#BLACK} или {@link Color#NONE} для джокеров)
   */
  public Color getColor() {
    return color;
  }

  /**
   * Проверяет, является ли масть красной.
   *
   * <p>Для джокеров ({@link #JOKER}) возвращает {@code false}, так как их цвет определяется через
   * достоинство.
   *
   * @return {@code true}, если масть красная
   */
  public boolean isRed() {
    return color == Color.RED;
  }

  /**
   * Проверяет, является ли масть черной.
   *
   * <p>Для джокеров ({@link #JOKER}) возвращает {@code false}, так как их цвет определяется через
   * достоинство.
   *
   * @return {@code true}, если масть черная
   */
  public Boolean isBlack() {
    return color == Color.BLACK;
  }

  /**
   * Проверяет, является ли масть специальной мастью джокера.
   *
   * @return {@code true}, если масть — {@link #JOKER}
   */
  public boolean isJoker() {
    return this == JOKER;
  }

  /**
   * Проверяет, является ли масть стандартной (не джокер).
   *
   * @return {@code true}, если это одна из четырёх стандартных мастей
   */
  public boolean isStandard() {
    return this != JOKER;
  }

  /**
   * Цвет игральной карты.
   *
   * <p>Используется в играх с чередованием цветов, таких как Клондайк (Косынка). Для джокеров
   * используется значение {@link #NONE}, а их цвет определяется через достоинство.
   *
   * @author nxckywhxte
   * @since 0.0.1
   */
  public enum Color {
    /** Красный цвет (червы, бубны). */
    RED,

    /** Чёрный цвет (кресты, пики). */
    BLACK,

    /** Нет цвета (используется для джокеров). */
    NONE
  }
}
