package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class SimpleTelegramBot extends TelegramLongPollingBot {

    // Замените на ваш токен от BotFather
    private static final String BOT_TOKEN = "8484982757:AAEWrWt0y7E9pk8m7O8yhy5v2eSuEfhOr0Y";

    // Замените на username вашего бота
    private static final String BOT_USERNAME = "MyTestJavaUltimateBot";

    @Override
    public String getBotUsername() {
        return BOT_USERNAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {
        // Проверяем, есть ли сообщение и текст
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();

            // Обрабатываем команды
            switch (messageText) {
                case "/start":
                    sendMessage(chatId, "Привет! Я простой бот. Отправь мне /help чтобы увидеть команды.");
                    break;
                case "/help":
                    sendMessage(chatId, "Доступные команды:\n/start - начать работу\n/help - помощь\n/hello - поздороваться");
                    break;
                case "/hello":
                    sendMessage(chatId, "Привет! Как дела?");
                    break;
                default:
                    sendMessage(chatId, "Я не понимаю эту команду. Используй /help для списка команд.");
                    break;
            }
        }
    }

    // Метод для отправки сообщений
    private void sendMessage(Long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId.toString());
        message.setText(text);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    // Главный метод для запуска бота
    public static void main(String[] args) {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new SimpleTelegramBot());
            System.out.println("Бот запущен и готов к работе!");
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
