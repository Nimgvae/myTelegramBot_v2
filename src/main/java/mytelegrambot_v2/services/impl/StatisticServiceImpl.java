package mytelegrambot_v2.services.impl;
import lombok.RequiredArgsConstructor;
import mytelegrambot_v2.dto.StatisticDto;
import mytelegrambot_v2.mappers.StatisticMapper;
import mytelegrambot_v2.repositories.StatisticRepository;
import mytelegrambot_v2.services.interfaces.StatisticService;
import org.springframework.stereotype.Service;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
/**
 * Service class for managing statistics.
 */
@Service
@RequiredArgsConstructor
public class StatisticServiceImpl implements StatisticService {
    private final StatisticRepository statisticRepository;
    /**
     * Retrieves a statistic by its ID.
     *
     * @param statId The ID of the statistic to retrieve.
     * @return The DTO of the retrieved statistic.
     */
    public StatisticDto getStatisticById(Long statId) {
        return StatisticMapper.mapToStatisticDto(Objects.requireNonNull(statisticRepository.findById(statId).orElse(null)));
    }
    /**
     * Retrieves all statistics, sorted by date.
     *
     * @return A list of DTOs representing all statistics, sorted by date.
     */
    public List<StatisticDto> getAllStatistic() {
        return statisticRepository.findAll()
                .stream()
                .map(StatisticMapper::mapToStatisticDto)
                .sorted(Comparator.comparing(StatisticDto::getDate))
                .collect(Collectors.toList());
    }
    /**
     * Adds a new statistic.
     *
     * @param statisticDto The DTO containing the statistic information to add.
     * @return The DTO of the added statistic.
     */
    public StatisticDto addStatistic(StatisticDto statisticDto) {
        return StatisticMapper.mapToStatisticDto(statisticRepository.save(StatisticMapper.mapToStatisticFromDto(statisticDto)));
    }

    /**
     * Updates an existing statistic.
     *
     * @param statisticDto The DTO containing the updated statistic information.
     * @param statId       The ID of the statistic to update.
     * @return The DTO of the updated statistic.
     */

    public StatisticDto updateStatistic(StatisticDto statisticDto, Long statId) {
        StatisticDto statisticDtoObj = StatisticMapper.mapToStatisticDto(Objects.requireNonNull(statisticRepository.findById(statId)
                .orElse(null)));
        statisticDtoObj.setPersonId(statisticDto.getPersonId());
        statisticDtoObj.setMessage(statisticDto.getMessage());
        statisticDtoObj.setEmail(statisticDto.isEmail());
        statisticDtoObj.setDate(statisticDto.getDate());
        return addStatistic(statisticDtoObj);
    }
    /**
     * Deletes a statistic by its ID.
     *
     * @param statId The ID of the statistic to delete.
     */
    public void deleteStatistic(Long statId) {
        statisticRepository.deleteById(statId);
    }
    /**
     * Deletes all statistics.
     */
    public void deleteAllStatistic() {
        statisticRepository.deleteAll();
    }
    /**
     * Adds a list of statistics.
     *
     * @param sDto The list of statistics DTOs to add.
     * @return The list of added statistics DTOs.
     */
    public List<StatisticDto> addAllStatistic(List<StatisticDto> sDto) {
        return sDto.stream()
                .map(this::addStatistic)
                .collect(Collectors.toList());
    }
}
