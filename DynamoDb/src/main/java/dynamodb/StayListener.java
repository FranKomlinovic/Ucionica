package dynamodb;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.DynamodbEvent;
import com.amazonaws.services.lambda.runtime.events.models.dynamodb.AttributeValue;
import com.amazonaws.services.lambda.runtime.events.models.dynamodb.StreamRecord;
import utils.entity.UserBalance;

import java.math.BigDecimal;
import java.util.Map;

import static utils.entity.SdkObjects.DYNAMO_DB_MAPPER;

public class StayListener implements RequestHandler<DynamodbEvent, String> {

    @Override
    public String handleRequest(DynamodbEvent dynamodbEvent, Context context) {
        for (DynamodbEvent.DynamodbStreamRecord dynamoRecord : dynamodbEvent.getRecords()) {
            StreamRecord dynamodb = dynamoRecord.getDynamodb();
            doMagic(dynamodb.getNewImage(), true);
            doMagic(dynamodb.getOldImage(), false);
        }
        return null;
    }

    private void doMagic(Map<String, AttributeValue> input, boolean subtract) {
        if (input == null) {
            return;
        }
        if (input.isEmpty()) {
            return;
        }
        if (input.get("price") == null) {
            return;
        }
        BigDecimal amount = new BigDecimal(input.get("price").getN());
        String userId = input.get("userId").getS();
        UserBalance load = DYNAMO_DB_MAPPER.load(UserBalance.class, userId);
        BigDecimal balance;
        if (subtract) {
            balance = load.getBalance().subtract(amount);
        } else {
            balance = load.getBalance().add(amount);
        }
        load.setBalance(balance);
        DYNAMO_DB_MAPPER.save(load);

    }
}
