package handlers;

import static util.SkillData.FACT_STRINGS;
import static util.SkillKeys.DO_YOU_WANT_TO_SHARE_PROMPT;
import static util.SkillKeys.PREVIOUS_EXPERIENCE_SESSION_KEY;
import static util.SkillKeys.PREVIOUS_INTENT_SESSION_KEY;
import static util.SkillKeys.SAVE_USER_NAME_INTENT;
import static util.SkillKeys.YES_INTENT;
import static util.SkillKeys.YOU_CAN_SHARE_PROMPT;

import java.util.Map;
import java.util.Optional;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;

import util.RandomEntitySelector;

public class YesIntentHandler implements IntentRequestHandler {
    @Override
    public boolean canHandle(HandlerInput handlerInput, IntentRequest intentRequest) {
        return intentRequest.getIntent().getName().equals("AMAZON.YesIntent");
    }

    @Override
    public Optional<Response> handle(HandlerInput handlerInput, IntentRequest intentRequest) {
        final Map<String, Object> currentSessionAttributes = handlerInput.getAttributesManager().getSessionAttributes();
        String speechText;

        if (currentSessionAttributes.containsKey(PREVIOUS_INTENT_SESSION_KEY)) {
            switch (currentSessionAttributes.get(PREVIOUS_INTENT_SESSION_KEY).toString()) {
                case SAVE_USER_NAME_INTENT:
                    speechText = RandomEntitySelector.getRandomObject(FACT_STRINGS);
                    currentSessionAttributes.put(PREVIOUS_EXPERIENCE_SESSION_KEY, speechText);
                    speechText = speechText + ", " + YOU_CAN_SHARE_PROMPT;
                    break;
                default: speechText = "Sorry, this feature is not available yet! Stay tuned for updates";
            }
        } else {
            speechText = "Sorry, this feature is not available yet! Stay tuned for updates";
        }
        return handlerInput.getResponseBuilder()
                           .withSpeech(speechText)
                           .withReprompt(speechText)
                           .build();
    }
}
