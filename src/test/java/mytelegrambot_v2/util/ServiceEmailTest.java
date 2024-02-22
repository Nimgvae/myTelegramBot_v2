package mytelegrambot_v2.util;

import mytelegrambot_v2.dto.MessageInfoDto;
import mytelegrambot_v2.dto.MessagePatternDto;
import mytelegrambot_v2.dto.PersonDto;
import mytelegrambot_v2.services.impl.MessageInfoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ServiceEmailTest {
    @Mock
    private JavaMailSender emailSender;

    @Mock
    private MessageInfoServiceImpl messageInfoServiceImpl;

    @InjectMocks
    private ServiceEmail serviceEmail;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

    }

    @Test
    void testSendEmail_Successful() {
        List<MessageBuilder.Message> messages = new ArrayList<>();
        MessageBuilder.Message message = new MessageBuilder.Message();
        MessageInfoDto messageInfoDto = new MessageInfoDto();
        PersonDto personDto = new PersonDto();
        MessagePatternDto messagePatternDto = new MessagePatternDto();
        personDto.setEmail("mymail@example.com");
        messageInfoDto.setPerson(personDto);
        messageInfoDto.setPattern(messagePatternDto);
        message.setMessageInfoDto(messageInfoDto);
        messages.add(message);
        List<MessageBuilder.Message> result = serviceEmail.sendEmail(messages);
        assertEquals(messages.size(), result.size());
    }
    @Test
    void testSendEmail_Unsuccessful() {
        List<MessageBuilder.Message> messages = new ArrayList<>();
        MessageBuilder.Message message = new MessageBuilder.Message();
        MessageInfoDto messageInfoDto = new MessageInfoDto();
        PersonDto personDto = new PersonDto();
        personDto.setEmail("test@example.com");
        messageInfoDto.setPerson(personDto);

        MessagePatternDto messagePatternDto = new MessagePatternDto();
        messagePatternDto.setTemplate("Your template goes here");

        messageInfoDto.setPattern(messagePatternDto);

        message.setMessageInfoDto(messageInfoDto);
        messages.add(message);
        doThrow(new MailSendException("Test mail sending error")).when(emailSender).send(any(SimpleMailMessage.class));
        assertThrows(MailException.class, () -> serviceEmail.sendEmail(messages));
    }

    @Test
    void updateMessageInfoAfterEmailing_Successful() {

        Long messageId = 1L;
        serviceEmail.updateMessageInfoAfterEmailing(messageId);
        verify(messageInfoServiceImpl, times(1)).updateMessInfoStatus(true, messageId);
    }
}