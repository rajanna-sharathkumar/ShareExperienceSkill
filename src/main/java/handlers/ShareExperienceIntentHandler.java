package handlers;

import java.util.Map;
import java.util.Optional;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;

public class ShareExperienceIntentHandler implements IntentRequestHandler {
    @Override
    public boolean canHandle(HandlerInput handlerInput, IntentRequest intentRequest) {
        System.out.println("Slots: " + intentRequest.getIntent().getSlots());
        System.out.println("Session Attributes: " +handlerInput.getRequestEnvelope().getSession().getAttributes());
        return intentRequest.getIntent().getName().equals("ShareExperienceToUserIntent");
    }

    @Override
    public Optional<Response> handle(HandlerInput handlerInput, IntentRequest intentRequest) {

        Map<String, Object> currentSessionAttributes = handlerInput.getAttributesManager().getSessionAttributes();

        if (currentSessionAttributes.size() == 0) {
            currentSessionAttributes.put("Count", 1);
        } else {
            currentSessionAttributes.put("Count", (int)currentSessionAttributes.get("Count") + 1);
        }

        String speechText;
        if (intentRequest.getIntent().getSlots() != null
            && intentRequest.getIntent().getSlots().get("user") != null
            && intentRequest.getIntent().getSlots().get("user").getValue() != null) {
            final String userName = intentRequest.getIntent().getSlots().get("user").getValue();
            speechText = "Sorry, this feature is not available yet! Stay tuned to share the experience with " + userName;
        } else {
            speechText = "With whom do you wanna share this experience?";
        }

        return handlerInput.getResponseBuilder()
            .withSpeech()
                           .withSpeech(speechText)
                           .withReprompt(speechText)
                           .build();
    }
}
