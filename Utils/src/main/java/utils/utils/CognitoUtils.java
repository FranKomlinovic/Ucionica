package utils.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AdminGetUserRequest;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AdminGetUserResponse;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AttributeType;
import software.amazon.awssdk.services.cognitoidentityprovider.model.CognitoIdentityProviderException;
import software.amazon.awssdk.services.cognitoidentityprovider.model.ListUsersRequest;
import software.amazon.awssdk.services.cognitoidentityprovider.model.UserType;
import utils.dto.UserDto;
import utils.entity.SdkObjects;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.amazonaws.util.StringUtils.isNullOrEmpty;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CognitoUtils {

    private static final String USER_POOL_ID = "eu-central-1_zpNOTpJzD";
    private static final String PICTURE = "https://i1.sndcdn.com/artworks-000420504324-qbn3os-t500x500.jpg";
    private static final String NOT_FOUND = "https://img.icons8.com/ios/500/user-not-found.png";

    public static UserDto getUserById(String userId) {
        try {
            AdminGetUserRequest userRequest = AdminGetUserRequest.builder()
                    .username(userId)
                    .userPoolId(USER_POOL_ID)
                    .build();

            AdminGetUserResponse response = SdkObjects.COGNITO_IDENTITY_PROVIDER.adminGetUser(userRequest);
            return new UserDto(userId, getName(response.userAttributes()), getPicture(response.userAttributes()));

        } catch (CognitoIdentityProviderException e) {
            e.printStackTrace();
            return new UserDto(userId, userId, NOT_FOUND);
        }

    }

    public static List<UserDto> getAllUsers() {
        try {
            ListUsersRequest userRequest = ListUsersRequest.builder()
                    .userPoolId(USER_POOL_ID)
                    .build();

            return SdkObjects.COGNITO_IDENTITY_PROVIDER.listUsers(userRequest).users().stream().map(CognitoUtils::convertToUser).collect(Collectors.toList());

        } catch (CognitoIdentityProviderException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static UserDto convertToUser(UserType userType) {
        return new UserDto(userType.username(), getName(userType.attributes()), getPicture(userType.attributes()));
    }

    private static String getName(List<AttributeType> userAttributes) {
        String nickname = getAttributeValue(userAttributes, "nickname");
        String email = getAttributeValue(userAttributes, "email");
        String givenName = getAttributeValue(userAttributes, "given_name");
        if (nickname.isBlank()) {
            if (givenName.isBlank()) {
                return email;
            }
            nickname = getAttributeValue(userAttributes, "given_name") + " " + getAttributeValue(userAttributes, "family_name");
        }
        return nickname;
    }

    private static String getPicture(List<AttributeType> userAttributes) {
        String picture = getAttributeValue(userAttributes, "picture");
        if (isNullOrEmpty(picture)) {
            picture = PICTURE;
        }
        return picture;

    }

    private static String getAttributeValue(List<AttributeType> attributes, String field) {
        Optional<AttributeType> first = attributes.stream().filter(a -> a.name().equals(field)).findFirst();
        return first.map(AttributeType::value).orElse("");
    }
}
