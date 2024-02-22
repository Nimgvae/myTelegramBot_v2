package mytelegrambot_v2.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
/**
 * Entity class representing a person in a Telegram bot application.
 */
@Entity
@Data
@Table(name = "person")
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    @Id
    @NotNull(message = "ChatId cannot be null")
    private Long chatId;

    private String firstName;

    private String lastName;

    private String email;

    private String telegramName;

    private Timestamp registeredAt;
}
