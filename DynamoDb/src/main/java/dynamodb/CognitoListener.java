package dynamodb;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.CognitoUserPoolPreSignUpEvent;
import utils.entity.UserBalance;

import java.math.BigDecimal;

import static utils.entity.SdkObjects.DYNAMO_DB_MAPPER;

public class CognitoListener implements RequestHandler<CognitoUserPoolPreSignUpEvent, CognitoUserPoolPreSignUpEvent> {

    @Override
    public CognitoUserPoolPreSignUpEvent handleRequest(CognitoUserPoolPreSignUpEvent cognitoEvent, Context context) {
        DYNAMO_DB_MAPPER.save(new UserBalance(cognitoEvent.getUserName(), BigDecimal.ZERO));
        return cognitoEvent;
    }
}
