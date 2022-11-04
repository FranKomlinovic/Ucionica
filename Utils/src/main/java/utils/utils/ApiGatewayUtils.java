package utils.utils;

import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Map;

import static utils.entity.SdkObjects.GSON;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiGatewayUtils {

    private static final Map<String, String> HEADERS = Map.of("Access-Control-Allow-Origin", "*");

    public static APIGatewayV2HTTPResponse createSuccessResponse(Object object) {
        return APIGatewayV2HTTPResponse.builder().withStatusCode(200)
                .withHeaders(HEADERS).withBody(GSON.toJson(object)).build();
    }

    public static String getPathParameter(APIGatewayV2HTTPEvent event, String key) {
        return event.getPathParameters().get(key);
    }
}
