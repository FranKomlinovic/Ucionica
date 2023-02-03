package gdpr;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPResponse;
import gdpr.dto.GdprTable;
import software.amazon.awssdk.utils.StringUtils;

import static utils.entity.SdkObjects.DYNAMO_DB_MAPPER;
import static utils.utils.ApiGatewayUtils.createSuccessResponse;
import static utils.utils.ApiGatewayUtils.getPathParameter;

public class GetGdprById implements RequestHandler<APIGatewayV2HTTPEvent, APIGatewayV2HTTPResponse> {

    @Override
    public APIGatewayV2HTTPResponse handleRequest(APIGatewayV2HTTPEvent apiGatewayV2HTTPEvent, Context context) {
        String id = getPathParameter(apiGatewayV2HTTPEvent, "id");

        if (StringUtils.isEmpty(id)) {
            return null;
        }

        return createSuccessResponse(DYNAMO_DB_MAPPER.load(GdprTable.class, id));

    }

}
