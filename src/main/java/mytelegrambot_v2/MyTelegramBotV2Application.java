package mytelegrambot_v2;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@OpenAPIDefinition(
        info=@Info(
                title = "Students Project",
                version = "3.0.0",
                description = "This project is my pet project",
                termsOfService = "www.linkedin.com/in/eugen-korschun-769a17124",
                contact = @Contact(
                        name = "Eugen Korschun",
                        email = "Eugenkorschun@gmail.com"),
                license = @License(name = "licence",
                        url = "www.linkedin.com/in/eugen-korschun-769a17124")
        )
)
@EnableScheduling
public class MyTelegramBotV2Application {

    public static void main(String[] args) {
        SpringApplication.run(MyTelegramBotV2Application.class, args);
    }

}
