package mytelegrambot_v2.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
/**
 * Entity class representing statistical information in a Telegram bot application.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
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
