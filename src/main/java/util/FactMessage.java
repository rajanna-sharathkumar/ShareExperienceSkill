package util;

public class FactMessage {
    String messageFromUserName;
    String messageToUserName;
    String factId;

    public FactMessage(String messageFromUserName, String messageToUserName, String factId){
        this.messageFromUserName = messageFromUserName;
        this.messageToUserName = messageToUserName;
        this.factId = factId;
    }

    public String getFactId() {
        return factId;
    }

    public String getMessageFromUserName() {
        return messageFromUserName;
    }

    public String getMessageToUserName() {
        return messageToUserName;
    }
}
