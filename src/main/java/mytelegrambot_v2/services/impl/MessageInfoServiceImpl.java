package mytelegrambot_v2.services.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import mytelegrambot_v2.dto.MessageInfoDto;
import mytelegrambot_v2.dto.MessagePatternDto;
import mytelegrambot_v2.dto.PersonDto;
import mytelegrambot_v2.entity.MessageInfo;
import mytelegrambot_v2.entity.MessagePattern;
import mytelegrambot_v2.entity.Person;
import mytelegrambot_v2.mappers.MessageInfoMapper;
import mytelegrambot_v2.repositories.MessageInfoRepository;
import mytelegrambot_v2.services.interfaces.MessageInfoService;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
/**
 * Service class for managing message information entities.
 */
@Service
@RequiredArgsConstructor
public class MessageInfoServiceImpl implements MessageInfoService {

    private final MessageInfoRepository messageInfoRepository;
    private final PersonServiceImpl personServiceImpl;
    private final MessagePatternServiceImpl messagePatternServiceImpl;
    /**
     * Retrieves a message info DTO by its ID.
     *
     * @param idMessInfo The ID of the message info to retrieve.
     * @return The message info DTO if found, otherwise null.
     */

    public MessageInfoDto getMessInfoById(Long idMessInfo) {
        MessageInfo messageInfo = messageInfoRepository.findById(idMessInfo)
                .orElseThrow(() -> new EntityNotFoundException("MessageInfo with ID " + idMessInfo + " not found"));
        return MessageInfoMapper.toDto(messageInfo);
    }
    /**
     * Retrieves a list of all message info DTOs.
     *
     * @return A list of message info DTOs.
     */
    public List<MessageInfoDto> getAllMessInfo(){
        return messageInfoRepository.findAll().stream().map(MessageInfoMapper::toDto).collect(Collectors.toList());
    }
    /**
     * Retrieves a list of message info DTOs by date.
     *
     * @param date The date to filter the message info entities.
     * @return A list of message info DTOs filtered by the specified date.
     */
    public List<MessageInfoDto> getMessageInfosByDate(LocalDate date) {

        return messageInfoRepository.getMessageInfoByDateAndStatusFalse(date)
                .stream()
                .map(MessageInfoMapper::toDto)
                .collect(Collectors.toList());
    }
    /**
     * Adds a new message info entity.
     *
     * @param messageInfoDto The message info DTO to add.
     * @return The added message info DTO.
     */
    public MessageInfoDto addMessageInfo(MessageInfoDto messageInfoDto){
        if (messageInfoDto.getPerson() != null && messageInfoDto.getPattern() != null ) {
            messageInfoDto.setPerson(personServiceImpl.getPersonById(messageInfoDto.getPerson().getChatId()));
            messageInfoDto.setPattern(messagePatternServiceImpl.getMessagePatternById(messageInfoDto.getPattern().getId()));
            return MessageInfoMapper.toDto(messageInfoRepository.save(MessageInfoMapper.fromDto(messageInfoDto)));
        }
        else throw new IllegalArgumentException("PersonDto and MessagePatternDto cannot be null");
    }
    /**
     * Updates the status of a message info entity.
     *
     * @param newStatus   The new status value.
     * @param idMessInfo  The ID of the message info entity to update.
     */
    public void updateMessInfoStatus(boolean newStatus, Long idMessInfo) {
        MessageInfoDto messageInfoDto = getMessInfoById(idMessInfo);
        messageInfoDto.setStatus(newStatus);
        updateMessInfo(messageInfoDto, idMessInfo);
    }
    /**
     * Updates a message info entity.
     *
     * @param messageInfoDto The updated message info DTO.
     * @param idMessInfo     The ID of the message info entity to update.
     * @return The updated message info DTO.
     */
    @Transactional
    public MessageInfoDto updateMessInfo(MessageInfoDto messageInfoDto, Long idMessInfo) {
        MessageInfo messageInfo = messageInfoRepository.findById(idMessInfo).orElse(null);
        if (messageInfo != null) {
            if (messageInfoDto.getPerson() != null) {
                PersonDto personDto = messageInfoDto.getPerson();
                Person person = new Person(personDto.getChatId(), personDto.getFirstName(), personDto.getLastName(),
                        personDto.getEmail(), personDto.getTelegramName(), personDto.getRegisteredAt());
                messageInfo.setPerson(person);
            }
            if (messageInfoDto.getPattern() != null) {
                MessagePatternDto patternDto = messageInfoDto.getPattern();
                MessagePattern pattern = new MessagePattern(patternDto.getId(), patternDto.getTemplate(), patternDto.isAdv());
                messageInfo.setPattern(pattern);
            }
            messageInfo.setStatus(messageInfoDto.isStatus());
            messageInfo.setDate(messageInfoDto.getDate());
            messageInfoRepository.save(messageInfo);
            return MessageInfoMapper.toDto(messageInfo);
        } else {
            throw new EntityNotFoundException("MessageInfo with ID " + idMessInfo + " not found");
        }
    }
    /**
     * Deletes a message info entity by its ID.
     *
     * @param idMessInfo The ID of the message info entity to delete.
     */
    public void deleteMessInfo(Long idMessInfo){
        messageInfoRepository.deleteById(idMessInfo);
    }

}
