package mytelegrambot_v2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) representing a message pattern.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessagePatternDto {
    /**
     * The ID of the message pattern.
     */
    private Long id;

    /**
     * The template of the message pattern.
     */
    private String template;

    private boolean adv;
}
