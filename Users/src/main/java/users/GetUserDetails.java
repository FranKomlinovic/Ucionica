package users;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPResponse;
import software.amazon.awssdk.utils.StringUtils;
import users.dto.PaymentDto;
import users.dto.StayDto;
import users.dto.UserDetailDto;
import utils.dto.UserDto;
import utils.entity.PaymentTable;
import utils.entity.StaysTable;
import utils.entity.UserBalance;
import utils.utils.CognitoUtils;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static utils.entity.SdkObjects.DYNAMO_DB_MAPPER;
import static utils.utils.ApiGatewayUtils.createSuccessResponse;
import static utils.utils.ApiGatewayUtils.getPathParameter;
import static utils.utils.EuroUtils.convertBigDecimalToString;

public class GetUserDetails implements RequestHandler<APIGatewayV2HTTPEvent, APIGatewayV2HTTPResponse> {

    private static final String USER_ID = ":userId";
    @Override
    public APIGatewayV2HTTPResponse handleRequest(APIGatewayV2HTTPEvent apiGatewayV2HTTPEvent, Context context) {
        String id = getPathParameter(apiGatewayV2HTTPEvent, "id");
        if (StringUtils.isEmpty(id)) {
            return null;
        }
        UserDto userById = CognitoUtils.getUserById(id);
        UserDetailDto userDetailDto = new UserDetailDto();
        if (userById != null) {
            userDetailDto.setUsername(userById.getUsername());
            userDetailDto.setId(userById.getId());
            userDetailDto.setPicture(userById.getPicture());
        }

        userDetailDto.setBalance(convertBigDecimalToString(DYNAMO_DB_MAPPER.load(UserBalance.class, id).getBalance()));
        userDetailDto.setCurrentlyActive(isCurrentlyActive(id));
        userDetailDto.setStays(getAllStays(id));
        userDetailDto.setPayments(getAllPayments(id));

        return createSuccessResponse(userDetailDto);
    }

    private Boolean isCurrentlyActive(String userId) {
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("userId = :userId and attribute_not_exists(endTime)").withExpressionAttributeValues(Map.of(USER_ID, new AttributeValue().withS(userId)));

        return !DYNAMO_DB_MAPPER.scan(StaysTable.class, scanExpression).isEmpty();
    }

    private List<StayDto> getAllStays(String userId) {
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("userId = :userId and attribute_exists(endTime)").withExpressionAttributeValues(Map.of(USER_ID, new AttributeValue().withS(userId)));

        return DYNAMO_DB_MAPPER.scan(StaysTable.class, scanExpression).stream().sorted(Comparator.comparing(StaysTable::getStartTime).reversed()).map(StayDto::new).collect(Collectors.toList());
    }

    private List<PaymentDto> getAllPayments(String userId) {
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("userId = :userId").withExpressionAttributeValues(Map.of(USER_ID, new AttributeValue().withS(userId)));

        return DYNAMO_DB_MAPPER.scan(PaymentTable.class, scanExpression).stream().sorted(Comparator.comparing(PaymentTable::getTime).reversed()).map(PaymentDto::new).collect(Collectors.toList());


    }
}
