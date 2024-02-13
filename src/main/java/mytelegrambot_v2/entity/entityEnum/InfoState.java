package mytelegrambot_v2.entity.entityEnum;
/**
 * Enumeration representing different states and corresponding messages for the Telegram bot.
 */
public enum InfoState {
    /**
     * Help text providing an overview of available commands and functionalities.
     */
    HELP_TEXT("This bot is created to demonstrate Spring capabilities.\n\n" +
            "You can execute commands from the main menu on the left or by typing a command:\n\n" +
            "Type /start to see a welcome message\n\n" +
            "Type /motivation to request motivation from the AI\n\n"+
            "Type /register to registration to send out free adverts\n\n"+
            "Type /addemail to registration to send out free email\n\n"+
            "Type /mydata to see data stored about yourself\n\n" +
            "Type /deletedata to delete your data from our list\n\n"+
            "Type /help to see this message again"),
    /**
     * Error text indicating that an error occurred during the bot's operation.
     */
    ERROR_TEXT("Error occurred: "),;
    private final String description;
    /**
     * Constructor to initialize the description of each enum constant.
     * @param s The description or message associated with the enum constant.
     */

    InfoState(String s) {
        this.description = s;
    }
    /**
     * Retrieves the description or message associated with the enum constant.
     * @return The description or message.
     */
    public String getDescription() {
        return description;
    }
}
