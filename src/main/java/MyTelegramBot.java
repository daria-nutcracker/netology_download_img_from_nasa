import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.IOException;

public class MyTelegramBot extends TelegramLongPollingBot {

    //Наша ссылка, на которую будем отправлять запрос
    public static final String URI = "https://api.nasa.gov/planetary/apod?api_key=ahIAAEbbk2QJBmUQdPzn9R1axadqFLj9FUi8lMme";
    public static final String BOT_USERNAME = "sendimg_bot";
    public static final String BOT_TOKEN = "";

    public static long chat_id;

    public MyTelegramBot () throws TelegramApiException {
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(this);
    }
    @Override
    public String getBotUsername() {
        return BOT_USERNAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }
//update - слушает. вся основная логика происходит здесь
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            //у каждого пользователя есть свой id, чтобы ответ шел тому чел, кто отправил запрос
            chat_id = update.getMessage().getChatId();
            switch (update.getMessage().getText()) {
                case "/help":
                    sendMessage("Привет, я бот Nasa! Я высылаю ссылки на картинки по запросу. " +
                            "Напоминаю, что картинки на сайте обновляются раз в сутки");
                    break;
                case "/give":
                    try {
                        sendMessage(Utils.getUrl(URI));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                default:
                    sendMessage("Я не понимаю :(");
            }
        }

    }

    private void sendMessage(String messageText) {
        //предоставляет библиотека телеграма
        SendMessage message = new SendMessage();
        message.setChatId(chat_id);
        message.setText(messageText);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
