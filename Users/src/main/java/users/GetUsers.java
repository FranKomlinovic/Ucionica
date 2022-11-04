package users;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPEvent;
import utils.dto.UserDto;
import utils.utils.CognitoUtils;

import java.util.List;

public class GetUsers implements RequestHandler<APIGatewayV2HTTPEvent, List<UserDto>> {

    @Override
    public List<UserDto> handleRequest(APIGatewayV2HTTPEvent apiGatewayV2HTTPEvent, Context context) {
        return CognitoUtils.getAllUsers();
    }
}
