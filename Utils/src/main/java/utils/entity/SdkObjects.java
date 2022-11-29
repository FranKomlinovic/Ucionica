package utils.entity;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SdkObjects {
    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create();
    public static final CognitoIdentityProviderClient COGNITO_IDENTITY_PROVIDER = CognitoIdentityProviderClient.create();
    public static final DynamoDBMapper DYNAMO_DB_MAPPER = new DynamoDBMapper(AmazonDynamoDBClientBuilder.standard().build());
}
