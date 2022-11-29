package events;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.util.StringUtils;
import events.dto.CreateEventDto;
import utils.dto.ResponseDto;
import utils.entity.EventTable;

import java.util.List;

import static utils.entity.SdkObjects.DYNAMO_DB_MAPPER;
import static utils.utils.LocalDateTimeUtils.convertToLocalDate;

public class CreateEvent implements RequestHandler<CreateEventDto, ResponseDto> {

    @Override
    public ResponseDto handleRequest(CreateEventDto eventDto, Context context) {
        EventTable eventsTable = new EventTable();
        String responseText = "kreiran";
        if (!StringUtils.isNullOrEmpty(eventDto.getId())) {
            eventsTable = DYNAMO_DB_MAPPER.load(EventTable.class, eventDto.getId());
            responseText = "ažuriran";
        }
        List<String> users = eventDto.getUsers();
        if (users == null || users.isEmpty()) {
            users = null;
        }
        eventsTable.setUsers(users);


        eventsTable.setName(eventDto.getName());
        eventsTable.setDescription(eventDto.getDescription());
        eventsTable.setStartTime(convertToLocalDate(eventDto.getStartTime()));
        eventsTable.setEndTime(convertToLocalDate(eventDto.getEndTime()));
        if (!StringUtils.isNullOrEmpty(eventDto.getPicture())) {
            eventsTable.setPicture(eventDto.getPicture());
        }
        DYNAMO_DB_MAPPER.save(eventsTable);
        return new ResponseDto("Event pod nazivom " + eventDto.getName() + " je " + responseText);
    }
}
