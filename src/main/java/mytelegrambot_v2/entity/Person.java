package mytelegrambot_v2.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.sql.Timestamp;
/**
 * Entity class representing a person in a Telegram bot application.
 */
@Entity
@Data
@Table(name = "person")
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
