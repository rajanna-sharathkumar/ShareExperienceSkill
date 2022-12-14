package handlers;

import static util.SkillKeys.PREVIOUS_EXPERIENCE_SESSION_KEY;
import static util.SkillKeys.SHARE_EXPERIENCE_INTENT;

import java.util.Map;
import java.util.Optional;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;

public class ShareExperienceIntentHandler implements IntentRequestHandler {
    @Override
    public boolean canHandle(HandlerInput handlerInput, IntentRequest intentRequest) {
        return intentRequest.getIntent().getName().equals(SHARE_EXPERIENCE_INTENT);
    }

    @Override
    public Optional<Response> handle(HandlerInput handlerInput, IntentRequest intentRequest) {

        String speechText;
        Map<String, Object> currentSessionAttributes = handlerInput.getAttributesManager().getSessionAttributes();


        if (!currentSessionAttributes.containsKey(PREVIOUS_EXPERIENCE_SESSION_KEY)) {
            speechText = "Sorry, you don't have any experience to share.";
        } else if (intentRequest.getIntent().getSlots() != null
            && intentRequest.getIntent().getSlots().get("user") != null
            && intentRequest.getIntent().getSlots().get("user").getValue() != null) {
            final String targetUserName = intentRequest.getIntent().getSlots().get("user").getValue();
            speechText = String.format("I would like to share the experience with %s, But this feature is not available yet!", targetUserName);
        } else {
            speechText = "With whom do you wanna share this experience?";
        }

        return handlerInput.getResponseBuilder()
                           .withSpeech(speechText)
                           .withReprompt(speechText)
                           .build();
    }
}
