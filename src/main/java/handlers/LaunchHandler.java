package handlers;

import static util.SkillData.FACT_STRINGS;
import static util.SkillKeys.DELIVER_MESSAGE_PROMPT;
import static util.SkillKeys.DO_YOU_WANT_TO_SHARE_PROMPT;
import static util.SkillKeys.UNKNOWN_USER_PROMPT;
import static util.SkillKeys.UNKNOWN_USER_RE_PROMPT;
import static util.SkillKeys.USER_MESSAGE_FACT_ID_SESSION_KEY;
import static util.SkillKeys.USER_MESSAGE_FROM_SESSION_KEY;
import static util.SkillKeys.USER_NAME_SESSION_KEY;
import static util.SkillData.HELLO_STRINGS;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.impl.LaunchRequestHandler;
import com.amazon.ask.model.LaunchRequest;
import com.amazon.ask.model.Response;

import java.util.Map;
import java.util.Optional;

import com.fasterxml.jackson.databind.JsonNode;

import util.RandomEntitySelector;

public class LaunchHandler implements LaunchRequestHandler {

    @Override
    public boolean canHandle(HandlerInput handlerInput, LaunchRequest launchRequest) {
        return true;
    }

    @Override
    public Optional<Response> handle(HandlerInput handlerInput, LaunchRequest launchRequest) {

        String speechText;
        String rePromptText;
        boolean shouldEndSession;

        System.out.println("inside LaunchHandler");



        System.out.println("aaaaa");

        final Map<String, Object> sessionAttributes = handlerInput.getRequestEnvelope().getSession().getAttributes();
        if (!sessionAttributes.containsKey(USER_NAME_SESSION_KEY) ||
            sessionAttributes.get(USER_NAME_SESSION_KEY).toString().equals("UNKNOWN") ) {
            // need to register user

            System.out.println("bbbb");


            speechText = RandomEntitySelector.getRandomObject(HELLO_STRINGS) + UNKNOWN_USER_PROMPT;
            rePromptText = UNKNOWN_USER_RE_PROMPT;
            shouldEndSession = false;
        } else {
            // returned user
            JsonNode node = handlerInput.getRequestEnvelopeJson();
            JsonNode request = node.get("request");
            JsonNode payload = request.get("payload");

            if((payload.isNull() || payload.get("isFromCIF").isNull() || !payload.get("isFromCIF").textValue().equals("true")) &&
                    sessionAttributes.containsKey(USER_MESSAGE_FROM_SESSION_KEY)  ){
                // render message
                String messageFrom = sessionAttributes.get(USER_MESSAGE_FROM_SESSION_KEY).toString();
                String message = sessionAttributes.get(USER_MESSAGE_FACT_ID_SESSION_KEY).toString();
                speechText = DELIVER_MESSAGE_PROMPT + messageFrom + ". " + message;
                rePromptText = speechText;
                shouldEndSession = true;
                String customerName = sessionAttributes.get(USER_NAME_SESSION_KEY).toString();
                deleteMessageFromDDB(customerName);
            }else {
                // give facts
                speechText = RandomEntitySelector.getRandomObject(FACT_STRINGS) + ", " + DO_YOU_WANT_TO_SHARE_PROMPT;
                rePromptText = speechText;
                shouldEndSession = false;
            }
        }

        return handlerInput.getResponseBuilder()
                           .withSpeech(speechText)
                           .withReprompt(speechText)
                           .withShouldEndSession(shouldEndSession)
                           .build();
    }

    // customerName: name of the message receiver
    private void deleteMessageFromDDB(String customerName){
        // TODO
    }
}