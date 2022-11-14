package events;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPResponse;
import software.amazon.awssdk.utils.StringUtils;
import utils.dto.ResponseDto;
import utils.entity.EventTable;

import static utils.entity.SdkObjects.DYNAMO_DB_MAPPER;
import static utils.utils.ApiGatewayUtils.createSuccessResponse;
import static utils.utils.ApiGatewayUtils.getPathParameter;

public class DeleteEvent implements RequestHandler<APIGatewayV2HTTPEvent, APIGatewayV2HTTPResponse> {

    @Override
    public APIGatewayV2HTTPResponse handleRequest(APIGatewayV2HTTPEvent event, Context context) {
        String id = getPathParameter(event, "id");
        if (StringUtils.isEmpty(id)) {
            return null;
        }
        EventTable load = DYNAMO_DB_MAPPER.load(EventTable.class, id);
        DYNAMO_DB_MAPPER.delete(load);

        return createSuccessResponse(new ResponseDto("Uspješno ste obrisali event"));
    }


}
