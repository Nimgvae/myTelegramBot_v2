package mytelegrambot_v2.util;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import mytelegrambot_v2.dto.MessageInfoDto;
import mytelegrambot_v2.dto.StatisticDto;
import mytelegrambot_v2.services.impl.MessageInfoServiceImpl;
import mytelegrambot_v2.services.impl.StatisticServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
/**
 * Class responsible for building and sending messages.
 */
@Component
@AllArgsConstructor
@Getter
@Setter
public class MessageBuilder {
    private static final Logger logger = LoggerFactory.getLogger(MessageBuilder.class);
    private  final MessageInfoServiceImpl messageInfo;
    private final StatisticServiceImpl statisticServiceImpl;
    private final ServiceEmail serviceEmail;
    /**
     * Inner class representing a message.
     */
    @Data
    public static class Message {
        private MessageInfoDto messageInfoDto;
        private boolean email = false;
    }
    /**
     * Prepares messages based on the provided list of MessageInfoDto objects.
     * @param messageInfoList List of MessageInfoDto objects
     * @return List of prepared messages
     */
    public synchronized List<Message> prepareMessages(List<MessageInfoDto> messageInfoList) {
        List<Message> messages = new ArrayList<>();
        for (MessageInfoDto messageInfoDto : messageInfoList) {
            Message message = new Message();
            if(!messageInfoDto.isStatus()){
                message.setMessageInfoDto(messageInfoDto);
                messages.add(message);
            }
        }
        return messages;
    }
    /**
     * Sends the provided messages via email using the ServiceEmail class.
     * Also, updates statistics based on the sented messages.
     * @param messages List of messages to send
     */
    public synchronized void sendMessages(List<Message> messages) {
                returnedStatistics(serviceEmail.sendEmail(messages));
     }
    /**
     * Updates statistics based on the provided list of messages.
     * @param mList List of messages
     */
    public void returnedStatistics(List<Message> mList) {
        List<StatisticDto> sDto = mList.stream()
                .map(message -> {
                    StatisticDto statisticDto = new StatisticDto();
                    statisticDto.setPersonId(message.getMessageInfoDto().getPerson().getChatId());
                    statisticDto.setMessage(message.getMessageInfoDto().getPattern().getTemplate());
                    statisticDto.setEmail(message.isEmail());
                    statisticDto.setDate(new Timestamp(System.currentTimeMillis()));
                    return statisticDto;
                })
                .collect(Collectors.toList());
        statisticServiceImpl.addAllStatistic(sDto);
    }


}

