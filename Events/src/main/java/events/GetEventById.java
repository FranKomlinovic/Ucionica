package events;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPResponse;
import events.dto.EventDto;
import software.amazon.awssdk.utils.StringUtils;
import utils.entity.EventTable;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static utils.entity.SdkObjects.DYNAMO_DB_MAPPER;
import static utils.utils.ApiGatewayUtils.createSuccessResponse;
import static utils.utils.ApiGatewayUtils.getPathParameter;
import static utils.utils.LocalDateTimeUtils.getCurrentTime;

public class GetEventById implements RequestHandler<APIGatewayV2HTTPEvent, APIGatewayV2HTTPResponse> {

    @Override
    public APIGatewayV2HTTPResponse handleRequest(APIGatewayV2HTTPEvent event, Context context) {
        String id = getPathParameter(event, "id");

        if (StringUtils.isEmpty(id)) {
            return null;
        }

        HashMap<String, AttributeValue> eav = new HashMap<>();
        eav.put(":v1", new AttributeValue().withS(id));

        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("contains(userList, :v1) or attribute_not_exists(userList)")
                .withExpressionAttributeValues(eav);

        List<EventDto> collect = DYNAMO_DB_MAPPER.scan(EventTable.class, scanExpression).stream().filter(a -> a.getEndTime().isAfter(getCurrentTime())).sorted(Comparator.comparing(EventTable::getStartTime)).map(EventDto::new).collect(Collectors.toList());

        return createSuccessResponse(collect);
    }


}
