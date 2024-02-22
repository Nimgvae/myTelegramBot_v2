package mytelegrambot_v2.services.impl;

import jakarta.persistence.EntityNotFoundException;
import mytelegrambot_v2.dto.MessageInfoDto;
import mytelegrambot_v2.dto.MessagePatternDto;
import mytelegrambot_v2.dto.PersonDto;
import mytelegrambot_v2.entity.MessageInfo;
import mytelegrambot_v2.entity.MessagePattern;
import mytelegrambot_v2.entity.Person;
import mytelegrambot_v2.repositories.MessageInfoRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MessageInfoServiceImplTest {

    @Mock
    private MessageInfoRepository messageInfoRepository;

    @Mock
    private PersonServiceImpl personServiceImpl;

    @Mock
    private MessagePatternServiceImpl messagePatternServiceImpl;

    @InjectMocks
    private MessageInfoServiceImpl messageInfoServiceImpl;

    private MessagePatternDto messagePatternDto;
    private PersonDto personDto;
    private MessageInfo messageInfo;
    private MessageInfoDto messageInfoDto;

    @BeforeEach
    void setUp() {
        LocalDate date = LocalDate.now();
        messagePatternDto = new MessagePatternDto();
        messagePatternDto.setId(10L);
        messagePatternDto.setTemplate("Template");
        messagePatternDto.setAdv(true);

        personDto = new PersonDto();
        personDto.setChatId(10L);
        personDto.setFirstName("Person");
        personDto.setLastName("First");
        personDto.setEmail("Eugenkorschun@gmail.com");
        personDto.setTelegramName("nimgvae");

        messageInfo = new MessageInfo();
        messageInfo.setId(10L);
        messageInfo.setPerson(new Person(10L,"Person", "First","Eugenkorschun@gmail.com", "nimgvae", null));
        messageInfo.setPattern(new MessagePattern(10L, "Template",true));
        messageInfo.setStatus(false);
        messageInfo.setDate(date);


        messageInfoDto = new MessageInfoDto();
        messageInfoDto.setId(10L);
        messageInfoDto.setPerson(personDto);
        messageInfoDto.setPattern(messagePatternDto);
        messageInfoDto.setDate(messageInfo.getDate());
    }

    @Test
    void testGetMessInfoById() {
        Long messageId = 1L;
        when(messageInfoRepository.findById(messageId)).thenReturn(Optional.of(messageInfo));
        MessageInfoDto actualDto = messageInfoServiceImpl.getMessInfoById(messageId);
        assertEquals(messageInfoDto, actualDto);
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
        when(messagePatternServiceImpl.getMessagePatternById(anyLong())).thenReturn(messagePatternDto);
        when(personServiceImpl.getPersonById(anyLong())).thenReturn(personDto);
        when(messagePatternServiceImpl.getMessagePatternById(anyLong())).thenReturn(messagePatternDto);
        when(messageInfoRepository.save(any(MessageInfo.class))).thenReturn(messageInfo);
        MessageInfoDto savedMessageInfoDto = messageInfoServiceImpl.addMessageInfo(messageInfoDto);
        assertEquals(messageInfo.getId(), savedMessageInfoDto.getId());
        assertEquals(personDto, savedMessageInfoDto.getPerson());
        assertEquals(messagePatternDto, savedMessageInfoDto.getPattern());
    }

    @Test
    void testUpdateMessInfo_Success() {
        Long idMessInfo = 10L;
        MessageInfoDto messageInfoDto1 = new MessageInfoDto();
        messageInfoDto1.setId(idMessInfo);
        messageInfoDto1.setPerson(personDto);
        messageInfoDto1.setPattern(messagePatternDto);
        messageInfoDto1.setDate(LocalDate.now());
        messageInfoDto1.setStatus(true);

        Optional<MessageInfo> optionalMessageInfo = Optional.ofNullable(messageInfo);
        when(messageInfoRepository.findById(anyLong())).thenReturn(optionalMessageInfo);
        MessageInfoDto updatedMessageInfoDto = messageInfoServiceImpl.updateMessInfo(messageInfoDto1, idMessInfo);
        assertEquals(messageInfoDto1, updatedMessageInfoDto);
    }

    @Test
    public void testUpdateMessInfo_EntityNotFound() {
        Long idMessInfo = 10L;
        MessageInfoDto inputDto = new MessageInfoDto();
        when(messageInfoRepository.findById(idMessInfo)).thenReturn(java.util.Optional.empty());
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