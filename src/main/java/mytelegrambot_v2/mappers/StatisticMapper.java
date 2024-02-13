package mytelegrambot_v2.mappers;
import lombok.val;
import mytelegrambot_v2.dto.StatisticDto;
import mytelegrambot_v2.entity.Statistic;
/**
 * Mapper class for converting between Statistic entities and StatisticDto DTOs.
 */
public class StatisticMapper {
    /**
     * Maps a Statistic entity to a StatisticDto DTO.
     * @param statistic The Statistic entity to map.
     * @return The corresponding StatisticDto DTO.
     */
    public static StatisticDto mapToStatisticDto (Statistic statistic){
        val dto = new StatisticDto();
        dto.setId(statistic.getId());
        dto.setPersonId(statistic.getPersonId());
        dto.setMessage(statistic.getMessage());
        dto.setEmail(statistic.isEmail());
        dto.setDate(statistic.getDate());
        return dto;
    }
    /**
     * Maps a StatisticDto DTO to a Statistic entity.
     * @param dto The StatisticDto DTO to map.
     * @return The corresponding Statistic entity.
     */
    public static Statistic mapToStatisticFromDto(StatisticDto dto){
        val statistic = new Statistic();
        statistic.setId(dto.getId());
        statistic.setPersonId(dto.getPersonId());
        statistic.setMessage(dto.getMessage());
        statistic.setEmail(dto.isEmail());
        statistic.setDate(dto.getDate());
        return statistic;
    }
}
