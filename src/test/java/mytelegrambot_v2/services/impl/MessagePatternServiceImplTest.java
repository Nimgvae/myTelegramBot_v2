package mytelegrambot_v2.services.impl;

import jakarta.ws.rs.NotFoundException;
import mytelegrambot_v2.dto.MessagePatternDto;
import mytelegrambot_v2.entity.MessagePattern;
import mytelegrambot_v2.repositories.MessagePatternRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MessagePatternServiceImplTest {
    @Mock
    private MessagePatternRepository messagePatternRepository;

    @InjectMocks
    private MessagePatternServiceImpl messagePatternService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddMessagePattern() {

        MessagePatternDto inputDto = new MessagePatternDto();
        inputDto.setTemplate("Test Template");
        MessagePattern savedPattern = new MessagePattern();
        savedPattern.setId(1L);
        savedPattern.setTemplate("Test Template");
        when(messagePatternRepository.save(any(MessagePattern.class))).thenReturn(savedPattern);
        MessagePatternDto resultDto = messagePatternService.addMessagePattern(inputDto);
        assertNotNull(resultDto);
        assertEquals(savedPattern.getId(), resultDto.getId());
        assertEquals(inputDto.getTemplate(), resultDto.getTemplate());
    }

    @Test
    void testUpdateMessagePattern_Successful() {
        Long id = 1L;
        MessagePatternDto inputDto = new MessagePatternDto();
        inputDto.setTemplate("Updated Template");
        MessagePattern existingPattern = new MessagePattern();
        existingPattern.setId(id);
        existingPattern.setTemplate("Old Template");

        when(messagePatternRepository.findById(id)).thenReturn(Optional.of(existingPattern));
        when(messagePatternRepository.save(any(MessagePattern.class))).thenAnswer(invocation -> invocation.getArgument(0));
        MessagePatternDto resultDto = messagePatternService.updateMessagePattern(inputDto, id);
        assertNotNull(resultDto);
        assertEquals(existingPattern.getId(), resultDto.getId());
        assertEquals(inputDto.getTemplate(), resultDto.getTemplate());
    }

    @Test
    void testUpdateMessagePattern_NotFound() {

        Long id = 1L;
        MessagePatternDto inputDto = new MessagePatternDto();
        inputDto.setTemplate("Updated Template");
        when(messagePatternRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> messagePatternService.updateMessagePattern(inputDto, id));
    }

    @Test
    void testDeleteMessagePattern() {

        Long id = 1L;
        messagePatternService.deleteMessagePattern(id);
        verify(messagePatternRepository, times(1)).deleteById(id);
    }

    @Test
    void testGetMessagePatternById_Successful() {

        Long id = 1L;
        MessagePattern existingPattern = new MessagePattern();
        existingPattern.setId(id);
        existingPattern.setTemplate("Test Template");
        when(messagePatternRepository.findById(id)).thenReturn(Optional.of(existingPattern));
        MessagePatternDto resultDto = messagePatternService.getMessagePatternById(id);
        assertNotNull(resultDto);
        assertEquals(existingPattern.getId(), resultDto.getId());
        assertEquals(existingPattern.getTemplate(), resultDto.getTemplate());
    }

    @Test
    void testGetMessagePatternById_NotFound() {

        Long id = 1L;
        when(messagePatternRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> messagePatternService.getMessagePatternById(id));
    }

    @Test
    void testGetAllMessagePattern() {
        List<MessagePattern> patterns = new ArrayList<>();
        MessagePattern pattern1 = new MessagePattern();
        pattern1.setId(1L);
        pattern1.setTemplate("Template 1");
        patterns.add(pattern1);

        MessagePattern pattern2 = new MessagePattern();
        pattern2.setId(2L);
        pattern2.setTemplate("Template 2");
        patterns.add(pattern2);
        when(messagePatternRepository.findAll()).thenReturn(patterns);

        List<MessagePatternDto> resultDtos = messagePatternService.getAllMessagePattern();
        assertNotNull(resultDtos);
        assertEquals(2, resultDtos.size());
        assertEquals(patterns.get(0).getId(), resultDtos.get(0).getId());
        assertEquals(patterns.get(0).getTemplate(), resultDtos.get(0).getTemplate());
        assertEquals(patterns.get(1).getId(), resultDtos.get(1).getId());
        assertEquals(patterns.get(1).getTemplate(), resultDtos.get(1).getTemplate());
    }

}