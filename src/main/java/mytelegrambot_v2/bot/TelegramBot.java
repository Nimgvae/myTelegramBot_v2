package mytelegrambot_v2.bot;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mytelegrambot_v2.entity.entityEnum.CommandList;
import mytelegrambot_v2.bot.config.TelegramBotConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import java.util.ArrayList;
import java.util.List;


/**
 * Telegram bot implementation class that handles incoming updates and executes commands.
 */
@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot {
    private static final Logger logger = LoggerFactory.getLogger(TelegramBot.class);

    final TelegramBotConfig telegramBotConfig;



    @Lazy
    @Autowired
    private  CommandListHandler commandListHandler;
    /**
     * Constructor to initialize the Telegram bot with its configuration.
     * @param config The configuration object for the Telegram bot.
     */

    public TelegramBot(TelegramBotConfig config) {
        this.telegramBotConfig = config;
        List<BotCommand> listOfCommands = new ArrayList<>();
        for (CommandList command : CommandList.values()) {
            listOfCommands.add(new BotCommand(command.getCommand(), command.getDescription()));
        }
        try {
            this.execute(new SetMyCommands(listOfCommands, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            logger.error("Error setting bot's command list: {}", e.getMessage());
        }
    }

    /**
     * Retrieves the username of the Telegram bot.
     * @return The bot's username.
     */

    @Override
    public String getBotUsername() {
        return telegramBotConfig.getBotName();
    }
    /**
     * Retrieves the token of the Telegram bot.
     * @return The bot's token.
     */
    @Override
    public String getBotToken() {
        return telegramBotConfig.getBotToken();
    }
    /**
     * Handles incoming updates from the Telegram API.
     * @param update The incoming update object.
     */
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            logger.info("Received message: " + update.getMessage().getText());
        }
        commandListHandler.handleCommand(update);
    }
}




