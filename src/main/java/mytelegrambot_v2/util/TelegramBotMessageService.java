package mytelegrambot_v2.util;
import com.vdurmont.emoji.EmojiParser;
import lombok.RequiredArgsConstructor;
import mytelegrambot_v2.entity.entityEnum.InfoState;
import mytelegrambot_v2.bot.TelegramBot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
/**
 * Service class for sending messages via the Telegram bot.
 */
@Service
@RequiredArgsConstructor
public class TelegramBotMessageService {

    private final TelegramBot telegramBot;
    private static final Logger logger = LoggerFactory.getLogger(TelegramBotMessageService.class);
    /**
     * Executes the given SendMessage request.
     * @param message The SendMessage request to execute.
     */

    public void executeMessage(SendMessage message){
        try {
            telegramBot.execute(message);
            System.out.println(message);
        } catch (TelegramApiException e) {
            logger.error(InfoState.ERROR_TEXT + e.getMessage());
        }
    }
    /**
     * Prepares and sends a message to the specified chat ID.
     * @param chatId The chat ID to send the message to.
     * @param textToSend The text to send in the message.
     */

    public void prepareAndSendMessage(long chatId, String textToSend){
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);
        executeMessage(message);
    }
    /**
     * Sends a message to the specified chat ID.
     * @param chatId The chat ID to send the message to.
     * @param textToSend The text to send in the message.
     */
    public void sentMessage(long chatId, String textToSend) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);
        executeMessage(message);
    }
    /**
     * Generates a welcome message for the user with the given name.
     * @param name The name of the user.
     * @return The welcome message.
     */
    private String generateWelcomeMessage(String name) {
        return EmojiParser.parseToUnicode("Hi, " + name + ", nice to meet you!" + " :blush:");
    }
    /**
     * Handles the start command received from the user.
     * @param chatId The chat ID of the user.
     * @param name The name of the user.
     */

    public void startCommandReceived(long chatId, String name) {
        String answer = generateWelcomeMessage(name);
        sentMessage(chatId, answer);
    }
}
