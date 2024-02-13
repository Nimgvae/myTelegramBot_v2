package mytelegrambot_v2.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import mytelegrambot_v2.entity.entityEnum.UserState;
/**
 * Represents the state of a person in the bot.
 */
@Data
@Entity
public class PersonState {
    /** The unique identifier for the person. */
    @Id
    private Long id;

    /** The state of the user associated with this person. */
    private UserState userState;
}
