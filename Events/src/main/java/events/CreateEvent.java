package events;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.util.StringUtils;
import events.dto.CreateEventDto;
import utils.dto.ResponseDto;
import utils.entity.EventTable;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static utils.entity.SdkObjects.DYNAMO_DB_MAPPER;

public class CreateEvent implements RequestHandler<CreateEventDto, ResponseDto> {

    @Override
    public ResponseDto handleRequest(CreateEventDto eventDto, Context context) {
        EventTable eventsTable = new EventTable();
        if (!eventDto.getUsers().isEmpty()) {
            eventsTable.setUsers(eventDto.getUsers());
        }
        eventsTable.setName(eventDto.getName());
        eventsTable.setDescription(eventDto.getDescription());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        eventsTable.setStartTime(LocalDateTime.parse(eventDto.getStartTime(), formatter));
        eventsTable.setEndTime(LocalDateTime.parse(eventDto.getEndTime(), formatter));
        if (!StringUtils.isNullOrEmpty(eventDto.getPicture())) {
            eventsTable.setPicture(eventDto.getPicture());
        }
        DYNAMO_DB_MAPPER.save(eventsTable);
        return new ResponseDto("Event pod nazivom " + eventDto.getName() + " je kreiran");

    }
}
