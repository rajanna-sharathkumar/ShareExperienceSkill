package interceptors;

import static util.SkillKeys.USER_NAME_SESSION_KEY;

import java.util.Map;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.interceptor.RequestInterceptor;

public class GetUserNameRequestIntereceptor implements RequestInterceptor {

    @Override
    public void process(HandlerInput input) {
        Map<String, Object> currentSessionAttributes = input.getAttributesManager().getSessionAttributes();

        if (!currentSessionAttributes.containsKey(USER_NAME_SESSION_KEY)) {
            currentSessionAttributes.put(USER_NAME_SESSION_KEY, "UNKNOWN");
        }
    }
}
