package mytelegrambot_v2.mappers;
import mytelegrambot_v2.dto.MessageInfoDto;
import mytelegrambot_v2.entity.MessageInfo;

/**
 * Mapper class for converting between MessageInfo entities and MessageInfoDto DTOs.
 */
public class MessageInfoMapper {
    /**
     * Converts a MessageInfo entity to a MessageInfoDto DTO.
     * @param messageInfo The MessageInfo entity to convert.
     * @return The corresponding MessageInfoDto DTO.
     */
    public static MessageInfoDto toDto(MessageInfo messageInfo) {
    MessageInfoDto messageInfoDto = new MessageInfoDto();
    messageInfoDto.setDate(messageInfo.getDate());
    messageInfoDto.setId(messageInfo.getId());
    messageInfoDto.setPerson(PersonMapper.toDto(messageInfo.getPerson()));
    messageInfoDto.setPattern(MessagePatternMapper.toDto(messageInfo.getPattern()));
    messageInfoDto.setStatus(messageInfo.isStatus());
    return messageInfoDto;
}
    /**
     * Converts a MessageInfoDto DTO to a MessageInfo entity.
     * @param messageInfoDto The MessageInfoDto DTO to convert.
     * @return The corresponding MessageInfo entity.
     */

    public static MessageInfo fromDto(MessageInfoDto messageInfoDto){
        MessageInfo messageInfo = new MessageInfo();
        messageInfo.setDate(messageInfoDto.getDate());
        messageInfo.setId(messageInfoDto.getId());
        messageInfo.setPattern(MessagePatternMapper.fromDto(messageInfoDto.getPattern()));
        messageInfo.setPerson(PersonMapper.fromDto(messageInfoDto.getPerson()));
        messageInfo.setStatus(messageInfoDto.isStatus());
        return messageInfo;
    }
}
