package mytelegrambot_v2.services.impl;

import jakarta.ws.rs.NotFoundException;
import lombok.AllArgsConstructor;
import mytelegrambot_v2.dto.MessagePatternDto;
import mytelegrambot_v2.entity.MessagePattern;
import mytelegrambot_v2.mappers.MessagePatternMapper;
import mytelegrambot_v2.repositories.MessagePatternRepository;
import mytelegrambot_v2.services.interfaces.MessagePatternService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
/**
 * Service class for managing message patterns.
 */
@Service
@AllArgsConstructor
public class MessagePatternServiceImpl implements MessagePatternService {
    private final MessagePatternRepository messagePatternRepository;
    /**
     * Adds a new message pattern.
     *
     * @param m The DTO containing the message pattern information.
     * @return The DTO of the added message pattern.
     */
    public MessagePatternDto addMessagePattern(MessagePatternDto m) {
        return MessagePatternMapper.toDto(messagePatternRepository.save(MessagePatternMapper.fromDto(m)));
    }
    /**
     * Updates an existing message pattern.
     *
     * @param messagePatternDto The DTO containing the updated message pattern information.
     * @param iDMessagePatt  The ID of the message pattern to update.
     * @return The DTO of the updated message pattern.
     * @throws NotFoundException If the message pattern with the specified ID is not found.
     */
    public MessagePatternDto updateMessagePattern(MessagePatternDto messagePatternDto, Long iDMessagePatt) {
    MessagePattern messagePattern = messagePatternRepository.findById(iDMessagePatt).orElse(null);
    if (messagePattern == null) {
        throw new NotFoundException("Message Pattern not found for ID: " + iDMessagePatt);
    }

    MessagePatternDto messagePatDto = MessagePatternMapper.toDto(messagePattern);
    messagePatDto.setTemplate(messagePatternDto.getTemplate());
    return addMessagePattern(messagePatDto);
}   /**
     * Deletes a message pattern by ID.
     *
     * @param id The ID of the message pattern to delete.
     */

    public void deleteMessagePattern(Long id) {
        messagePatternRepository.deleteById(id);
    }
    /**
     * Retrieves a message pattern by its ID.
     *
     * @param id The ID of the message pattern to retrieve.
     * @return The DTO of the retrieved message pattern.
     */
//    public MessagePatternDto getMessagePatternById(Long id) {
//        return MessagePatternMapper.toDto(Objects.requireNonNull(messagePatternRepository.findById(id).orElse(null)));
//    }
    public MessagePatternDto getMessagePatternById(Long id) {
        MessagePattern messagePattern = messagePatternRepository.findById(id).orElse(null);
        if (messagePattern != null) {
            return MessagePatternMapper.toDto(messagePattern);
        } else {
            throw new IllegalArgumentException("MessagePattern with id " + id + " not found");
        }
    }
    /**
     * Retrieves all message patterns.
     *
     * @return A list of DTOs representing all message patterns.
     */

    public List<MessagePatternDto> getAllMessagePattern() {
        return messagePatternRepository.findAll()
                .stream().map(MessagePatternMapper::toDto).collect(Collectors.toList());
    }
}
