package handlers;

import static util.SkillData.FACT_STRINGS;
import static util.SkillKeys.DO_YOU_WANT_TO_SHARE_PROMPT;
import static util.SkillKeys.UNKNOWN_USER_PROMPT;
import static util.SkillKeys.UNKNOWN_USER_RE_PROMPT;
import static util.SkillKeys.USER_NAME_SESSION_KEY;
import static util.SkillData.HELLO_STRINGS;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.impl.LaunchRequestHandler;
import com.amazon.ask.model.LaunchRequest;
import com.amazon.ask.model.Response;

import java.util.Map;
import java.util.Optional;

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

        final Map<String, Object> sessionAttributes = handlerInput.getRequestEnvelope().getSession().getAttributes();
        if (!sessionAttributes.containsKey(USER_NAME_SESSION_KEY) ||
            sessionAttributes.get(USER_NAME_SESSION_KEY).toString().equals("UNKNOWN") ) {
            speechText = RandomEntitySelector.getRandomObject(HELLO_STRINGS) + UNKNOWN_USER_PROMPT;
            rePromptText = UNKNOWN_USER_RE_PROMPT;
        } else {
            speechText = RandomEntitySelector.getRandomObject(FACT_STRINGS) + ", " + DO_YOU_WANT_TO_SHARE_PROMPT;
            rePromptText = DO_YOU_WANT_TO_SHARE_PROMPT;
        }

        return handlerInput.getResponseBuilder()
                           .withSpeech(speechText)
                           .withReprompt(speechText)
                           .build();
    }
}