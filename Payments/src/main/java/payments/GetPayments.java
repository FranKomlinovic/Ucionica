package payments;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPEvent;
import payments.dto.PaymentDto;
import utils.entity.PaymentTable;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static utils.entity.SdkObjects.DYNAMO_DB_MAPPER;

public class GetPayments implements RequestHandler<APIGatewayV2HTTPEvent, List<PaymentDto>> {

    @Override
    public List<PaymentDto> handleRequest(APIGatewayV2HTTPEvent apiGatewayV2HTTPEvent, Context context) {
        return DYNAMO_DB_MAPPER.scan(PaymentTable.class, new DynamoDBScanExpression()).stream().sorted(Comparator.comparing(PaymentTable::getTime).reversed()).map(PaymentDto::new).collect(Collectors.toList());
    }

}
