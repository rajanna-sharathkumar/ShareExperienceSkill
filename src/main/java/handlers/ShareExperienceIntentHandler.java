package handlers;

import static util.SkillKeys.MESSAGE_SHARE_COMPLETE;
import static util.SkillKeys.PREVIOUS_EXPERIENCE_SESSION_KEY;
import static util.SkillKeys.SHARE_EXPERIENCE_INTENT;
import static util.SkillKeys.TARGET_USER_DOES_NOT_EXIST;
import static util.SkillKeys.TARGET_USER_NAME_ELICITATION;
import static util.SkillKeys.USER_NAME_SESSION_KEY;

import java.util.Map;
import java.util.Optional;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;

import util.DynamoDbHelper;

public class ShareExperienceIntentHandler implements IntentRequestHandler {
    @Override
    public boolean canHandle(HandlerInput handlerInput, IntentRequest intentRequest) {
        return intentRequest.getIntent().getName().equals(SHARE_EXPERIENCE_INTENT);
    }

    @Override
    public Optional<Response> handle(HandlerInput handlerInput, IntentRequest intentRequest) {

        String speechText;
        Map<String, Object> currentSessionAttributes = handlerInput.getAttributesManager().getSessionAttributes();
        boolean shouldEndSession = false;

        if (!currentSessionAttributes.containsKey(PREVIOUS_EXPERIENCE_SESSION_KEY)) {
            speechText = "Sorry, you don't have any experience to share.";
        } else if (intentRequest.getIntent().getSlots() != null
            && intentRequest.getIntent().getSlots().get("user") != null
            && intentRequest.getIntent().getSlots().get("user").getValue() != null) {
            final String targetUserName = intentRequest.getIntent().getSlots().get("user").getValue();

            DynamoDbHelper dbHelper = new DynamoDbHelper();
            if(!dbHelper.userExists(targetUserName)){
                speechText = TARGET_USER_DOES_NOT_EXIST + targetUserName + ". " + TARGET_USER_NAME_ELICITATION;
            } else{
                speechText = MESSAGE_SHARE_COMPLETE;
                shouldEndSession = true;
                dbHelper.saveMessageToDDB(currentSessionAttributes.get(USER_NAME_SESSION_KEY).toString(), targetUserName,
                        currentSessionAttributes.get(PREVIOUS_EXPERIENCE_SESSION_KEY).toString());
            }


        } else {
            speechText = TARGET_USER_NAME_ELICITATION;
        }

        return handlerInput.getResponseBuilder()
                           .withSpeech(speechText)
                           .withReprompt(speechText)
                           .withShouldEndSession(shouldEndSession)
                           .build();
    }
}
