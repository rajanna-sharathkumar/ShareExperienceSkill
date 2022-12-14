package util;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.DeleteItemRequest;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;
import software.amazon.awssdk.services.dynamodb.model.ScanRequest;
import software.amazon.awssdk.services.dynamodb.model.ScanResponse;

// https://github.com/awsdocs/aws-doc-sdk-examples/blob/main/javav2/example_code/dynamodb/src/main/java/com/example/dynamodb/
public class DynamoDbHelper {
    DynamoDbClient ddb;
    final String ShareExperienceSkillMessagesTable = "ShareExperienceSkillMessages";
    final String ShareExperienceSkillMessagesTableKey = "recipientName";
    final String ShareExperienceSkillMessagesTableAttr1 = "senderName";
    final String ShareExperienceSkillMessagesTableAttr2 = "message";
    final String ShareExperienceSkillUserNamesTable = "ShareExperienceSkillUserNames";
    final String ShareExperienceSkillUserNamesTableKey = "userID";
    final String ShareExperienceSkillUserNamesTableAttr = "userName";

    public DynamoDbHelper() {
        Region region = Region.US_EAST_1;
        ddb = DynamoDbClient.builder()
                .region(region)
                .build();
    }

    public Optional<String> getCustomerNameFromDDB(String cid){
        System.out.println("[DbHelper] getting name for " + cid);

        HashMap<String, AttributeValue> keyToGet = new HashMap<>();
        keyToGet.put(ShareExperienceSkillUserNamesTableKey, AttributeValue.builder().s(cid).build());

        GetItemRequest request = GetItemRequest.builder()
                .key(keyToGet)
                .tableName(ShareExperienceSkillUserNamesTable)
                .build();

        Map<String, AttributeValue> returnedItem = ddb.getItem(request).item();
        if (returnedItem == null || returnedItem.isEmpty()) {
            System.out.println("[DbHelper] cannot find name for " + cid);
            return Optional.empty();
        } else {
            String userName = returnedItem.get(ShareExperienceSkillUserNamesTableAttr).s();
            System.out.println("[DbHelper] successfully retrieved name " + userName + " for " + cid);
            return Optional.of(userName);
        }
    }

    // customerName: name of the message receiver
    public void deleteMessageFromDDB(String customerName){
        System.out.println("[DbHelper] deleting message for " + customerName);

        HashMap<String, AttributeValue> keyToDelete = new HashMap<>();
        keyToDelete.put(ShareExperienceSkillMessagesTableKey, AttributeValue.builder().s(customerName).build());

        DeleteItemRequest request = DeleteItemRequest.builder()
                .key(keyToDelete)
                .tableName(ShareExperienceSkillMessagesTable)
                .build();

        ddb.deleteItem(request);
        System.out.println("[DbHelper] successfully deleted message for " + customerName);
    }

    public void saveUserNameToDDB(String cid, String name){
        System.out.println("[DbHelper] saving cid " + cid + " with name " + name);

        HashMap<String, AttributeValue> itemValues = new HashMap<>();
        itemValues.put(ShareExperienceSkillUserNamesTableKey, AttributeValue.builder().s(cid).build());
        itemValues.put(ShareExperienceSkillUserNamesTableAttr, AttributeValue.builder().s(name).build());

        PutItemRequest request = PutItemRequest.builder()
                .tableName(ShareExperienceSkillUserNamesTable)
                .item(itemValues)
                .build();

        ddb.putItem(request);

        System.out.println("[DbHelper] successfully saved cid " + cid + " with name " + name);
    }

    public void saveMessageToDDB(String messageFromName, String messageToName, String message){
        System.out.println("[DbHelper] saving message from " + messageFromName + " to " + messageToName);

        HashMap<String, AttributeValue> itemValues = new HashMap<>();
        itemValues.put(ShareExperienceSkillMessagesTableKey, AttributeValue.builder().s(messageToName).build());
        itemValues.put(ShareExperienceSkillMessagesTableAttr1, AttributeValue.builder().s(messageFromName).build());
        // message content e.g. factID to be stored
        itemValues.put(ShareExperienceSkillMessagesTableAttr2, AttributeValue.builder().s(message).build());

        PutItemRequest request = PutItemRequest.builder()
                .tableName(ShareExperienceSkillMessagesTable)
                .item(itemValues)
                .build();

        ddb.putItem(request);

        System.out.println("[DbHelper] successfully saved message from " + messageFromName + " to " + messageToName);
    }

    public boolean userExists(String userName){
        System.out.println("[DbHelper] scanning table for name " + userName);

        Map<String, AttributeValue> expressionAttributeValues = new HashMap<>();
        expressionAttributeValues.put(":val", AttributeValue.builder().s(userName).build());

        ScanRequest scanRequest = ScanRequest.builder()
                .tableName(ShareExperienceSkillUserNamesTable)
                .filterExpression(ShareExperienceSkillUserNamesTableAttr + " = :val")
                .expressionAttributeValues(expressionAttributeValues)
                .build();

        ScanResponse scanResult = ddb.scan(scanRequest);

        System.out.println((scanResult.hasItems()? "[DbHelper] found " : "[DbHelper] did not find ") + userName);

        return scanResult.hasItems();
    }

    public Optional<FactMessage> getMessageForUser(String userName){
        System.out.println("[DbHelper] getting message for name " + userName);

        HashMap<String, AttributeValue> keyToGet = new HashMap<>();
        keyToGet.put(ShareExperienceSkillMessagesTableKey, AttributeValue.builder().s(userName).build());

        GetItemRequest request = GetItemRequest.builder()
                .key(keyToGet)
                .tableName(ShareExperienceSkillMessagesTable)
                .build();

        Map<String, AttributeValue> returnedItem = ddb.getItem(request).item();
        if (returnedItem == null || returnedItem.isEmpty()) {
            System.out.println("[DbHelper] cannot find message for name " + userName);
            return Optional.empty();
        } else {
            String sender = returnedItem.get(ShareExperienceSkillMessagesTableAttr1).s();
            String message = returnedItem.get(ShareExperienceSkillMessagesTableAttr2).s();
            System.out.println("[DbHelper] successfully retrieved message " + message + " for name " + userName +
                    " from " + sender);
            return Optional.of(new FactMessage(sender, userName, message));
        }
    }
}