package mytelegrambot_v2.services;
import jakarta.persistence.EntityNotFoundException;
import mytelegrambot_v2.dto.MessageInfoDto;
import mytelegrambot_v2.dto.MessagePatternDto;
import mytelegrambot_v2.dto.PersonDto;
import mytelegrambot_v2.entity.MessageInfo;
import mytelegrambot_v2.entity.MessagePattern;
import mytelegrambot_v2.entity.Person;
import mytelegrambot_v2.mappers.MessageInfoMapper;
import mytelegrambot_v2.repositories.MessageInfoRepository;
import mytelegrambot_v2.services.impl.MessageInfoServiceImpl;
import mytelegrambot_v2.services.impl.MessagePatternServiceImpl;
import mytelegrambot_v2.services.impl.PersonServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MessageInfoServiceImplTest {
    @Mock
    private MessageInfoRepository messageInfoRepository;
    @Mock
    private MessageInfoMapper messageInfoMapper;

    @Mock
    private PersonServiceImpl personServiceImpl;

    @Mock
    private MessagePatternServiceImpl messagePatternServiceImpl;

    @InjectMocks
    private MessageInfoServiceImpl messageInfoServiceImpl;


    @Test
    void testGetMessInfoById() {
        Long messageId = 1L;

        // Создаем заглушку Person
        Person person = new Person();
        person.setChatId(100L);

        // Создаем заглушку MessagePattern
        MessagePattern messagePattern = new MessagePattern();
        messagePattern.setTemplate("someTemplate");

        // Создаем объект MessageInfo и устанавливаем значения его полей
        MessageInfo expectedInfo = new MessageInfo();
        expectedInfo.setId(messageId);
        expectedInfo.setPerson(person); // Устанавливаем объект Person
        expectedInfo.setPattern(messagePattern); // Устанавливаем объект MessagePattern

        // Конвертируем объект MessageInfo в DTO
        MessageInfoDto expectedDto = MessageInfoMapper.toDto(expectedInfo);

        // Возвращаем Optional<MessageInfo>, содержащий созданный объект MessageInfo
        when(messageInfoRepository.findById(messageId)).thenReturn(Optional.of(expectedInfo));

        // Вызываем тестируемый метод
        MessageInfoDto actualDto = messageInfoServiceImpl.getMessInfoById(messageId);

        // Проверяем, что полученный DTO соответствует ожидаемому
        assertEquals(expectedDto, actualDto);
    }

    @Test
    void testGetAllMessInfo() {
        when(messageInfoRepository.findAll()).thenReturn(Collections.emptyList());

        assertTrue(messageInfoServiceImpl.getAllMessInfo().isEmpty());
    }

    @Test
    void testGetMessageInfosByDate() {
        LocalDate date = LocalDate.now();
        when(messageInfoRepository.getMessageInfoByDateAndStatusFalse(date)).thenReturn(Collections.emptyList());

        assertTrue(messageInfoServiceImpl.getMessageInfosByDate(date).isEmpty());
    }

    @Test
    void testAddMessageInfo() {
        MessageInfoDto messageInfoDto = new MessageInfoDto();
        when(personServiceImpl.getPersonById(any())).thenReturn(new PersonDto());
        when(messagePatternServiceImpl.getMessagePatternById(any())).thenReturn(new MessagePatternDto());
        when(messageInfoRepository.save(any())).thenReturn(new MessageInfoDto());
        assertDoesNotThrow(() -> messageInfoServiceImpl.addMessageInfo(messageInfoDto));
    }

    @Test
    void testUpdateMessInfoStatus() {
        Long messageId = 1L;
        boolean newStatus = true;
        MessageInfoDto messageInfoDto = new MessageInfoDto();
        messageInfoDto.setId(messageId);
        when(messageInfoServiceImpl.getMessInfoById(messageId)).thenReturn(messageInfoDto);
        when(messageInfoRepository.save(any())).thenReturn(new MessageInfoDto());

        assertDoesNotThrow(() -> messageInfoServiceImpl.updateMessInfoStatus(newStatus, messageId));
    }

    @Test
    public void testUpdateMessInfo_Success() {
        // Arrange
        Long idMessInfo = 1L;
        MessageInfoDto inputDto = new MessageInfoDto();
        inputDto.setId(1L);
        inputDto.setPerson(new PersonDto(100L,"John", "Wenning", "email", "telegramname", new Timestamp(System.currentTimeMillis())));
        inputDto.setDate(LocalDate.parse("2024-02-12"));
        inputDto.setPattern(new MessagePatternDto(10L,"Test pattern"));
        inputDto.setStatus(true);

        MessageInfo existingMessageInfo = new MessageInfo();
        existingMessageInfo.setId(idMessInfo);

        when(messageInfoRepository.findById(idMessInfo)).thenReturn(java.util.Optional.of(existingMessageInfo));
        when(messageInfoRepository.save(any(MessageInfo.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        MessageInfoDto updatedDto = messageInfoServiceImpl.updateMessInfo(inputDto, idMessInfo);

        // Assert
        assertNotNull(updatedDto);
        assertEquals(inputDto.getPerson().getFirstName(), updatedDto.getPerson().getFirstName());
        assertEquals(inputDto.getDate(), updatedDto.getDate());
        assertEquals(inputDto.getPattern().getTemplate(), updatedDto.getPattern().getTemplate());
        assertEquals(inputDto.isStatus(), updatedDto.isStatus());
    }

    @Test
    public void testUpdateMessInfo_EntityNotFound() {
        // Arrange
        Long idMessInfo = 1L;
        MessageInfoDto inputDto = new MessageInfoDto();

        when(messageInfoRepository.findById(idMessInfo)).thenReturn(java.util.Optional.empty());

        // Act and Assert
        assertThrows(EntityNotFoundException.class, () -> {
            messageInfoServiceImpl.updateMessInfo(inputDto, idMessInfo);
        });
    }

    @Test
    void testDeleteMessInfo() {
        Long messageId = 1L;
        doNothing().when(messageInfoRepository).deleteById(messageId);

        assertDoesNotThrow(() -> messageInfoServiceImpl.deleteMessInfo(messageId));
    }
}