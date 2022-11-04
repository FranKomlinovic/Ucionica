package stays.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import utils.dto.UserDto;
import utils.entity.StaysTable;
import utils.entity.UserBalance;
import utils.utils.CognitoUtils;
import utils.utils.LocalDateTimeUtils;

import static utils.entity.SdkObjects.DYNAMO_DB_MAPPER;
import static utils.utils.EuroUtils.convertBigDecimalToString;

@Getter
@Setter
@ToString
public class CurrentStayDto {
    private String id;
    private String user;
    private String userId;
    private String startTime;
    // Implement later
    private String balance;
    private String picture;

    public CurrentStayDto(StaysTable staysTable) {
        this.setId(staysTable.getId());
        this.setStartTime(LocalDateTimeUtils.convertLocalDateTimeToString(staysTable.getStartTime()));
        this.setUserId(staysTable.getUserId());
        this.setBalance(convertBigDecimalToString(DYNAMO_DB_MAPPER.load(UserBalance.class, staysTable.getUserId()).getBalance()));
        UserDto userById = CognitoUtils.getUserById(staysTable.getUserId());
        if (userById != null) {
            this.setPicture(userById.getPicture());
            this.setUser(userById.getUsername());
        }
    }
}

