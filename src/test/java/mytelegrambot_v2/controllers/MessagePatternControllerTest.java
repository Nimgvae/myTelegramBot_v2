package mytelegrambot_v2.controllers;

import mytelegrambot_v2.dto.MessagePatternDto;
import mytelegrambot_v2.services.impl.MessagePatternServiceImpl;
import mytelegrambot_v2.util.SimpleResponse;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MessagePatternControllerTest {

    @Mock
    private MessagePatternServiceImpl messagePatternServiceImpl;

    @InjectMocks
    private MessagePatternController messagePatternController;

    @Test
    void getMessagePatternById_WhenPatternExists_ReturnsPattern() {
        // Arrange
        Long patternId = 1L;
        MessagePatternDto patternDto = new MessagePatternDto();
        when(messagePatternServiceImpl.getMessagePatternById(patternId)).thenReturn(patternDto);

        // Act
        SimpleResponse<?> response = messagePatternController.getMessagePatternById(patternId);

        // Assert
        assertEquals(patternDto, response.getResult());
    }

    @Test
    void getMessagePatternById_WhenPatternDoesNotExist_ReturnsNotFound() {
        // Arrange
        Long patternId = 1L;
        when(messagePatternServiceImpl.getMessagePatternById(patternId)).thenReturn(null);

        // Act
        SimpleResponse<?> response = messagePatternController.getMessagePatternById(patternId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getResult());
    }

    @Test
    void getAllMessagePattern_ReturnsListOfPatterns() {
        // Arrange
        List<MessagePatternDto> patternList = new ArrayList<>();
        when(messagePatternServiceImpl.getAllMessagePattern()).thenReturn(patternList);

        // Act
        SimpleResponse<?> response = messagePatternController.getALlMessagePattern();

        // Assert
        assertEquals(patternList, response.getResult());
    }

    @Test
    void addMessagePattern_ReturnsOkResponse() {
        // Arrange
        MessagePatternDto patternDto = new MessagePatternDto();
        when(messagePatternServiceImpl.addMessagePattern(patternDto)).thenReturn(patternDto);

        // Act
        SimpleResponse<?> response = messagePatternController.addMessagePattern(patternDto);

        // Assert
        assertEquals(patternDto, response.getResult());
    }

    @Test
    void updateMessagePattern_ReturnsUpdatedPattern() {
        // Arrange
        Long patternId = 1L;
        MessagePatternDto patternDto = new MessagePatternDto();
        when(messagePatternServiceImpl.updateMessagePattern(patternDto, patternId)).thenReturn(patternDto);

        // Act
        SimpleResponse<?> response = messagePatternController.updateMessagePattern(patternDto, patternId);

        // Assert
        assertEquals(patternDto, response.getResult());
    }

    @Test
    void deleteMessagePattern_ReturnsNoContentResponse() {
        // Arrange
        Long patternId = 1L;

        // Act
        ResponseEntity<Void> responseEntity = messagePatternController.deleteMessagePattern(patternId);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(messagePatternServiceImpl, times(1)).deleteMessagePattern(patternId);
    }
}