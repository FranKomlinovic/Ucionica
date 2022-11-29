package payments.dto;

import lombok.Getter;
import lombok.Setter;
import utils.dto.UserDto;
import utils.entity.PaymentTable;
import utils.utils.CognitoUtils;

import static utils.utils.EuroUtils.convertBigDecimalToString;
import static utils.utils.LocalDateTimeUtils.convertLocalDateTimeToString;

@Getter
@Setter
public class PaymentDto {
    private String id;
    private String user;
    private String time;
    private String amount;
    private String picture;
    private String description;

    public PaymentDto(PaymentTable payment) {
        this.setId(payment.getId());
        this.setTime(convertLocalDateTimeToString(payment.getTime()));
        this.setAmount(convertBigDecimalToString(payment.getAmount()));
        UserDto userById = CognitoUtils.getUserById(payment.getUserId());
        if (userById != null) {
            this.setPicture(userById.getPicture());
            this.setUser(userById.getUsername());
        }
    }
}

