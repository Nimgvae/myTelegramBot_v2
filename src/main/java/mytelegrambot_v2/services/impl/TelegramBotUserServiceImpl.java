package mytelegrambot_v2.services.impl;
import com.vdurmont.emoji.EmojiParser;
import lombok.RequiredArgsConstructor;
import mytelegrambot_v2.bot.TelegramBot;
import mytelegrambot_v2.entity.entityEnum.UserState;
import mytelegrambot_v2.dto.PersonDto;
import mytelegrambot_v2.entity.Person;
import mytelegrambot_v2.entity.PersonState;
import mytelegrambot_v2.repositories.MessageInfoRepository;
import mytelegrambot_v2.repositories.PersonRepository;
import mytelegrambot_v2.repositories.PersonStateRepository;
import mytelegrambot_v2.util.GptProperties;
import mytelegrambot_v2.util.TelegramBotMessageService;
import mytelegrambot_v2.services.interfaces.TelegramBotUserService;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import java.io.IOException;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class for managing user-related functionality in the Telegram bot.
 */
@Service
@RequiredArgsConstructor
public class TelegramBotUserServiceImpl implements TelegramBotUserService {

    private static final Logger logger = LoggerFactory.getLogger(TelegramBotUserServiceImpl.class);

    private final PersonServiceImpl personServiceImpl;
    private final PersonRepository personRepository;
    private final MessageInfoRepository messageInfoRepository;
    private final TelegramBotMessageService telegramBotMessageService;
    private final PersonStateRepository personStateRepository;
    private final TelegramBot telegramBot;
    private final GptProperties gptProperties;

    /**
     * Retrieves user information and sends it as a message.
     *
     * @param chatId The chat ID of the user.
     */

    public void getUserInfo(long chatId) {
        personRepository.findById(chatId).ifPresentOrElse(person -> {
            PersonDto personDto = personServiceImpl.getPersonById(chatId);
            String userInfo = "User Information:\n" +
                    "First Name: " + personDto.getFirstName() + "\n" +
                    "Last Name: " + personDto.getLastName() + "\n" +
                    "Telegram Username: " + personDto.getTelegramName() + "\n" +
                    "Email: " + personDto.getEmail() + "\n" +
                    "Registered At: " + personDto.getRegisteredAt();
            telegramBotMessageService.prepareAndSendMessage(chatId, userInfo);
        }, () -> telegramBotMessageService.prepareAndSendMessage(chatId, "You are not registered in the database."));
    }

    /**
     * Removes a user from the system.
     *
     * @param msg The message object containing user information.
     */

    public void removeUser(Message msg) {
        Long chatId = msg.getChatId();
        personRepository.findById(chatId).ifPresentOrElse(person -> {
            messageInfoRepository.deleteAllMessageInfoByPersonId(chatId);
            personStateRepository.deleteById(chatId);
            personRepository.deleteById(chatId);
            telegramBotMessageService.prepareAndSendMessage(chatId, EmojiParser.parseToUnicode("Hm, you have been removed from my mailing list. :confused:"));
        }, () -> telegramBotMessageService.prepareAndSendMessage(chatId, "You are not registered for the mailing list."));
        if (personStateRepository.existsById(chatId)) {
            personStateRepository.deleteById(chatId);
        }
    }

    /**
     * Registers a user in the system.
     *
     * @param msg The message object containing user information.
     */

    public void registerUser(Message msg) {
        Long chatId = msg.getChatId();
        var chat = msg.getChat();
        personRepository.findById(chatId).ifPresentOrElse(person -> {
            telegramBotMessageService.prepareAndSendMessage(chatId, "You've already registered");
        }, () -> {
            Person personDto = new Person();
            personDto.setChatId(chatId);
            personDto.setFirstName(chat.getFirstName());
            personDto.setLastName(chat.getLastName());
            personDto.setTelegramName(chat.getUserName());
            personDto.setEmail("Not added.");
            personDto.setRegisteredAt(new Timestamp(System.currentTimeMillis()));
            personRepository.save(personDto);
            telegramBotMessageService.prepareAndSendMessage(chatId, EmojiParser.parseToUnicode("Cool, you have been added to the mailing list! :blush:"));
        });
    }

    /**
     * Handles the add email command received from the user.
     *
     * @param chatId The chat ID of the user.
     */

