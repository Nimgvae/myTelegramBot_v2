package mytelegrambot_v2.controllers;

import mytelegrambot_v2.dto.MessageInfoDto;
import mytelegrambot_v2.dto.MessagePatternDto;
import mytelegrambot_v2.services.impl.MessageInfoServiceImpl;
import mytelegrambot_v2.util.SimpleResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class MessageInfoControllerTest {

    @Mock
    private MessageInfoServiceImpl messageInfoServiceImpl;

    @InjectMocks
    private MessageInfoController messageInfoController;
    @Test
    void getMessageInfoById_WhenMessageInfoExists_ReturnsOkResponse() {
        // Arrange
        Long messageId = 1L;
        MessageInfoDto messageInfoDto = new MessageInfoDto();
        when(messageInfoServiceImpl.getMessInfoById(messageId)).thenReturn(messageInfoDto);

        // Act
        ResponseEntity<SimpleResponse<MessageInfoDto>> responseEntity = messageInfoController.getMessageInfoById(messageId);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(messageInfoDto, responseEntity.getBody().getResult());
    }

    @Test
    void getMessageInfoById_WhenMessageInfoDoesNotExist_ReturnsNotFoundResponse() {
        // Arrange
        Long messageId = 1L;
        when(messageInfoServiceImpl.getMessInfoById(messageId)).thenReturn(null);

        // Act
        ResponseEntity<SimpleResponse<MessageInfoDto>> responseEntity = messageInfoController.getMessageInfoById(messageId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void addMessageInfo_ReturnsOkResponse() {
        // Arrange
        MessageInfoDto messageInfoDto = new MessageInfoDto();
        doAnswer(invocation -> null).when(messageInfoServiceImpl).addMessageInfo(any());

        // Act
        SimpleResponse<?> response = messageInfoController.addMessageInfo(messageInfoDto);

        // Assert
        assertEquals(HttpStatus.OK, response.getResult());
    }

    @Test
    void updateMessageInfo_WhenMessageInfoExists_ReturnsOkResponse() {
        // Arrange
        Long messageId = 1L;
        MessageInfoDto messageInfoDto = new MessageInfoDto();
        MessagePatternDto messagePatternDto = new MessagePatternDto();
        messagePatternDto.setId(1L); // Установка id шаблона
        messagePatternDto.setTemplate("Some pattern");
        messageInfoDto.setId(1L); // Установка id сообщения
        messageInfoDto.setPattern(messagePatternDto); // Установка шаблона
        // Установка других значений по мере необходимости

        when(messageInfoServiceImpl.updateMessInfo(any(), anyLong())).thenReturn(messageInfoDto);

        // Act
        SimpleResponse<?> response = messageInfoController.updateMessageInfo(messageInfoDto, messageId);

        // Assert
        assertEquals(messageInfoDto, response.getResult());
    }

    @Test
    void updateMessageInfo_WhenMessageInfoDoesNotExist_ReturnsNotFoundResponse() {
        // Arrange
        Long messageId = 1L;
        MessageInfoDto messageInfoDto = new MessageInfoDto();
        when(messageInfoServiceImpl.updateMessInfo(any(), anyLong())).thenReturn(null);

        // Act
        SimpleResponse<?> response = messageInfoController.updateMessageInfo(messageInfoDto, messageId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getResult());
    }

    @Test
    void deleteMessageInfo_ReturnsNoContentResponse() {
        // Arrange
        Long messageId = 1L;
        doNothing().when(messageInfoServiceImpl).deleteMessInfo(anyLong());

        // Act
        ResponseEntity<Void> responseEntity = messageInfoController.deleteMessageInfo(messageId);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }

    // Add more tests as needed...

}