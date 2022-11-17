package utils.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@DynamoDBTable(tableName = "UcionicaEvents")
public class EventTable {
    @DynamoDBHashKey(attributeName = "id")
    @DynamoDBAutoGeneratedKey
    private String id;
    @DynamoDBAttribute(attributeName = "userList")
    private List<String> users;
    @DynamoDBAttribute(attributeName = "acceptedUsers")
    private List<String> acceptedUsers;
    @DynamoDBAttribute(attributeName = "name")
    private String name;
    @DynamoDBAttribute(attributeName = "picture")
    private String picture;
    @DynamoDBAttribute(attributeName = "description")
    private String description;
    @DynamoDBTypeConverted(converter = LocalDateTimeConverter.class)
    private LocalDateTime startTime;
    @DynamoDBTypeConverted(converter = LocalDateTimeConverter.class)
    private LocalDateTime endTime;

}