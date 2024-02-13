package mytelegrambot_v2.bot;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mytelegrambot_v2.repositories.PersonRepository;
import mytelegrambot_v2.util.GptProperties;
import mytelegrambot_v2.util.TelegramBotMessageService;
import mytelegrambot_v2.services.impl.TelegramBotUserServiceImpl;
import mytelegrambot_v2.entity.entityEnum.CommandList;
import mytelegrambot_v2.entity.entityEnum.InfoState;
import mytelegrambot_v2.entity.entityEnum.UserState;
import mytelegrambot_v2.repositories.PersonStateRepository;
import mytelegrambot_v2.services.impl.PersonServiceImpl;
import mytelegrambot_v2.services.impl.PersonStateServiceImpl;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Handler class responsible for processing commands received by the Telegram bot.
 */

@Slf4j
@Component
@RequiredArgsConstructor
public class CommandListHandler {

    private final TelegramBotMessageService telegramBotMessageService;
    private final TelegramBotUserServiceImpl telegramBotUserServiceImpl;
    private final PersonServiceImpl personServiceImpl;
    private final PersonRepository personRepository;
    private final PersonStateRepository personStateRepository;
    private final PersonStateServiceImpl personStateServiceImpl;




    /**
     * Handles incoming commands and performs appropriate actions based on the command type.
     *
     * @param update The incoming update containing the command.
     */

    public void handleCommand(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            CommandList command = CommandList.getByCommand(messageText);
            if (command != null) {
                switch (command) {
                    case START:
                        telegramBotMessageService.startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                        break;
                    case MOTIVATION:
                        String motivation = telegramBotUserServiceImpl.requestMotivationFromGPT();
                        telegramBotMessageService.prepareAndSendMessage(chatId, motivation);
                        break;
                    case HELP:
                        telegramBotMessageService.prepareAndSendMessage(chatId, InfoState.HELP_TEXT.getDescription());
                        break;
                    case REGISTER:
                        telegramBotUserServiceImpl.registerUser(update.getMessage());
                        break;
                    case MYDATA:
                        telegramBotUserServiceImpl.getUserInfo(chatId);
                        break;
                    case DELETEDATA:
                        telegramBotUserServiceImpl.removeUser(update.getMessage());
                        break;
                    case ADDEMAIL:
                        if (personRepository.existsById(chatId)) {
                            telegramBotUserServiceImpl.addEmailCommandReceived(chatId);
                        } else {
                            telegramBotMessageService.prepareAndSendMessage(chatId, "You are not registered. Please register first.");
                        }
                        break;
                    default:
                        break;
                }
            } else {
                if (personStateRepository.findById(chatId).get().getUserState() == UserState.WAITING_FOR_EMAIL) {
                    if (messageText.equalsIgnoreCase("Cancel")) {
                        personStateServiceImpl.updatePersonState(chatId, UserState.IDLE);
                        telegramBotMessageService.prepareAndSendMessage(chatId, "Email addition process canceled.");
                    } else {
                        String userEmail = messageText.trim();
                        personServiceImpl.updatePersonEmail(chatId, userEmail);
                        personStateServiceImpl.updatePersonState(chatId, UserState.IDLE);
                        telegramBotMessageService.prepareAndSendMessage(chatId, "Thank you! Your email has been received.");
                    }
                }
            }
        }
    }

}
