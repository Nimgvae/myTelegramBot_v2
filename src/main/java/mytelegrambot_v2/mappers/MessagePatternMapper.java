package mytelegrambot_v2.mappers;

import mytelegrambot_v2.dto.MessagePatternDto;
import mytelegrambot_v2.entity.MessagePattern;
/**
 * Mapper class for converting between MessagePattern entities and MessagePatternDto DTOs.
 */
public class MessagePatternMapper {
    /**
     * Converts a MessagePattern entity to a MessagePatternDto DTO.
     * @param messagePattern The MessagePattern entity to convert.
     * @return The corresponding MessagePatternDto DTO.
     */
    public static MessagePatternDto toDto(MessagePattern messagePattern) {

        MessagePatternDto messagePatternDto = new MessagePatternDto();
        messagePatternDto.setTemplate(messagePattern.getTemplate());
        messagePatternDto.setId(messagePattern.getId());
        messagePatternDto.setAdv(messagePattern.isAdv());
        return messagePatternDto;
    }
    /**
     * Converts a MessagePatternDto DTO to a MessagePattern entity.
     * @param messagePatternDto The MessagePatternDto DTO to convert.
     * @return The corresponding MessagePattern entity.
     */

    public static MessagePattern fromDto(MessagePatternDto messagePatternDto) {

        MessagePattern messagePattern = new MessagePattern();
        messagePattern.setTemplate(messagePatternDto.getTemplate());
        messagePattern.setId(messagePatternDto.getId());
        messagePattern.setAdv(messagePatternDto.isAdv());
        return messagePattern;
    }
}
