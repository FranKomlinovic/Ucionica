package stays;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import stays.dto.CreateStayDto;
import utils.dto.ResponseDto;
import utils.dto.UserDto;
import utils.entity.StaysTable;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import static utils.entity.SdkObjects.DYNAMO_DB_MAPPER;
import static utils.utils.CognitoUtils.getUserById;
import static utils.utils.LocalDateTimeUtils.getCurrentTime;

public class EndStay implements RequestHandler<CreateStayDto, ResponseDto> {


    @Override
    public ResponseDto handleRequest(CreateStayDto stayDto, Context context) {
        UserDto user = getUserById(stayDto.getUserId());
        if (user == null) {
            return new ResponseDto("Nije pronađen korisnik");
        }
        LocalDateTime time = getCurrentTime();
        if (stayDto.getTime() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            time = LocalDateTime.parse(stayDto.getTime(), formatter);
        }
        StaysTable existingStay = getByUserIdAndNoDate(stayDto.getUserId());
        if (existingStay != null) {
            existingStay.setEndTime(time);
            existingStay.setPrice();
            DYNAMO_DB_MAPPER.save(existingStay);
            return new ResponseDto("Zbogom " + user.getUsername());
        } else {
            return new ResponseDto("Već ste odjavljeni");
        }
    }

    private StaysTable getByUserIdAndNoDate(String userId) {
        Map<String, AttributeValue> eav = new HashMap<>();
        eav.put(":userId", new AttributeValue(userId).withS(userId));
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("userId = :userId and attribute_not_exists(endTime)").withExpressionAttributeValues(eav);

        return DYNAMO_DB_MAPPER.scan(StaysTable.class, scanExpression).stream().findFirst().orElse(null);
    }


}
