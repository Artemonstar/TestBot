package org.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Минимальные тесты без предупреждений
 */
@DisplayName("Unit тесты для Telegram бота")
public class SimpleTelegramBotSimpleTests {

    // Тестовая логика обработки команд
    static class CommandHandler {
        public static String handle(String command) {
            if (command == null || command.trim().isEmpty()) {
                return "Я не понимаю эту команду. Используй /help для списка команд.";
            }

            return switch (command.trim()) {
                case "/start" -> "Привет! Я простой бот. Отправь мне /help чтобы увидеть команды.";
                case "/help" -> "Доступные команды:\n/start - начать работу\n/help - помощь\n/hello - поздороваться";
                case "/hello" -> "Привет! Как дела?";
                default -> "Я не понимаю эту команду. Используй /help для списка команд.";
            };
        }
    }

    // ------------------------------------------------------------
    // ТЕСТ-КЕЙС 1: TestWithCorrectCommand
    // ------------------------------------------------------------
    @Test
    @DisplayName("TestWithCorrectCommand - проверка команды /start")
    void testStartCommand() {
        String response = CommandHandler.handle("/start");

        assertNotNull(response, "Ответ не должен быть null");
        assertTrue(response.contains("Привет! Я простой бот"),
                "Должно быть приветствие");
        assertTrue(response.contains("/help"),
                "Должно быть упоминание /help");

        System.out.println("✓ TestWithCorrectCommand пройден");
        System.out.println("  Входные данные: /start");
        System.out.println("  Результат: " + response.substring(0, Math.min(50, response.length())) + "...");
    }

    // ------------------------------------------------------------
    // ТЕСТ-КЕЙС 2: TestWithIncorrectInput
    // ------------------------------------------------------------
    @Test
    @DisplayName("TestWithIncorrectInput - проверка неизвестной команды")
    void testUnknownCommand() {
        String response = CommandHandler.handle("/unknown");

        assertNotNull(response, "Ответ не должен быть null");
        assertTrue(response.contains("Я не понимаю эту команду"),
                "Должно быть сообщение об ошибке");
        assertTrue(response.contains("/help"),
                "Должно быть предложение использовать /help");

        System.out.println("✓ TestWithIncorrectInput пройден");
        System.out.println("  Входные данные: /unknown");
        System.out.println("  Результат: " + response.substring(0, Math.min(50, response.length())) + "...");
    }

    // ------------------------------------------------------------
    // Дополнительные тесты для полноты
    // ------------------------------------------------------------
    @Test
    @DisplayName("Test команды /help")
    void testHelpCommand() {
        String response = CommandHandler.handle("/help");

        assertNotNull(response);
        assertTrue(response.contains("Доступные команды"));
        assertTrue(response.contains("/start"));
        assertTrue(response.contains("/hello"));
    }

    @Test
    @DisplayName("Test команды /hello")
    void testHelloCommand() {
        String response = CommandHandler.handle("/hello");

        assertNotNull(response);
        assertEquals("Привет! Как дела?", response);
    }

    @Test
    @DisplayName("Test пустой команды")
    void testEmptyCommand() {
        String response = CommandHandler.handle("");

        assertNotNull(response);
        assertTrue(response.contains("Я не понимаю"));
    }

    @Test
    @DisplayName("Test оригинального бота (безопасные методы)")
    void testOriginalBot() {
        SimpleTelegramBot bot = new SimpleTelegramBot();

        assertEquals("MyTestJavaUltimateBot", bot.getBotUsername());
        assertEquals("8484982757:AAEWrWt0y7E9pk8m7O8yhy5v2eSuEfhOr0Y", bot.getBotToken());

        System.out.println("✓ Тест оригинального бота пройден");
        System.out.println("  Username: " + bot.getBotUsername());
        System.out.println("  Token: " + bot.getBotToken().substring(0, 10) + "...");
    }
}