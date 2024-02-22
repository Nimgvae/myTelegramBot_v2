package mytelegrambot_v2.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mytelegrambot_v2.entity.entityEnum.UserState;
/**
 * Represents the state of a person in the bot.
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class PersonState {
    /** The unique identifier for the person. */
    @Id
    private Long id;

    /** The state of the user associated with this person. */
    private UserState userState;
}
