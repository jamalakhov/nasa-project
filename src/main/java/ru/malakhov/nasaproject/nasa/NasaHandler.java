package ru.malakhov.nasaproject.nasa;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.InputStream;

@Slf4j
@Component
public class NasaHandler {
    private final NasaHttpClient httpClient;
    private final NasaResponseMapper mapper;

    public NasaHandler(NasaHttpClient httpClient, NasaResponseMapper mapper) {
        this.httpClient = httpClient;
        this.mapper = mapper;
    }

    public void init() {
        mapper.init();
        httpClient.init();
    }

    public SendMessage handling(Update update) {
        NasaResponseObject responseObject;

        long userId = update.getMessage().getFrom().getId();
        var message = update.getMessage().getText();

        if (message.equalsIgnoreCase("/url")) {
            try (InputStream stream = httpClient.getResponse().getEntity().getContent()) {
                responseObject = mapper.mapping(stream);
                return SendMessage.builder()
                        .chatId(userId)
                        .text(responseObject.getUrl())
                        .build();

            } catch (Exception e) {
                log.error(e.toString());
            }
        } else if (message.equalsIgnoreCase("/hdurl")) {
            try (InputStream stream = httpClient.getResponse().getEntity().getContent()) {
                responseObject = mapper.mapping(stream);
                return SendMessage.builder()
                        .chatId(userId)
                        .text(responseObject.getHdurl())
                        .build();

            } catch (Exception e) {
                log.error(e.toString());
            }
        }

        return SendMessage.builder()
                .chatId(userId)
                .text("""
                        Доступные команды:
                                                
                        /url - получить ссылку на картинку обычного качества
                        /hdurl - получить ссылку на картинку высокого качества
                        """)
                .build();
    }
}