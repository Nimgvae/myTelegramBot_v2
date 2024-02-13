package mytelegrambot_v2.entity;

import jakarta.persistence.*;
import lombok.Data;
/**
 * Entity class representing a message pattern in a Telegram bot application.
 */
@Entity
@Data
public class MessagePattern {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "template",
            nullable = false)
    private String template;

    private boolean adv = false;
}
