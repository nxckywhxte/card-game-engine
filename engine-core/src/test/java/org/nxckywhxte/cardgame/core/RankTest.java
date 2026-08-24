package org.nxckywhxte.cardgame.core;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * Unit-тесты для {@link Rank}.
 *
 * @author nxckywhxte
 * @since 0.0.1
 */
@DisplayName("Rank: достоинства карт")
class RankTest {
  @Test
  @DisplayName("Должно быть ровно 15 достоинств")
  void shouldHaveExactlyFifteenRanks() {
    assertThat(Rank.values()).hasSize(15);
  }

  @Nested
  @DisplayName("Числовые значения")
  class ValueTests {

    @Test
    @DisplayName("Значения должны соответствовать номиналу")
    void valuesShouldMatchNominals() {
      assertThat(Rank.TWO.getValue()).isEqualTo(2);
      assertThat(Rank.TEN.getValue()).isEqualTo(10);
      assertThat(Rank.JACK.getValue()).isEqualTo(11);
      assertThat(Rank.QUEEN.getValue()).isEqualTo(12);
      assertThat(Rank.KING.getValue()).isEqualTo(13);
      assertThat(Rank.ACE.getValue()).isEqualTo(14);
    }

    @Test
    @DisplayName("Джокеры должны иметь наивысшие значения")
    void jokersShouldHaveHighestValues() {
      assertThat(Rank.RED_JOKER.getValue()).isEqualTo(15);
      assertThat(Rank.BLACK_JOKER.getValue()).isEqualTo(16);
    }
  }

  @Nested
  @DisplayName("Цвета достоинств")
  class ColorTests {

    @Test
    @DisplayName("Красный джокер должен быть красным")
    void redJokerShouldBeRed() {
      assertThat(Rank.RED_JOKER.getColor()).isEqualTo(Suit.Color.RED);
    }

    @Test
    @DisplayName("Чёрный джокер должен быть чёрным")
    void blackJokerShouldBeBlack() {
      assertThat(Rank.BLACK_JOKER.getColor()).isEqualTo(Suit.Color.BLACK);
    }

    @Test
    @DisplayName("Обычные карты не должны иметь цвета в Rank")
    void regularRanksShouldHaveNoColor() {
      assertThat(Rank.ACE.getColor()).isEqualTo(Suit.Color.NONE);
      assertThat(Rank.SIX.getColor()).isEqualTo(Suit.Color.NONE);
      assertThat(Rank.KING.getColor()).isEqualTo(Suit.Color.NONE);
    }
  }

  @Nested
  @DisplayName("Классификация достоинств")
  class ClassificationTests {

    @Test
    @DisplayName("Только JACK, QUEEN, KING должны быть фигурами")
    void onlyFaceCardsShouldBeFaceCards() {
      assertThat(Rank.JACK.isFaceCard()).isTrue();
      assertThat(Rank.QUEEN.isFaceCard()).isTrue();
      assertThat(Rank.KING.isFaceCard()).isTrue();

      assertThat(Rank.ACE.isFaceCard()).isFalse();
      assertThat(Rank.TEN.isFaceCard()).isFalse();
      assertThat(Rank.RED_JOKER.isFaceCard()).isFalse();
    }

    @Test
    @DisplayName("Только джокеры должны возвращать true для isJoker()")
    void onlyJokersShouldBeJokers() {
      assertThat(Rank.RED_JOKER.isJoker()).isTrue();
      assertThat(Rank.BLACK_JOKER.isJoker()).isTrue();

      assertThat(Rank.ACE.isJoker()).isFalse();
      assertThat(Rank.TWO.isJoker()).isFalse();
    }

    @Test
    @DisplayName("Числовые карты должны быть от 2 до 10")
    void numericCardsShouldBeFromTwoToTen() {
      assertThat(Rank.TWO.isNumeric()).isTrue();
      assertThat(Rank.TEN.isNumeric()).isTrue();

      assertThat(Rank.JACK.isNumeric()).isFalse();
      assertThat(Rank.ACE.isNumeric()).isFalse();
      assertThat(Rank.RED_JOKER.isNumeric()).isFalse();
    }
  }
}
