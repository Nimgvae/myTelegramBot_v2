package mytelegrambot_v2.entity.entityEnum;

/**
 * Enumeration representing different states for user interactions in the Telegram bot.
 */
public enum UserState {

    /**
     * Idle state indicating that the user is not currently engaged in any specific action.
     */
    IDLE,

    /**
     * State indicating that the bot is waiting for the user to provide an email address.
     */
    WAITING_FOR_EMAIL
}