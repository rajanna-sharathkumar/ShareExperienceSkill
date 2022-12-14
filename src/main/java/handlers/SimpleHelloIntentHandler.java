package handlers;

import java.util.Optional;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;

public class SimpleHelloIntentHandler implements IntentRequestHandler {

    @Override
    public boolean canHandle(HandlerInput handlerInput, IntentRequest intentRequest) {
        return intentRequest.getIntent().getName().equals("HelloWorldIntent") &&
            intentRequest.getIntent().getSlots() != null;
    }

    @Override
    public Optional<Response> handle(HandlerInput handlerInput, IntentRequest intentRequest) {
        final String speechText = intentRequest.getIntent().getSlots().get("userQuestion").getValue();
        System.out.println("Customer Request: " + speechText);
        final String response = "DUMMY RESPONSE";
        System.out.println("ChatGPT Response: " + response);
        return handlerInput.getResponseBuilder()
                           .withSpeech(response)
                           .withReprompt(speechText)
                           .build();
    }

}