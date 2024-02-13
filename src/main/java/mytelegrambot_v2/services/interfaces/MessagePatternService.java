package mytelegrambot_v2.services.interfaces;

import jakarta.ws.rs.NotFoundException;
import mytelegrambot_v2.dto.MessagePatternDto;
import mytelegrambot_v2.entity.MessagePattern;
import mytelegrambot_v2.mappers.MessagePatternMapper;

import java.util.List;
import java.util.stream.Collectors;

public interface MessagePatternService {

    MessagePatternDto addMessagePattern(MessagePatternDto m);
    MessagePatternDto updateMessagePattern(MessagePatternDto messagePatternDto, Long iDMessagePatt);

    void deleteMessagePattern(Long id);
    MessagePatternDto getMessagePatternById(Long id);

    List<MessagePatternDto> getAllMessagePattern();

}
