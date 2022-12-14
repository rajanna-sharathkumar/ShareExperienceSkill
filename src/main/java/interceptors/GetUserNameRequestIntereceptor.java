package interceptors;

import static util.SkillKeys.USER_NAME_SESSION_KEY;

import java.util.Map;
import java.util.Optional;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.interceptor.RequestInterceptor;

import com.fasterxml.jackson.databind.JsonNode;

public class GetUserNameRequestIntereceptor implements RequestInterceptor {

    @Override
    public void process(HandlerInput input) {
        Map<String, Object> currentSessionAttributes = input.getAttributesManager().getSessionAttributes();

        if (!currentSessionAttributes.containsKey(USER_NAME_SESSION_KEY)) {
            JsonNode node = input.getRequestEnvelopeJson();
            String cid = node.get("session").get("user").get("userId").textValue();
            Optional<String> cName = getCustomerNameFromDDB(cid);
            if(cName.isPresent()){
                currentSessionAttributes.put(USER_NAME_SESSION_KEY, cName.get());
            } else {
                currentSessionAttributes.put(USER_NAME_SESSION_KEY, "UNKNOWN");
            }

        }
    }

    private Optional<String> getCustomerNameFromDDB(String cid){
        // TODO
        return Optional.empty();
    }
}
