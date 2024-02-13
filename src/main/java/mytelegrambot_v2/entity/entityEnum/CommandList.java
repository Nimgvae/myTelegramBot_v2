package mytelegrambot_v2.entity.entityEnum;
/**
 * Enumeration representing different commands supported by the Telegram bot along with their descriptions.
 */
public enum CommandList {
    /**
     * Command to get a welcome message.
     */
    START("/start", "get a welcome message"),
    /**
     * Command to get information on how to use the bot.
     */
    HELP("/help", "info how to use this bot"),
    /**
     * Command for registration to send out free adverts.
     */
    REGISTER("/register", "registration to send out free adverts."),
    /**
     * Command for registration to send out free emails.
     */
    ADDEMAIL("/addemail", "registration to send out free emails."),
    /**
     * Command to get the data stored about the user.
     */
    MYDATA("/mydata", "get your data stored"),
    /**
     * Command to delete the user's data from the system.
     */
    DELETEDATA("/deletedata", "delete my data"),
    /**
     * Command to request motivation from the AI.
     */
    MOTIVATION("/motivation", "Get motivation from AI");



    private final String command;
    private final String description;
    /**
     * Constructor to initialize the command and its description.
     * @param command The command string.
     * @param description The description of the command.
     */

    CommandList(String command, String description) {
        this.command = command;
        this.description = description;
    }

    /**
     * Retrieves the command string.
     * @return The command string.
     */

    public String getCommand() {
        return command;
    }
    /**
     * Retrieves the description of the command.
     * @return The description of the command.
     */

    public String getDescription() {
        return description;
    }
    /**
     * Retrieves the CommandList enum constant based on the command string.
     * @param command The command string.
     * @return The CommandList enum constant.
     */

    public static CommandList getByCommand(String command) {
        for (CommandList value : values()) {
            if (value.getCommand().equals(command)) {
                return value;
            }
        }
        return null;
    }

}
