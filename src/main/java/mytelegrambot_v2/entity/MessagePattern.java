package mytelegrambot_v2.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity class representing a message pattern in a Telegram bot application.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessagePattern {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "template",
            nullable = false)
    private String template;

    private boolean adv = false;
}
