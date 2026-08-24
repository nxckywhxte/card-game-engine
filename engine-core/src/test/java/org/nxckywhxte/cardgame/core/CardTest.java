package org.nxckywhxte.cardgame.core;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeSet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * Unit-тесты для {@link Card}.
 *
 * @author nxckywhxte
 * @since 0.0.1
 */
@DisplayName("Card: игральная карта")
class CardTest {
  @Nested
  @DisplayName("Создание карт")
  class CreationTests {

    @Test
    @DisplayName("Должна создаваться обычная карта")
    void shouldCreateRegularCard() {
      Card card = new Card(Suit.HEARTS, Rank.ACE, true);

      assertThat(card.suit()).isEqualTo(Suit.HEARTS);
      assertThat(card.rank()).isEqualTo(Rank.ACE);
      assertThat(card.isFaceUp()).isTrue();
    }

    @Test
    @DisplayName("Должны создаваться джокеры")
    void shouldCreateJokers() {
      Card redJoker = new Card(Suit.JOKER, Rank.RED_JOKER, true);
      Card blackJoker = new Card(Suit.JOKER, Rank.BLACK_JOKER, false);

      assertThat(redJoker.isJoker()).isTrue();
      assertThat(blackJoker.isJoker()).isTrue();
      assertThat(redJoker.isRed()).isTrue();
      assertThat(blackJoker.isBlack()).isTrue();
    }
  }

  @Nested
  @DisplayName("Валидация в конструкторе")
  class ValidationTests {

    @Test
    @DisplayName("Должен бросать исключение при null масти")
    void shouldThrowOnNullSuit() {
      assertThatThrownBy(() -> new Card(null, Rank.ACE, true))
          .isInstanceOf(IllegalArgumentException.class)
          .hasMessageContaining("Suit cannot be null");
    }

    @Test
    @DisplayName("Должен бросать исключение при null достоинстве")
    void shouldThrowOnNullRank() {
      assertThatThrownBy(() -> new Card(Suit.HEARTS, null, true))
          .isInstanceOf(IllegalArgumentException.class)
          .hasMessageContaining("Rank cannot be null");
    }

    @Test
    @DisplayName("Должен бросать исключение при JOKER масти с обычным рангом")
    void shouldThrowOnJokerSuitWithRegularRank() {
      assertThatThrownBy(() -> new Card(Suit.JOKER, Rank.ACE, true))
          .isInstanceOf(IllegalArgumentException.class)
          .hasMessageContaining("Joker suit must be paired with Joker rank");
    }

    @Test
    @DisplayName("Должен бросать исключение при обычном масти с джокерным рангом")
    void shouldThrowOnRegularSuitWithJokerRank() {
      assertThatThrownBy(() -> new Card(Suit.HEARTS, Rank.RED_JOKER, true))
          .isInstanceOf(IllegalArgumentException.class)
          .hasMessageContaining("Joker rank must be paired with Joker suit");
    }
  }

  @Nested
  @DisplayName("Операции над картой")
  class OperationTests {

    @Test
    @DisplayName("flip() должна возвращать новую карту с противоположным состоянием")
    void flipShouldReturnNewCardWithInvertedState() {
      Card closed = new Card(Suit.HEARTS, Rank.ACE, false);
      Card flipped = closed.flip();

      // Исходная карта не изменилась
      assertThat(closed.isFaceUp()).isFalse();
      // Новая карта имеет противоположное состояние
      assertThat(flipped.isFaceUp()).isTrue();
      // Масть и достоинство сохранились
      assertThat(flipped.suit()).isEqualTo(closed.suit());
      assertThat(flipped.rank()).isEqualTo(closed.rank());
    }

    @Test
    @DisplayName("reveal() должна возвращать открытую карту")
    void revealShouldReturnFaceUpCard() {
      Card closed = new Card(Suit.HEARTS, Rank.ACE, false);
      Card revealed = closed.reveal();

      assertThat(revealed.isFaceUp()).isTrue();
    }

    @Test
    @DisplayName("conceal() должна возвращать закрытую карту")
    void concealShouldReturnFaceDownCard() {
      Card open = new Card(Suit.HEARTS, Rank.ACE, true);
      Card concealed = open.conceal();

      assertThat(concealed.isFaceUp()).isFalse();
    }
  }

  @Nested
  @DisplayName("Определение цвета")
  class ColorTests {

