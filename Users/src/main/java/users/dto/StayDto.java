package users.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import utils.entity.StaysTable;

import static utils.utils.EuroUtils.convertBigDecimalToString;
import static utils.utils.LocalDateTimeUtils.convertLocalDateTimeToString;
import static utils.utils.LocalDateTimeUtils.convertToDateString;
import static utils.utils.LocalDateTimeUtils.convertToTimeString;

@Getter
@Setter
@ToString
public class StayDto {
    private String date;
    private String startTime;
    private String endTime;
    private String price;

    public StayDto(StaysTable staysTable) {
        this.startTime = convertToTimeString(staysTable.getStartTime());
        this.endTime = convertToTimeString(staysTable.getEndTime());
        this.date = convertToDateString(staysTable.getStartTime());
        this.price = convertBigDecimalToString(staysTable.getPrice());
    }
}

