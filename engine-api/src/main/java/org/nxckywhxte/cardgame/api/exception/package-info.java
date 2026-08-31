/**
 * Исключения библиотеки карточных игр.
 *
 * <p>Этот пакет содержит все исключения, которые может бросать библиотека. Все исключения
 * наследуются от {@link org.nxckywhxte.cardgame.api.exception.CardGameException} и являются
 * <b>unchecked</b> (наследуют {@link RuntimeException}).
 *
 * <h2>Иерархия исключений</h2>
 *
 * <pre>
 * CardGameException (базовое)
 * ├── EmptyDeckException - попытка взять карту из пустой колоды
 * ├── CardNotFoundException - попытка удалить отсутствующую карту
 * └── IllegalMoveException - попытка применить невалидный ход
 * </pre>
 *
 * <h2>Обработка исключений</h2>
 *
 * <p>Все исключения библиотеки можно ловить одним блоком {@code catch}:
 *
 * <pre>{@code
 * try {
 *     // операции с библиотекой
 * } catch (CardGameException e) {
 *     // обработка любой ошибки библиотеки
 * }
 * }</pre>
 *
 * @author nxckywhxte
 * @since 0.0.1
 */
package org.nxckywhxte.cardgame.api.exception;
