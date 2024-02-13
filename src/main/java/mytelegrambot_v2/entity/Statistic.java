package mytelegrambot_v2.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.sql.Timestamp;
/**
 * Entity class representing statistical information in a Telegram bot application.
 */
@Entity
@Data
public class Statistic {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "person_id")
    private Long personId;

    @Column(nullable = false)
    private String message;

    @Column
    private boolean email;

    @Column(nullable = false)
    private Timestamp date;
}
