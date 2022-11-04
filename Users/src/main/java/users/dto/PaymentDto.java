package users.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import utils.entity.PaymentTable;

import static utils.utils.EuroUtils.convertBigDecimalToString;
import static utils.utils.LocalDateTimeUtils.convertLocalDateTimeToString;
import static utils.utils.LocalDateTimeUtils.convertToDateString;
import static utils.utils.LocalDateTimeUtils.convertToTimeString;

@Getter
@Setter
@ToString
public class PaymentDto {
    private String date;
    private String time;
    private String amount;

    public PaymentDto(PaymentTable paymentTable) {
        this.date = convertToDateString(paymentTable.getTime());
        this.time = convertToTimeString(paymentTable.getTime());
        this.amount = convertBigDecimalToString(paymentTable.getAmount());
    }
}

