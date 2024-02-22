package mytelegrambot_v2.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * Data Transfer Object (DTO) representing a statistic in the context of a Telegram bot.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatisticDto {
    /**
     * The ID of the statistic.
     */
    private Long id;

    /**
     * The ID of the person associated with the statistic.
     */
    private Long personId;

    /**
     * The message associated with the statistic.
     */
    private String message;

    /**
     * A flag indicating whether the statistic involves an email.
     */
    private boolean email;

    /**
     * The timestamp indicating when the statistic occurred.
     */
    private Timestamp date;
}