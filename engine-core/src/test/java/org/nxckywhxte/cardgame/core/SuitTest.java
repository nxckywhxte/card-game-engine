package org.nxckywhxte.cardgame.core;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * Unit-тесты для {@link Suit}.
 *
 * @author nxckywhxte
 * @since 0.0.1
 */
@DisplayName("Suit: масти карт")
class SuitTest {
  @Test
  @DisplayName("Должно быть ровно 5 мастей (4 стандартные и JOKER")
  void shouldHaveExactlyFiveSuits() {
    assertThat(Suit.values()).hasSize(5);
  }

  @Nested
  @DisplayName("Цвета мастей")
  class ColorTests {
    @Test
    @DisplayName("HEARTS и DIAMONDS должны быть красными")
    void redSuitShouldBeRed() {
      assertThat(Suit.HEARTS.isRed()).isTrue();
      assertThat(Suit.DIAMONDS.isRed()).isTrue();
    }

    @Test
    @DisplayName("CLUBS и SPADES должны быть чёрными")
    void blackSuitsShouldBeBlack() {
      assertThat(Suit.CLUBS.isBlack()).isTrue();
      assertThat(Suit.SPADES.isBlack()).isTrue();
    }

    @Test
    @DisplayName("JOKER не должен быть ни красным, ни чёрным")
    void jokerShouldHaveNoColor() {
      assertThat(Suit.JOKER.isRed()).isFalse();
      assertThat(Suit.JOKER.isBlack()).isFalse();
      assertThat(Suit.JOKER.getColor()).isEqualTo(Suit.Color.NONE);
    }
  }

  @Nested
  @DisplayName("Проверка джокера")
  class JokerTests {

    @Test
    @DisplayName("только JOKER должен возвращать true для isJoker()")
    void onlyJokerSuitShouldBeJoker() {
      assertThat(Suit.JOKER.isJoker()).isTrue();
      assertThat(Suit.HEARTS.isJoker()).isFalse();
      assertThat(Suit.DIAMONDS.isJoker()).isFalse();
      assertThat(Suit.CLUBS.isJoker()).isFalse();
      assertThat(Suit.SPADES.isJoker()).isFalse();
    }
  }
}
