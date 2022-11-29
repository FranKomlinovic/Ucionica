package payments.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class CreatePaymentDto {
    private String id;
    private String userId;
    private BigDecimal amount;
    private String time;
    private String description;
}
