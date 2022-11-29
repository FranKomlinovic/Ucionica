package events.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class CreateEventDto {
    private String id;
    private List<String> users;
    private String startTime;
    private String endTime;
    private String name;
    private String description;
    private String picture;
}
