package mytelegrambot_v2.bot.config;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
/**
 * Configuration class for Telegram Bot properties.
 */
@Configuration
@Data
@PropertySource("application.properties")
public class TelegramBotConfig {
     /**
      * The name of the Telegram Bot.
      */
     @Value("${bot.name}")
     String botName;
     /**
      * The token of the Telegram Bot.
      */
     @Value("${bot.token}")
     String botToken;
     /**
      * The owner ID of the Telegram Bot.
      */
     @Value("${bot.owner}")
     Long ownerId;
}
