package mytelegrambot_v2.util;

import mytelegrambot_v2.dto.MessageInfoDto;
import mytelegrambot_v2.dto.MessagePatternDto;
import mytelegrambot_v2.dto.PersonDto;
import mytelegrambot_v2.services.impl.StatisticServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class MessageBuilderTest {


    @Mock
    private StatisticServiceImpl statisticService;

    @Mock
    private ServiceEmail serviceEmail;

    @InjectMocks
    private MessageBuilder messageBuilder;

    public MessageBuilderTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testPrepareMessages() {
        List<MessageInfoDto> messageInfoList = new ArrayList<>();
        MessageInfoDto messageInfoDto1 = new MessageInfoDto();
        messageInfoDto1.setStatus(true);
        MessageInfoDto messageInfoDto2 = new MessageInfoDto();
        messageInfoDto2.setStatus(false);
        messageInfoList.add(messageInfoDto1);
        messageInfoList.add(messageInfoDto2);
        List<MessageBuilder.Message> result = messageBuilder.prepareMessages(messageInfoList);
        assertEquals(1, result.size());
        assertEquals(messageInfoDto2, result.get(0).getMessageInfoDto());
    }

    @Test
    void testSendMessages() {

        List<MessageBuilder.Message> messages = new ArrayList<>();
        MessageBuilder.Message message = new MessageBuilder.Message();
        messages.add(message);
        List<MessageBuilder.Message> expectedReturnedMessages = new ArrayList<>();
        doReturn(expectedReturnedMessages).when(serviceEmail).sendEmail(messages);
        messageBuilder.sendMessages(messages);
        verify(serviceEmail, times(1)).sendEmail(messages);
        verify(statisticService, times(1)).addAllStatistic(anyList());
    }

    @Test
    void testReturnedStatistics() {
        List<MessageBuilder.Message> messages = new ArrayList<>();
        MessageBuilder.Message message = new MessageBuilder.Message();
        MessageInfoDto messageInfoDto = new MessageInfoDto();
        PersonDto personDto = new PersonDto();
        personDto.setChatId(123456L);
        MessagePatternDto messagePattern = new MessagePatternDto();
        messagePattern.setId(123L);
        messagePattern.setTemplate("Some text");
        messageInfoDto.setPattern(messagePattern);
        messageInfoDto.setPerson(personDto);
        message.setMessageInfoDto(messageInfoDto);
        messages.add(message);

        messageBuilder.returnedStatistics(messages);

        verify(statisticService, times(1)).addAllStatistic(anyList());
        verify(statisticService).addAllStatistic(argThat(argument ->
                argument.stream().allMatch(statisticDto ->
                        statisticDto.getPersonId() != null &&
                                statisticDto.getPersonId().equals(personDto.getChatId()) &&
                                (messageInfoDto.getPattern() != null && statisticDto.getMessage().equals(messageInfoDto.getPattern().getTemplate())) &&
                                statisticDto.isEmail() == message.isEmail() &&
                                statisticDto.getDate() != null)));
    }
}