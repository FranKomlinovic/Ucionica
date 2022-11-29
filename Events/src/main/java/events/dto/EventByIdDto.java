package events.dto;

import lombok.Getter;
import lombok.Setter;
import utils.entity.EventTable;

import java.util.List;

import static utils.utils.LocalDateTimeUtils.convertToString;

@Getter
@Setter
public class EventByIdDto {
    private String id;
    private List<String> users;
    private String startTime;
    private String endTime;
    private String name;
    private String description;
    private String picture;

    public EventByIdDto(EventTable eventsTable) {
        this.setId(eventsTable.getId());
        this.setUsers(eventsTable.getUsers());
        this.setStartTime(convertToString(eventsTable.getStartTime()));
        this.setEndTime(convertToString(eventsTable.getEndTime()));
        this.setName(eventsTable.getName());
        this.setDescription(eventsTable.getDescription());
        this.setPicture(eventsTable.getPicture());
    }
}

