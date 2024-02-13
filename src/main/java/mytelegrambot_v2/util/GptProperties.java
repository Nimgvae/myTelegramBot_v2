package mytelegrambot_v2.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
/**
 * Component class for managing properties related to the GPT (Generative Pre-trained Transformer) model.
 */
@Component
public class GptProperties {

    @Value("${gpt.api.url}")
    private String gptApiUrl;

    @Value("${api.key.placeholder}")
    private String apiKeyPlaceholder;

    @Value("${gpt.api.model}")
    private String gptApiModel;
    /**
     * Retrieves the URL of the GPT API.
     *
     * @return The URL of the GPT API.
     */
    public String getGptApiUrl() {
        return gptApiUrl;
    }
    /**
     * Retrieves the placeholder for the API key.
     *
     * @return The placeholder for the API key.
     */
    public String getApiKeyPlaceholder() {
        return apiKeyPlaceholder;
    }
    /**
     * Retrieves the name of the GPT API model.
     *
     * @return The name of the GPT API model.
     */
    public String gptApiModel(){
        return gptApiModel;
    }
}
