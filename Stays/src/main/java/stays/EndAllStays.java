package stays;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPEvent;
import utils.dto.ResponseDto;
import utils.entity.StaysTable;
import utils.utils.CognitoUtils;

import java.util.List;
import java.util.stream.Collectors;

import static utils.entity.SdkObjects.DYNAMO_DB_MAPPER;
import static utils.utils.LocalDateTimeUtils.getCurrentTime;

public class EndAllStays implements RequestHandler<APIGatewayV2HTTPEvent, ResponseDto> {

    @Override
    public ResponseDto handleRequest(APIGatewayV2HTTPEvent apiGatewayV2HTTPEvent, Context context) {
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("attribute_not_exists(endTime)");

        List<String> collect = DYNAMO_DB_MAPPER.scan(StaysTable.class, scanExpression).stream().map(this::endStay).collect(Collectors.toList());

        return new ResponseDto("Zbogom " + String.join(", ", collect));

    }

    private String endStay(StaysTable staysTable) {
        staysTable.setEndTime(getCurrentTime());
        staysTable.setPrice();
        DYNAMO_DB_MAPPER.save(staysTable);
        return CognitoUtils.getUserById(staysTable.getUserId()).getUsername();
    }


}
