package mytelegrambot_v2.entity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Entity class representing message information in a Telegram bot application.
 */
@Entity
@Data
public class MessageInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "pattern_id")
    private MessagePattern pattern;
    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;
    @Column(nullable = false)
    private LocalDate date;
    private boolean status;
}
