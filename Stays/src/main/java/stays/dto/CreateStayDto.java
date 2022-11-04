package stays.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CreateStayDto {
    private String userId;
    private String time;
}
