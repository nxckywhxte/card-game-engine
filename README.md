# 🃏 Card Game Engine

**Модульная библиотека для разработки карточных игр на Java 25.**

[![Build Status](https://github.com/nxckywhxte/card-game-engine/actions/workflows/build.yml/badge.svg)](https://github.com/nxckywhxte/card-game-engine/actions)
[![Java](https://img.shields.io/badge/Java-25-orange.svg)](https://openjdk.org/)
[![Maven](https://img.shields.io/badge/Maven-3.9-blue.svg)](https://maven.apache.org/)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)
[![Conventional Commits](https://img.shields.io/badge/Conventional%20Commits-1.0.0-%23FE5196)](https://conventionalcommits.org)

> ⚠️ **Статус проекта: активная разработка (версия 0.0.1)**
>
> Публичный API ещё не стабилизирован. До релиза `1.0.0` возможны ломающие изменения.
> Подробности — в [плане разработки](docs/ROADMAP.md).

---

Переиспользуемый движок для создания карточных игр: **Дурак**, **Клондайк (Косынка)**, **Паук**.
Спроектирован с акцентом на строгий контроль качества, неизменяемость и чистую архитектуру.

## ✨ Ключевые возможности

- 🎯 **Единый API** для всех карточных игр через интерфейс `GameRules`
- 🃏 **Поддержка колод** 24, 36, 52 и 54 карты (с джокерами)
- 🔒 **Иммутабельные сущности** — карты и состояния потокобезопасны
- 🎭 **Полная поддержка джокеров** — красный и чёрный с правильной семантикой
- 📏 **Строгий режим качества**: `-Xlint:all -Werror`, Spotless, JaCoCo, Git-хуки
- 🧩 **Модульная архитектура** — добавление новой игры требует только реализации `GameRules`
- ⚡ **Быстрая сборка** — параллельный билд, кэширование, Maven Wrapper

## 🏗️ Структура проекта

```
card-game-engine/
├── engine-core/ ✅ Базовые сущности: Suit, Rank, Card
├── engine-api/ 🔜 Публичные интерфейсы (Фаза 1)
├── engine-impl/ 🔜 Стандартные реализации (Фаза 1)
├── engine-rules-klondike/ ⏳ Правила Клондайка (Фаза 2)
├── engine-rules-spider/ ⏳ Правила Паука (Фаза 3)
├── engine-rules-durak/ ⏳ Правила Дурака (Фаза 3)
└── docs/ 📚 Архитектура, план, ADR
```

Зависимости направлены только вниз: `rules-*` → `api` → `core`.

## 🚀 Быстрый старт

> ⚠️ Пример концептуальный. Интерфейсы `engine-api` находятся в разработке.

```java
public final class Main() {
    static void main() {
        // Создаём колоду и тасуем
        Deck deck = DeckFactory.standard52();
        deck.shuffle();

        // Инициализируем игру
        GameRules rules = new KlondikeRules();
        GameState state = rules.initialize(deck, List.of(new StandardPlayer("Alice")));

        // Игровой цикл
        while (!state.isFinished()) {
            List<Move> allowed = rules.getAllowedMoves(state);
            Move chosen = chooseMove(allowed); // логика приложения
            state = rules.applyMove(state, chosen);
        }
    }
}
```

Подробнее — [в документации по использованию](docs/USAGE.md).

## 📋 Требования

| Инструмент | Версия                               |
|------------|--------------------------------------|
| Java       | 25+ (LTS)                            |
| Maven      | 3.9+ (или используйте Maven Wrapper) |
| Git        | 2.30+ (для работы с хуками)          |

Maven Wrapper (``mvnw``) включён в проект — отдельная установка Maven не требуется.

## 📦 Сборка

```bash
# Клонировать репозиторий
git clone https://github.com/nxckywhxte/card-game-engine.git
cd card-game-engine

# Установить Git-хуки (проверка форматирования и тестов перед коммитом)
./git-hooks/setup.sh

# Собрать проект с тестами
./mvnw clean verify          # Linux/macOS
.\mvnw.cmd clean verify      # Windows

# Автоматически исправить форматирование
./mvnw spotless:apply
```

## 🛠️ Технологии

- **Язык**: Java 25 (records, sealed classes, pattern matching)
- **Сборка**: Maven (multi-module), Maven Wrapper
- **Тестирование**: JUnit 5, AssertJ, Mockito, JaCoCo
- **Качество кода**: Spotless (Google Java Format), -Xlint:all -Werror
- **CI/CD**: GitHub Actions с кэшированием и строгим режимом
- **Архитектура**: SOLID, Dependency Inversion, Immutable Values

## 📚 Документация

Полная документация находится в папке ``docs/``:

| Документ                                  | Описание                                       |
|-------------------------------------------|------------------------------------------------|
| [Архитектура](docs/ARCHITECTURE.md)       | Модули, диаграммы, нефункциональные требования |
| [План разработки](docs/ROADMAP.md)        | Фазы и критерии готовности                     |
| [Использование](docs/USAGE.md)            | Примеры применения библиотеки                  |
| [Публичный API](docs/API.md)              | Контракт библиотеки                            |
| [Глоссарий](docs/GLOSSARY.md)             | Термины карточных игр и библиотеки             |
| [Решения (ADR)](docs/decisions/README.md) | Обоснование архитектурных решений              |

## 🎯 Цели проекта
- Изучение и демонстрация чистой архитектуры на Java
- Практика строгого CI/CD пайплайна уровня production
- Создание переиспользуемой библиотеки для карточных игр

---

<p style="text-align: center;">
  Сделано с ❤️ как проект для изучения Java-архитектуры
</p>