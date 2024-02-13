package mytelegrambot_v2.services.interfaces;

import mytelegrambot_v2.dto.StatisticDto;
import java.util.List;


public interface StatisticService {

   StatisticDto getStatisticById(Long statId);
    List<StatisticDto> getAllStatistic();
    StatisticDto addStatistic(StatisticDto statisticDto);


    StatisticDto updateStatistic(StatisticDto statisticDto, Long statId);
    void deleteStatistic(Long statId);

     void deleteAllStatistic();

    List<StatisticDto> addAllStatistic(List<StatisticDto> sDto);
}
