package mytelegrambot_v2.util;

import lombok.AllArgsConstructor;
import mytelegrambot_v2.services.impl.MessageInfoServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Service class responsible for sending emails in a Telegram bot application.
 */
@Service
@AllArgsConstructor
public class ServiceEmail {
    private final JavaMailSender emailSender;
    private final MessageInfoServiceImpl messageInfoServiceImpl;
    private static final Logger logger = LoggerFactory.getLogger(ServiceEmail.class);
    /**
     * Sends emails using the provided list of messages.
     * @param messages The list of messages to be sent via email.
     * @return The list of messages after attempting to send emails.
     */
    public List<MessageBuilder.Message> sendEmail(List<MessageBuilder.Message> messages) {
        for (MessageBuilder.Message message : messages) {
            try {
                SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
                simpleMailMessage.setTo(message.getMessageInfoDto().getPerson().getEmail());
                simpleMailMessage.setSubject("Message from TelegramBot");
                simpleMailMessage.setText(message.getMessageInfoDto().getPattern().getTemplate());
                emailSender.send(simpleMailMessage);
                message.setEmail(true);
                if (message.isEmail()){
                    updateMessageInfoAfterEmailing(message.getMessageInfoDto().getId());
                }
                else{
                    message.setEmail(false);

                }

            } catch (MailException e) {
                logger.error("Error sending email. PersonId: " + message.getMessageInfoDto().getPerson().getChatId(), e);
                throw e;
            }
        }
        return messages;
    }
    /**
     * Updates the message information after sending an email.
     * @param messageId The ID of the message.
     */
    public void updateMessageInfoAfterEmailing(Long messageId){
        messageInfoServiceImpl.updateMessInfoStatus(true, messageId);;

    }
}
