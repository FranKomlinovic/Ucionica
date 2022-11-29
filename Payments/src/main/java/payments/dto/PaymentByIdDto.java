package payments.dto;

import lombok.Getter;
import lombok.Setter;
import utils.entity.PaymentTable;

import static utils.utils.EuroUtils.convertToHrk;
import static utils.utils.LocalDateTimeUtils.convertToString;

@Getter
@Setter
public class PaymentByIdDto {
    private String id;
    private String userId;
    private Double amount;
    private String time;
    private String description;

    public PaymentByIdDto(PaymentTable eventsTable) {
        this.setId(eventsTable.getId());
        this.setUserId(eventsTable.getUserId());
        this.setTime(convertToString(eventsTable.getTime()));
        this.setAmount(convertToHrk(eventsTable.getAmount()).doubleValue());
        this.setDescription(eventsTable.getDescription());
    }
}

