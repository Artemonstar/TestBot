package org.example;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class AdvancedTelegramBot extends TelegramLongPollingBot {

    private static final String BOT_TOKEN = "8484982757:AAEWrWt0y7E9pk8m7O8yhy5v2eSuEfhOr0Y";
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
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();
            String userName = update.getMessage().getFrom().getFirstName();

            if (messageText.startsWith("/")) {
                handleCommand(chatId, messageText, userName);
            } else {
                handleTextMessage(chatId, messageText, userName);
            }
        }
    }

    private void handleCommand(Long chatId, String command, String userName) {
        switch (command) {
            case "/start":
                sendMessage(chatId, "Привет, " + userName + "! Я твой телеграм бот.");
                break;
            case "/help":
                sendMessage(chatId, "Доступные команды: /start, /help");
                break;
            default:
                sendMessage(chatId, "Неизвестная команда. Используй /help");
                break;
        }
    }

    private void handleTextMessage(Long chatId, String text, String userName) {
        String lowerText = text.toLowerCase();

        if (lowerText.contains("upuser")) {
            sendMessage(chatId, "Upuser");
        } else if (lowerText.contains("text")) {
            sendMessage(chatId, "token");
        } else if (lowerText.contains("chatid")) {
            sendMessage(chatId, "chatId: " + chatId);
        } else {
            sendMessage(chatId, "Bucket");
        }
    }

    private void sendMessage(Long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId.toString());
        message.setText(text);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            System.err.println("Ошибка при отправке сообщения: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new AdvancedTelegramBot());
            System.out.println("Бот успешно запущен!");
        } catch (TelegramApiException e) {
            System.err.println("Ошибка при запуске бота: " + e.getMessage());
        }
    }
}
