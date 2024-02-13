package mytelegrambot_v2.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
/**
 * Entity class representing advertisements in a Telegram bot application.
 */
@Data
@Entity(name = "adsTable")
public class Advertising {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String ad;
}
