package mytelegrambot_v2.util;
import lombok.RequiredArgsConstructor;
import mytelegrambot_v2.dto.MessageInfoDto;
import mytelegrambot_v2.entity.MessagePattern;
import mytelegrambot_v2.entity.Person;
import mytelegrambot_v2.repositories.MessagePatternRepository;
import mytelegrambot_v2.repositories.PersonRepository;
import mytelegrambot_v2.services.impl.MessageInfoServiceImpl;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * Service class for handling scheduled tasks related to sending advertisements to users.
 */
@Service
@RequiredArgsConstructor
public class MessageHandlerService {


    private final MessagePatternRepository messagePatternRepository;

    private final TelegramBotMessageService telegramBotMessageService;

    private final PersonRepository personRepository;
    private final MessageInfoServiceImpl messageInfoServiceImpl;
    private final MessageBuilder messageBuilder;

    /**
     * Sends advertisements to all registered users at a scheduled interval.
     */

    @Scheduled(cron = "${cron.telegramscheduler}")
    private void sendAds() {
        var mess = messagePatternRepository.getMessagePatternByAdvTrue();
        var persons = personRepository.findAll();

        for (MessagePattern messagePattern : mess) {
            for (Person person : persons) {
                telegramBotMessageService.prepareAndSendMessage(person.getChatId(), messagePattern.getTemplate());
            }
        }
    }

    /**
     * Scheduled method to send messages via email.
     * Runs at the specified cron schedule.
     */

    @Scheduled(cron = "${cron.emailscheduler}")
    public void sendMessage() {
        LocalDate date = LocalDate.now();
        List<MessageInfoDto> messages = messageInfoServiceImpl.getMessageInfosByDate(date);
        List<MessageBuilder.Message> preparedMessages = messageBuilder.prepareMessages(messages);
        messageBuilder.sendMessages(preparedMessages);
    }
}
