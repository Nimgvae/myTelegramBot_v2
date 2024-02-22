package mytelegrambot_v2.services.impl;

import mytelegrambot_v2.dto.StatisticDto;
import mytelegrambot_v2.entity.Statistic;
import mytelegrambot_v2.mappers.StatisticMapper;
import mytelegrambot_v2.repositories.StatisticRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.sql.Timestamp;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class StatisticServiceImplTest {
    @Mock
    private StatisticRepository statisticRepository;

    @InjectMocks
    private StatisticServiceImpl statisticService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testAddStatistic_Successful() {

        StatisticDto statisticDto = new StatisticDto();
        statisticDto.setPersonId(12345L);
        statisticDto.setMessage("Test message");
        statisticDto.setEmail(true);
        statisticDto.setDate(new Timestamp(System.currentTimeMillis()));
        Statistic savedStatistic = StatisticMapper.mapToStatisticFromDto(statisticDto);
        savedStatistic.setId(1L);
        when(statisticRepository.save(any(Statistic.class))).thenReturn(savedStatistic);
        StatisticDto resultDto = statisticService.addStatistic(statisticDto);

        assertNotNull(resultDto);
        assertEquals(savedStatistic.getId(), resultDto.getId());
        assertEquals(statisticDto.getPersonId(), resultDto.getPersonId());
        assertEquals(statisticDto.getMessage(), resultDto.getMessage());
        assertEquals(statisticDto.isEmail(), resultDto.isEmail());
        assertEquals(statisticDto.getDate(), resultDto.getDate());
    }

    @Test
    void testUpdateStatistic_Successful() {
        Long statId = 1L;
        StatisticDto statisticDto = new StatisticDto();
        statisticDto.setId(statId);
        statisticDto.setPersonId(54321L);
        statisticDto.setMessage("Updated message");
        statisticDto.setEmail(false);
        statisticDto.setDate(new Timestamp(System.currentTimeMillis()));

        Statistic existingStatistic = new Statistic();
        existingStatistic.setId(statId);
        existingStatistic.setPersonId(12345L);
        existingStatistic.setMessage("Test message");
        existingStatistic.setEmail(true);
        existingStatistic.setDate(new Timestamp(System.currentTimeMillis()));

        when(statisticRepository.findById(statId)).thenReturn(Optional.of(existingStatistic));
        when(statisticRepository.save(any(Statistic.class))).thenAnswer(invocation -> invocation.getArgument(0));

        StatisticDto resultDto = statisticService.updateStatistic(statisticDto, statId);
        assertNotNull(resultDto);
        assertEquals(statId, resultDto.getId());
        assertEquals(statisticDto.getPersonId(), resultDto.getPersonId());
        assertEquals(statisticDto.getMessage(), resultDto.getMessage());
        assertEquals(statisticDto.isEmail(), resultDto.isEmail());
        assertEquals(statisticDto.getDate(), resultDto.getDate());
    }

    @Test
    void testDeleteStatistic_Successful() {

        Long statId = 1L;
        statisticService.deleteStatistic(statId);
        verify(statisticRepository, times(1)).deleteById(statId);
    }

    @Test
    void testDeleteAllStatistic_Successful() {
        statisticService.deleteAllStatistic();

        verify(statisticRepository, times(1)).deleteAll();
    }
}