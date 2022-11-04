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
    private String userId;
    private BigDecimal amount;
    private LocalDateTime time;
}
