package interceptors;

import static util.SkillKeys.LAUNCH_REQUEST_INTENT;
import static util.SkillKeys.PREVIOUS_INTENT_SESSION_KEY;

import java.util.Map;
import java.util.Optional;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.interceptor.ResponseInterceptor;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;

public class SavePreviousIntentInterceptor implements ResponseInterceptor {

    @Override
    public void process(HandlerInput input, Optional<Response> response) {
        Map<String, Object> currentSessionAttributes = input.getAttributesManager().getSessionAttributes();
        System.out.println("Clazz type: " + input.getRequest().getClass().getName());
        if (input.getRequest().getClass().getName().equals("com.amazon.ask.model.LaunchRequest")) {
            currentSessionAttributes.put(PREVIOUS_INTENT_SESSION_KEY, LAUNCH_REQUEST_INTENT);
        } else {
            final IntentRequest intentRequest = (IntentRequest)input.getRequest();
            currentSessionAttributes.put(PREVIOUS_INTENT_SESSION_KEY, intentRequest.getIntent().getName());
        }
    }
}
