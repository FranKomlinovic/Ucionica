package users;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPResponse;
import lombok.Getter;
import lombok.Setter;
import software.amazon.awssdk.utils.StringUtils;
import users.dto.PaymentDto;
import users.dto.StayDto;
import users.dto.UserDetailDto;
import utils.dto.UserDto;
import utils.entity.PaymentTable;
import utils.entity.StaysTable;
import utils.entity.UserBalance;
import utils.utils.CognitoUtils;

import java.math.BigDecimal;
import java.time.Duration;
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
        userDetailDto.setUsername(userById.getUsername());
        userDetailDto.setId(userById.getId());
        userDetailDto.setPicture(userById.getPicture());

        UserInfo userInfo = new UserInfo();
        userDetailDto.setBalance(convertBigDecimalToString(DYNAMO_DB_MAPPER.load(UserBalance.class, id).getBalance()));
        userDetailDto.setCurrentlyActive(isCurrentlyActive(id));
        userInfo.withStays(getAllStays(id));
        userInfo.withPayments(getAllPayments(id));
        userDetailDto.setStays(userInfo.getAllStays());
        userDetailDto.setPayments(userInfo.getAllPayments());
        userDetailDto.setTotalStayPrice(userInfo.getTotalStayPrice());
        userDetailDto.setAverageStayPrice(userInfo.getAverageStayPrice());
        userDetailDto.setTotalPayments(userInfo.getTotalPayments());
        userDetailDto.setTimeSpent(userInfo.getTimeSpent());
        userDetailDto.setAverageTimeSpent(userInfo.getAverageTimeSpent());

        return createSuccessResponse(userDetailDto);
    }

    private Boolean isCurrentlyActive(String userId) {
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("userId = :userId and attribute_not_exists(endTime)").withExpressionAttributeValues(Map.of(USER_ID, new AttributeValue().withS(userId)));

        return !DYNAMO_DB_MAPPER.scan(StaysTable.class, scanExpression).isEmpty();
    }

    private List<StaysTable> getAllStays(String userId) {
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("userId = :userId and attribute_exists(endTime)").withExpressionAttributeValues(Map.of(USER_ID, new AttributeValue().withS(userId)));

        return DYNAMO_DB_MAPPER.scan(StaysTable.class, scanExpression).stream().sorted(Comparator.comparing(StaysTable::getStartTime).reversed()).collect(Collectors.toList());
    }

    private List<PaymentTable> getAllPayments(String userId) {
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("userId = :userId").withExpressionAttributeValues(Map.of(USER_ID, new AttributeValue().withS(userId)));

        return DYNAMO_DB_MAPPER.scan(PaymentTable.class, scanExpression).stream().sorted(Comparator.comparing(PaymentTable::getTime).reversed()).collect(Collectors.toList());


    }
}

@Getter
@Setter
class UserInfo {
    private List<StayDto> allStays;
    private List<PaymentDto> allPayments;
    private String timeSpent;
    private String averageTimeSpent;
    private String totalPayments;
    private String totalStayPrice;
    private String averageStayPrice;

    public UserInfo withStays(List<StaysTable> stayTables) {
        allStays = stayTables.stream().map(StayDto::new).collect(Collectors.toList());
        int numberOfStays = allStays.size();
        long totalMinutes = stayTables.stream().mapToLong(a -> Duration.between(a.getStartTime(), a.getEndTime()).toMinutes()).sum();
        Duration averageTime = Duration.ofMinutes(totalMinutes / numberOfStays);
        Duration duration = Duration.ofMinutes(totalMinutes);
        timeSpent = duration.toDaysPart() + " dana, " + duration.toHoursPart() + " sati i " + duration.toMinutesPart() + " minuta";
        averageTimeSpent = averageTime.toHoursPart() + " sata i " + averageTime.toMinutesPart() + " minuta";

        double totalPrice = stayTables.stream().mapToDouble(a -> a.getPrice().doubleValue()).sum();
        totalStayPrice = convertBigDecimalToString(BigDecimal.valueOf(totalPrice));
        averageStayPrice = convertBigDecimalToString(BigDecimal.valueOf(totalPrice/numberOfStays));

        return this;
    }

    public UserInfo withPayments(List<PaymentTable> payments) {
        allPayments = payments.stream().map(PaymentDto::new).collect(Collectors.toList());
        totalPayments = convertBigDecimalToString(BigDecimal.valueOf(payments.stream().mapToDouble(a -> a.getAmount().doubleValue()).sum()));
        return this;
    }
}