package handlers;

import static util.SkillData.GOODBYE_STRINGS;

import java.util.Optional;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;

import util.RandomEntitySelector;

public class CancelIntentHandler implements IntentRequestHandler {
    @Override
    public boolean canHandle(HandlerInput handlerInput, IntentRequest intentRequest) {
        return intentRequest.getIntent().getName().equals("AMAZON.CancelIntent") ||
            intentRequest.getIntent().getName().equals("AMAZON.StopIntent");
    }

    @Override
    public Optional<Response> handle(HandlerInput handlerInput, IntentRequest intentRequest) {
        final String speechText = RandomEntitySelector.getRandomObject(GOODBYE_STRINGS);
        return handlerInput.getResponseBuilder()
                           .withSpeech(speechText)
                           .build();
    }
}
