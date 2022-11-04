package stays;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import stays.dto.CurrentStayDto;
import utils.entity.StaysTable;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static utils.entity.SdkObjects.DYNAMO_DB_MAPPER;

public class GetCurrentStays implements RequestHandler<APIGatewayProxyRequestEvent, List<CurrentStayDto>> {

    @Override
    public List<CurrentStayDto> handleRequest(APIGatewayProxyRequestEvent apiGatewayV2HTTPEvent, Context context) {
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("attribute_not_exists(endTime)");

        return DYNAMO_DB_MAPPER.scan(StaysTable.class, scanExpression).stream().sorted(Comparator.comparing(StaysTable::getStartTime).reversed()).map(CurrentStayDto::new).collect(Collectors.toList());
    }


}
