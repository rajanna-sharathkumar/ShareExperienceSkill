package interceptors;

import static util.SkillKeys.USER_MESSAGE_FACT_ID_SESSION_KEY;
import static util.SkillKeys.USER_MESSAGE_FROM_SESSION_KEY;
import static util.SkillKeys.USER_NAME_SESSION_KEY;

import java.util.Map;
import java.util.Optional;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.interceptor.RequestInterceptor;

import util.FactMessage;

public class GetUserMessageIntereceptor implements RequestInterceptor {

    @Override
    public void process(HandlerInput input) {
        Map<String, Object> currentSessionAttributes = input.getAttributesManager().getSessionAttributes();

        if (currentSessionAttributes.containsKey(USER_NAME_SESSION_KEY)) {
            System.out.println("nnnnn user name is there");
            String cName = currentSessionAttributes.get(USER_NAME_SESSION_KEY).toString();
            Optional<FactMessage> message = getMessageForUser(cName);
            if(message.isPresent()){
                currentSessionAttributes.put(USER_MESSAGE_FACT_ID_SESSION_KEY, message.get());
                currentSessionAttributes.put(USER_MESSAGE_FROM_SESSION_KEY, message.get());
            }
        }
    }

    private Optional<FactMessage> getMessageForUser(String userName){
        // TODO
        return Optional.empty();
    }
}