    @Test
    @DisplayName("Обычная карта должна получать цвет от масти")
    void regularCardShouldGetColorFromSuit() {
      assertThat(new Card(Suit.HEARTS, Rank.ACE, true).getColor()).isEqualTo(Suit.Color.RED);
      assertThat(new Card(Suit.SPADES, Rank.SIX, true).getColor()).isEqualTo(Suit.Color.BLACK);
    }

    @Test
    @DisplayName("Джокеры должны получать цвет от ранга")
    void jokersShouldGetColorFromRank() {
      assertThat(new Card(Suit.JOKER, Rank.RED_JOKER, true).getColor()).isEqualTo(Suit.Color.RED);
      assertThat(new Card(Suit.JOKER, Rank.BLACK_JOKER, true).getColor())
          .isEqualTo(Suit.Color.BLACK);
    }
  }

  @Nested
  @DisplayName("Контракт Comparable")
  class ComparableTests {
    @Test
    @DisplayName("compareTo должен быть согласован с equals")
    void compareToShouldBeConsistentWithEquals() {
      Card card1 = new Card(Suit.HEARTS, Rank.ACE, true);
      Card card2 = new Card(Suit.HEARTS, Rank.ACE, true);
      Card card3 = new Card(Suit.HEARTS, Rank.ACE, false); // отличается isFaceUp

      // Все проверки в одной цепочке
      assertThat(card1)
          .isEqualByComparingTo(card2)
          .isEqualTo(card2)
          .isNotEqualByComparingTo(card3)
          .isNotEqualTo(card3);
    }

    @Test
    @DisplayName("Карты должны сортироваться по достоинству")
    void cardsShouldBeSortedByRankFirst() {
      List<Card> cards = new ArrayList<>();
      cards.add(new Card(Suit.HEARTS, Rank.KING, true));
      cards.add(new Card(Suit.SPADES, Rank.SIX, false));
      cards.add(new Card(Suit.CLUBS, Rank.ACE, true));

      Collections.sort(cards);

      // Сначала SIX, потом KING, потом ACE
      assertThat(cards.get(0).rank()).isEqualTo(Rank.SIX);
      assertThat(cards.get(1).rank()).isEqualTo(Rank.KING);
      assertThat(cards.get(2).rank()).isEqualTo(Rank.ACE);
    }

    @Test
    @DisplayName("При равном достоинстве карты должны сортироваться по масти")
    void cardsWithEqualRankShouldBeSortedBySuit() {
      List<Card> cards = new ArrayList<>();
      cards.add(new Card(Suit.SPADES, Rank.ACE, true)); // ordinal = 3
      cards.add(new Card(Suit.HEARTS, Rank.ACE, true)); // ordinal = 0
      cards.add(new Card(Suit.CLUBS, Rank.ACE, true)); // ordinal = 2

      Collections.sort(cards);

      // HEARTS(0) < CLUBS(2) < SPADES(3)
      assertThat(cards.get(0).suit()).isEqualTo(Suit.HEARTS);
      assertThat(cards.get(1).suit()).isEqualTo(Suit.CLUBS);
      assertThat(cards.get(2).suit()).isEqualTo(Suit.SPADES);
    }

    @Test
    @DisplayName("При равных достоинстве и масти карты должны сортироваться по состоянию")
    void cardsWithEqualRankAndSuitShouldBeSortedByFaceUp() {
      List<Card> cards = new ArrayList<>();
      cards.add(new Card(Suit.HEARTS, Rank.ACE, true)); // isFaceUp = true
      cards.add(new Card(Suit.HEARTS, Rank.ACE, false)); // isFaceUp = false

      Collections.sort(cards);

      // false < true, поэтому закрытая идёт первой
      assertThat(cards.get(0).isFaceUp()).isFalse();
      assertThat(cards.get(1).isFaceUp()).isTrue();
    }

    @Test
    @DisplayName("TreeSet не должен терять карты с разным isFaceUp")
    void treeSetShouldNotLoseCardsWithDifferentFaceUpState() {
      TreeSet<Card> treeSet = new TreeSet<>();
      treeSet.add(new Card(Suit.HEARTS, Rank.ACE, true));
      treeSet.add(new Card(Suit.HEARTS, Rank.ACE, false));

      // Обе карты должны сохраниться, так как они различаются по compareTo
      assertThat(treeSet).hasSize(2);
    }
  }
}