    public void addEmailCommandReceived(long chatId) {
        SendMessage requestEmailMessage = new SendMessage();
        requestEmailMessage.setChatId(String.valueOf(chatId));
        requestEmailMessage.setText("Please provide your email address:"); // Установка текста сообщения

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setOneTimeKeyboard(true);
        keyboardMarkup.setResizeKeyboard(true);

        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.add("Cancel");

        List<KeyboardRow> keyboard = new ArrayList<>();
        keyboard.add(keyboardRow);
        keyboardMarkup.setKeyboard(keyboard);

        requestEmailMessage.setReplyMarkup(keyboardMarkup);

        PersonState personState = new PersonState();
        personState.setId(chatId);
        personState.setUserState(UserState.WAITING_FOR_EMAIL);
        personStateRepository.save(personState);

        try {
            telegramBot.execute(requestEmailMessage);
        } catch (TelegramApiException e) {
            logger.error("Error sending message to user {}: {}", chatId, e.getMessage());
        }
    }
    /**
     * Requests motivation from GPT-3.5 model.
     *
     * @return Motivational text retrieved from the GPT-3.5 model.
     */

    public String requestMotivationFromGPT() {
        try {
            String prompt = "Give me example of motivation to living.";
            return requestMotivationFromGPTUsingAPI(prompt, gptProperties.getApiKeyPlaceholder(), gptProperties.gptApiModel());
        } catch (Exception e) {
            logger.error("Error requesting motivation from GPT: {}", e.getMessage());
            return "Failed to retrieve motivation. Please try again later.";
        }
    }
    /**
     * Requests motivation from GPT-3.5 model using the provided API.
     *
     * @param prompt    The prompt to send to the GPT-3.5 model.
     * @param apiKey    The API key for accessing the GPT-3.5 model.
     * @param modelName The name of the GPT-3.5 model to use.
     * @return Motivational text retrieved from the GPT-3.5 model.
     */
    public String requestMotivationFromGPTUsingAPI(String prompt, String apiKey, String modelName) {
    try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
        String apiUrl = "https://api.openai.com/v1/chat/completions";
        HttpPost request = new HttpPost(apiUrl);
        request.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        request.setHeader("Authorization", "Bearer " + apiKey);

        JSONArray messagesArray = new JSONArray();
        JSONObject userMessage = new JSONObject();
        userMessage.put("role", "user");
        userMessage.put("content", prompt);
        messagesArray.put(userMessage);
        JSONObject requestBody = new JSONObject();
        requestBody.put("model", modelName);
        requestBody.put("messages", messagesArray);
        requestBody.put("max_tokens", 150);

        StringEntity entity = new StringEntity(requestBody.toString());
        request.setEntity(entity);

        try (CloseableHttpResponse response = httpClient.execute(request)) {
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) {
                String jsonResponse = EntityUtils.toString(responseEntity);
                return filterMotivationalContent(jsonResponse);
            }
        }
    } catch (IOException e) {
        logger.error("Error requesting motivation from GPT: {}", e.getMessage());
    }
    return null;
}
    /**
     * Filters the motivational content from the response obtained from the GPT-3.5 model.
     *
     * @param jsonResponse The JSON response obtained from the GPT-3.5 model.
     * @return Motivational text extracted from the JSON response.
     */

//    public String filterMotivationalContent(String jsonResponse) {
//        JSONObject responseObj = new JSONObject(jsonResponse);
//        JSONArray choicesArray = responseObj.getJSONArray("choices");
//        StringBuilder motivationalContent = new StringBuilder();
//        for (int i = 0; i < choicesArray.length(); i++) {
//            JSONObject choiceObj = choicesArray.getJSONObject(i);
//            JSONObject messageObj = choiceObj.getJSONObject("message");
//            String role = messageObj.getString("role");
//            String content = messageObj.getString("content");
//            if (role.equals("assistant")) {
//                motivationalContent.append(content).append("\n");
//            }
//        }
//        return motivationalContent.toString();
//    }
    public String filterMotivationalContent(String jsonResponse) {
        JSONObject responseObj = new JSONObject(jsonResponse);
        if (responseObj.has("choices")) {
            JSONArray choicesArray = responseObj.getJSONArray("choices");
            StringBuilder motivationalContent = new StringBuilder();
            for (int i = 0; i < choicesArray.length(); i++) {
                JSONObject choiceObj = choicesArray.getJSONObject(i);
                JSONObject messageObj = choiceObj.getJSONObject("message");
                String role = messageObj.getString("role");
                String content = messageObj.getString("content");
                if (role.equals("assistant")) {
                    motivationalContent.append(content).append("\n");
                }
            }
            return motivationalContent.toString();
        } else {
            logger.error("No 'choices' array found in the response from GPT.");
            return "Failed to retrieve motivation. Please try again later.";
        }
    }
}
