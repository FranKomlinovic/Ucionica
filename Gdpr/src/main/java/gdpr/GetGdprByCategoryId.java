package gdpr;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPResponse;
import gdpr.dto.GdprTable;
import software.amazon.awssdk.utils.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static utils.entity.SdkObjects.DYNAMO_DB_MAPPER;
import static utils.utils.ApiGatewayUtils.createSuccessResponse;
import static utils.utils.ApiGatewayUtils.getPathParameter;

public class GetGdprByCategoryId implements RequestHandler<APIGatewayV2HTTPEvent, APIGatewayV2HTTPResponse> {

    @Override
    public APIGatewayV2HTTPResponse handleRequest(APIGatewayV2HTTPEvent apiGatewayV2HTTPEvent, Context context) {
        String id = getPathParameter(apiGatewayV2HTTPEvent, "id");

        if (StringUtils.isEmpty(id)) {
            return null;
        }
        Map<String, AttributeValue> eav = new HashMap<>();
        eav.put(":companyId", new AttributeValue().withS(id));

        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("companyId = :companyId").withExpressionAttributeValues(eav);

        return createSuccessResponse(new ArrayList<>(DYNAMO_DB_MAPPER.scan(GdprTable.class, scanExpression)));

    }

}
