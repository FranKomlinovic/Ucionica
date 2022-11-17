package events.dto;

import com.amazonaws.util.StringUtils;
import lombok.Getter;
import lombok.Setter;
import utils.entity.EventTable;
import utils.utils.CognitoUtils;

import java.time.LocalDateTime;

import static utils.utils.LocalDateTimeUtils.convertToDateString;
import static utils.utils.LocalDateTimeUtils.convertToTimeString;
import static utils.utils.LocalDateTimeUtils.getCurrentTime;

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
    private Boolean currentEvent;
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
        String pic = eventsTable.getPicture();
        if (pic != null) {
            this.setPicture(pic);
        }
        LocalDateTime currentTime = getCurrentTime();
        if (!(currentTime.isBefore(eventsTable.getStartTime()) || currentTime.isAfter(eventsTable.getEndTime()))) {
            setCurrentEvent(true);
        }
        this.setDate(convertToDateString(eventsTable.getStartTime()));
        this.setStartTime(convertToTimeString(eventsTable.getStartTime()));
        this.setEndTime(convertToTimeString(eventsTable.getEndTime()));
        this.setName(eventsTable.getName());
        this.setDescription(eventsTable.getDescription());


    }
}

