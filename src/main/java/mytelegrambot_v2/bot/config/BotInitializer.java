package mytelegrambot_v2.bot.config;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mytelegrambot_v2.bot.TelegramBot;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
/**
 * Initializes the bot.
 */
@Slf4j
@Component
@AllArgsConstructor
public class BotInitializer {

    private final TelegramBot telegramBot;
    /**
     * Initializes the bot when the application context is refreshed.
     *
     * @throws TelegramApiException If an error occurs while registering the bot.
     */

    @EventListener({ContextRefreshedEvent.class})
    public void init() throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        try {
            telegramBotsApi.registerBot(telegramBot);
        }
        catch (TelegramApiException e){
            log.error("Error occurred: " + e.getMessage());
        }
    }
}
