package mytelegrambot_v2.controllers;

import mytelegrambot_v2.dto.StatisticDto;
import mytelegrambot_v2.services.impl.StatisticServiceImpl;
import mytelegrambot_v2.util.SimpleResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StatisticControllerTest {
    @Mock
    private StatisticServiceImpl statisticService;

    @InjectMocks
    private StatisticController statisticController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetStatisticById_WhenExists() {
        Long id = 1L;
        StatisticDto expectedDto = new StatisticDto();
        expectedDto.setId(id);
        when(statisticService.getStatisticById(id)).thenReturn(expectedDto);
        SimpleResponse<?> response = statisticController.getStatisticById(id);
        assertEquals(expectedDto, response.getResult());
    }
    @Test
    void testGetStatisticById_WhenNotFound() {
        Long id = 1L;
        when(statisticService.getStatisticById(id)).thenReturn(null);
        ResponseEntity<Void> response = statisticController.deleteStatistic(id);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }


    @Test
    void getAllStatistic() {
        List<StatisticDto> expectedDtoList = new ArrayList<>();
        expectedDtoList.add(new StatisticDto());
        when(statisticService.getAllStatistic()).thenReturn(expectedDtoList);
        SimpleResponse<?> response = statisticController.getAllStatistic();
        assertEquals(expectedDtoList, response.getResult());
    }

    @Test
    void addStatistic() {
        StatisticDto statisticDto = new StatisticDto();

        // Act
        SimpleResponse<?> response = statisticController.addStatistic(statisticDto);

        // Assert
        assertEquals(HttpStatus.OK, response.getResult());
        verify(statisticService, times(1)).addStatistic(statisticDto);
    }
    @Test
    void testUpdateStatistic_WhenFound() {
        // Arrange
        Long id = 1L;
        StatisticDto statisticDto = new StatisticDto();
        statisticDto.setId(id);
        when(statisticService.updateStatistic(statisticDto, id)).thenReturn(statisticDto);

        // Act
        SimpleResponse<?> response = statisticController.updateStatistic(statisticDto, id);

        // Assert
        assertEquals(HttpStatus.OK, response.getResult());
    }

    @Test
    void testUpdateStatistic_WhenNotFound() {
        // Arrange
        Long id = 1L;
        StatisticDto statisticDto = new StatisticDto();
        statisticDto.setId(id);
        when(statisticService.updateStatistic(statisticDto, id)).thenReturn(null);

        // Act
        SimpleResponse<?> response = statisticController.updateStatistic(statisticDto, id);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getResult());
    }

    @Test
    void testDeleteStatistic() {
        // Arrange
        Long id = 1L;

        // Act
        ResponseEntity<Void> response = statisticController.deleteStatistic(id);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(statisticService, times(1)).deleteStatistic(id);
    }

    @Test
    void testDeleteAllStatistic() {
        // Act
        ResponseEntity<Void> response = statisticController.deleteAllStatistic();

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(statisticService, times(1)).deleteAllStatistic();
    }
}