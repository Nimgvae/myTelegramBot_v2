package mytelegrambot_v2.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;


/**
 * Data Transfer Object (DTO) representing a person in the context of a Telegram bot.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonDto {
    /**
     * The chat ID of the person.
     */
    private Long chatId;

    /**
     * The first name of the person.
     */
    private String firstName;

    /**
     * The last name of the person.
     */
    private String lastName;

    /**
     * The email address of the person.
     */
    private String email;

    /**
     * The Telegram username of the person.
     */
    private String telegramName;

    /**
     * The timestamp indicating when the person was registered.
     */
    private Timestamp registeredAt;
}
