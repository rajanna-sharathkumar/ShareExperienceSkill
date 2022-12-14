package util;

public class SkillKeys {

    //intent names
    public static final String SAVE_USER_NAME_INTENT = "SaveUserNameIntent";
    public static final String LAUNCH_REQUEST_INTENT = "LaunchRequestIntent";
    public static final String SHARE_EXPERIENCE_INTENT = "ShareExperienceToUserIntent";
    public static final String YES_INTENT = "AMAZON.YesIntent";

    //utterance slot keys
    public static final String USER_NAME_SLOT_KEY = "user";

    //session keys
    public static final String USER_NAME_SESSION_KEY = "userName";
    public static final String USER_MESSAGE_FACT_ID_SESSION_KEY = "factId";
    public static final String USER_MESSAGE_FROM_SESSION_KEY = "messageFrom";
    public static final String PREVIOUS_INTENT_SESSION_KEY = "lastIntent";
    public static final String PREVIOUS_EXPERIENCE_SESSION_KEY = "lastExperience";

    //prompts
    public static final String UNKNOWN_USER_PROMPT= ", What is your name? ";
    public static final String UNKNOWN_USER_RE_PROMPT= "You can tell me your name by saying My name is ...";
    public static final String SAVE_USER_NAME_PROMPT = "Hi %s! Do you want to hear a fact?";
    public static final String SAVE_USER_NAME_RE_PROMPT = "Sorry, I didn't understand. Do you want to hear a fact?";
    public static final String DO_YOU_WANT_TO_SHARE_PROMPT = "Do you want to share this with your friend?";
    public static final String YOU_CAN_SHARE_PROMPT = "You can share this with your friend. Just say, share this";
    public static final String MESSAGE_SHARE_COMPLETE = "At your service!";
    public static final String DELIVER_MESSAGE_PROMPT = "You got a message from ";
    public static final String TARGET_USER_DOES_NOT_EXIST = "I don't find a user named ";
    public static final String TARGET_USER_NAME_ELICITATION = "With whom do you wanna share this experience?";
}
