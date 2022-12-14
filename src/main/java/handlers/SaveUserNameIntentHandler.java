package handlers;

import static java.lang.String.format;
import static util.SkillKeys.SAVE_USER_NAME_INTENT;
import static util.SkillKeys.SAVE_USER_NAME_PROMPT;
import static util.SkillKeys.SAVE_USER_NAME_RE_PROMPT;
import static util.SkillKeys.USER_NAME_SESSION_KEY;
import static util.SkillKeys.USER_NAME_SLOT_KEY;

import java.util.Map;
import java.util.Optional;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;

import com.fasterxml.jackson.databind.JsonNode;

public class SaveUserNameIntentHandler implements IntentRequestHandler {
    @Override
    public boolean canHandle(HandlerInput handlerInput, IntentRequest intentRequest) {
        System.out.println("Slots: " + intentRequest.getIntent().getSlots());
        System.out.println("Session Attributes: " +handlerInput.getRequestEnvelope().getSession().getAttributes());

        System.out.println("Exp1");
        System.out.println(intentRequest.getIntent().getName().equals(SAVE_USER_NAME_INTENT));

        System.out.println("Exp2");
        System.out.println(handlerInput.getAttributesManager().getSessionAttributes().get(USER_NAME_SESSION_KEY).equals("UNKNOWN"));

        System.out.println("Exp3");
        System.out.println(intentRequest.getIntent().getSlots() != null);

        System.out.println("Exp4");
        System.out.println(intentRequest.getIntent().getSlots().get(USER_NAME_SLOT_KEY).getValue() != null);

        return intentRequest.getIntent().getName().equals(SAVE_USER_NAME_INTENT)
            && handlerInput.getAttributesManager().getSessionAttributes().get(USER_NAME_SESSION_KEY).equals("UNKNOWN")
            && intentRequest.getIntent().getSlots() != null
            && intentRequest.getIntent().getSlots().get(USER_NAME_SLOT_KEY).getValue() != null;
    }

    @Override
    public Optional<Response> handle(HandlerInput handlerInput, IntentRequest intentRequest) {

        final String userName = intentRequest.getIntent().getSlots().get(USER_NAME_SLOT_KEY).getValue();
        Map<String, Object> currentSessionAttributes = handlerInput.getAttributesManager().getSessionAttributes();
        currentSessionAttributes.put(USER_NAME_SESSION_KEY, userName);
        String speechText = format(SAVE_USER_NAME_PROMPT, userName);

        JsonNode node = handlerInput.getRequestEnvelopeJson();
        String cid = node.get("session").get("user").get("userId").textValue();
        saveUserNameToDDB(cid, userName);
        return handlerInput.getResponseBuilder()
                           .withSpeech(speechText)
                           .withReprompt(SAVE_USER_NAME_RE_PROMPT)
                            .withShouldEndSession(false)
                           .build();
    }

    private void saveUserNameToDDB(String cid, String name){
        // TODO
    }
}
