package mytelegrambot_v2.services.interfaces;
import org.telegram.telegrambots.meta.api.objects.Message;



public interface TelegramBotUserService {

    void getUserInfo(long chatId);

    void removeUser(Message msg);

    void registerUser(Message msg);

    void addEmailCommandReceived(long chatId);

    String requestMotivationFromGPT();
    String requestMotivationFromGPTUsingAPI(String prompt, String apiKey, String modelName);

    String filterMotivationalContent(String jsonResponse);
}
