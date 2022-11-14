package events.dto;

import com.amazonaws.util.StringUtils;
import lombok.Getter;
import lombok.Setter;
import utils.entity.EventTable;
import utils.utils.CognitoUtils;

import static utils.utils.LocalDateTimeUtils.convertLocalDateTimeToString;
import static utils.utils.LocalDateTimeUtils.convertToDateString;
import static utils.utils.LocalDateTimeUtils.convertToTimeString;

@Getter
@Setter
public class EventDto {
    private String id;
    private String users;
    private String date;
    private String startTime;
    private String endTime;
    private String name;
    private String description;
    private String picture = "https://www.panpivo.hr/wp-content/uploads/2019/07/PAN_1024x512.jpg";

    public EventDto(EventTable eventsTable) {
        this.setId(eventsTable.getId());
        if (eventsTable.getUsers() != null) {
            this.setUsers(StringUtils.join(", ",
                    eventsTable.getUsers().stream()
                            .map(userId -> CognitoUtils.getUserById(userId).getUsername())
                            .toArray(String[]::new)));
        } else {
            this.setUsers("Svi dejstvenici su pozvani!");
        }
        this.setDate(convertToDateString(eventsTable.getStartTime()));
        this.setStartTime(convertToTimeString(eventsTable.getStartTime()));
        this.setEndTime(convertToTimeString(eventsTable.getEndTime()));
        this.setName(eventsTable.getName());
        this.setDescription(eventsTable.getDescription());


    }
}

