package ru.malakhov.nasaproject.bot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.malakhov.nasaproject.nasa.NasaHandler;

import javax.annotation.PostConstruct;

@Slf4j
@Component
public class NasaBot extends TelegramLongPollingBot {
    @Value("${bot.name}")
    private String botName;

    private final NasaHandler nasaHandler;

    public NasaBot(@Value("${bot.token}") String botToken,
                   NasaHandler nasaHandler) {
        super(botToken);
        this.nasaHandler = nasaHandler;
    }

    @PostConstruct
    public void init() {
        nasaHandler.init();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update != null && update.hasMessage()) {

            try {
                execute(nasaHandler.handling(update));
            } catch (TelegramApiException e) {
                log.error(e.toString());
            }
        } else {
            log.error("Error");
        }

    }

    @Override
    public String getBotUsername() {
        return botName;
    }
}