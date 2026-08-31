# Использование библиотеки

> ⚠️ **Внимание:** примеры ниже концептуальные. Интерфейсы `engine-api` ещё
> находятся в разработке (Фаза 1). После реализации примеры будут обновлены
> и сопровождаться рабочими тестами.

## 🎮 Пример 1: Партия в Клондайк (Косынку)

```java
import org.nxckywhxte.cardgame.api.Deck;
import org.nxckywhxte.cardgame.api.GameRules;
import org.nxckywhxte.cardgame.api.GameState;
import org.nxckywhxte.cardgame.api.Move;
import org.nxckywhxte.cardgame.api.Player;

public final class Main() {
    static void main() {
        // Создаём полную колоду (52 карты) и тасуем
        Deck deck = DeckFactory.standard52();
        deck.shuffle();

        // Инициализируем игру для одного игрока
        GameRules rules = new KlondikeRules();
        GameState state = rules.initialize(deck, List.of(new StandardPlayer("Alice")));

        // Игровой цикл
        while (!state.isFinished()) {
            List<Move> allowed = rules.getAllowedMoves(state);
            Move chosen = chooseMove(allowed); // логика приложения (ИИ или игрок)
            state = rules.applyMove(state, chosen);
        }
    }
}
```

## Пример 2: Партия в Дурака с джокерами

```java
import org.nxckywhxte.cardgame.api.Deck;
import org.nxckywhxte.cardgame.api.GameRules;
import org.nxckywhxte.cardgame.api.GameState;
import org.nxckywhxte.cardgame.api.Move;
import org.nxckywhxte.cardgame.api.Player;

public final class Main() {
    static void main() {
        // Колода из 36 карт + 2 джокера
        Deck deck = DeckFactory.durak36WithJokers();
        deck.shuffle();

        GameRules rules = new DurakRules();
        GameState state = rules.initialize(deck, List.of(
                new StandardPlayer("Alice"),
                new StandardPlayer("Bob")
        ));

        // Козырь определяется автоматически при инициализации
        System.out.println("Козырь: " + state.getTrumpSuit());

        while (!state.isFinished()) {
            // Логика атаки и защиты...
            state = rules.applyMove(state, currentMove);
        }
    }
}
```

## Пример 3: Создание собственной игры

```java
public class MyGameRules implements GameRules {

    @Override
    public GameState initialize(Deck deck, List<Player> players) {
        // Раздача карт, подготовка зон
    }

    @Override
    public boolean validateMove(GameState state, Move move) {
        // Проверка допустимости хода по правилам вашей игры
    }

    @Override
    public GameState applyMove(GameState state, Move move) {
        // Применение хода, возврат нового состояния
    }

    @Override
    public Player checkVictory(GameState state) {
        // Проверка условия победы
    }

    @Override
    public List<Move> getAllowedMoves(GameState state) {
        // Список всех допустимых ходов в текущем состоянии
    }
}
```

## 🧩 Ключевые концепции

- **Card** — неизменяемая карта (масть + достоинство + состояние)
- **Deck** — колода карт с возможностью тасования и взятия
- **Zone** — любая область для карт (рука игрока, стол, сброс)
- **Move** — действие игрока (перемещение карты)
- **GameState** — полное состояние игры в конкретный момент
- **GameRules** — правила конкретной игры