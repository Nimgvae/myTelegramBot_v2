package mytelegrambot_v2.dto;

import lombok.*;
import mytelegrambot_v2.dto.MessagePatternDto;
import mytelegrambot_v2.dto.PersonDto;
import java.time.LocalDate;
/**
 * Data Transfer Object (DTO) representing information about a message.
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageInfoDto {
    /**
     * The ID of the message.
     */
    private Long id;

    /**
     * The pattern associated with the message.
     */
    private MessagePatternDto pattern;

    /**
     * The person associated with the message.
     */
    private PersonDto person;

    /**
     * The date when the message was sent.
     */
    private LocalDate date;

    /**
     * The status of the message.
     */
    private boolean status;
}

