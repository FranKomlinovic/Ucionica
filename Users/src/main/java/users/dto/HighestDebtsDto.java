package users.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import utils.dto.UserDto;
import utils.entity.UserBalance;

import static utils.utils.CognitoUtils.getUserById;
import static utils.utils.EuroUtils.convertBigDecimalToString;

@Getter
@Setter
@ToString
public class HighestDebtsDto {
    private String userId;
    private String user;
    private String amount;
    private String picture;

    public HighestDebtsDto(UserBalance userBalance) {
        this.amount = convertBigDecimalToString(userBalance.getBalance());
        UserDto userById = getUserById(userBalance.getUserId());
        this.picture = userById.getPicture();
        this.user = userById.getUsername();
        this.userId = userById.getId();
    }
}
