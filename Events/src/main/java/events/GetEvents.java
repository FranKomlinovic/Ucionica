package events;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPEvent;
import events.dto.EventDto;
import utils.entity.EventTable;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static utils.entity.SdkObjects.DYNAMO_DB_MAPPER;

public class GetEvents implements RequestHandler<APIGatewayV2HTTPEvent, List<EventDto>> {

    @Override
    public List<EventDto> handleRequest(APIGatewayV2HTTPEvent apiGatewayV2HTTPEvent, Context context) {
        return DYNAMO_DB_MAPPER.scan(EventTable.class, new DynamoDBScanExpression()).stream().sorted(Comparator.comparing(EventTable::getStartTime).reversed()).map(EventDto::new).collect(Collectors.toList());
    }

}
