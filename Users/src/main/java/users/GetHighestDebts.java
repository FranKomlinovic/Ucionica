package users;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPEvent;
import users.dto.HighestDebtsDto;
import utils.entity.UserBalance;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static utils.entity.SdkObjects.DYNAMO_DB_MAPPER;

public class GetHighestDebts implements RequestHandler<APIGatewayV2HTTPEvent, List<HighestDebtsDto>> {

    @Override
    public List<HighestDebtsDto> handleRequest(APIGatewayV2HTTPEvent apiGatewayV2HTTPEvent, Context context) {
        return DYNAMO_DB_MAPPER.scan(UserBalance.class, new DynamoDBScanExpression()).stream().filter(a -> a.getBalance().signum() < 0).sorted(Comparator.comparing(UserBalance::getBalance)).map(HighestDebtsDto::new).collect(Collectors.toList());
    }
}
