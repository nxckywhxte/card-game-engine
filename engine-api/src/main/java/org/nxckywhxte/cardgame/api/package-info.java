/**
 * Публичный контракт библиотеки карточных игр.
 *
 * <p>Этот пакет содержит все интерфейсы, которые определяют публичный контракт библиотеки:
 *
 * <h2>Основные сущности</h2>
 *
 * <ul>
 *   <li>{@link org.nxckywhxte.cardgame.api.Deck} - колода карт
 *   <li>{@link org.nxckywhxte.cardgame.api.Zone} - зона для карт (рука, стол, сброс)
 *   <li>{@link org.nxckywhxte.cardgame.api.Player} - игрок с рукой карт
 *   <li>{@link org.nxckywhxte.cardgame.api.Move} - ход игрока
 * </ul>
 *
 * <h2>Состояние и правила игры</h2>
 *
 * <ul>
 *   <li>{@link org.nxckywhxte.cardgame.api.ReadableGameState} - состояние игры (только чтение)
 *   <li>{@link org.nxckywhxte.cardgame.api.GameState} - состояние игры с операциями создания новых
 *       состояний
 *   <li>{@link org.nxckywhxte.cardgame.api.GameRules} - правила конкретной игры
 * </ul>
 *
 * <h2>Вспомогательные типы</h2>
 *
 * <ul>
 *   <li>{@link org.nxckywhxte.cardgame.api.DrawResult} - результат взятия карт из колоды
 * </ul>
 *
 * <h2>Принципы проектирования</h2>
 *
 * <ul>
 *   <li><b>Иммутабельность:</b> все сущности неизменяемы, операции изменения возвращают новые
 *       объекты
 *   <li><b>Разделение интерфейсов:</b> чтение и изменение состояния разделены
 *   <li><b>Идентификаторы вместо объектов:</b> {@code Move} использует идентификаторы зон и игроков
 *   <li><b>Потокобезопасность:</b> все сущности безопасны для многопоточного использования
 * </ul>
 *
 * @author nxckywhxte
 * @since 0.0.1
 */
package org.nxckywhxte.cardgame.api;
