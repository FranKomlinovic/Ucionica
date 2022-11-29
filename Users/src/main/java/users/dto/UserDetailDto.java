package users.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class UserDetailDto {
   private String id;
   private String username;
   private String balance;
   private String picture;
   private Boolean currentlyActive;
   private List<StayDto> stays;
   private List<PaymentDto> payments;
   private String timeSpent;
   private String averageTimeSpent;
   private String totalStayPrice;
   private String totalPayments;
   private String averageStayPrice;
}

