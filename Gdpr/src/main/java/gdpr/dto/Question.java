package gdpr.dto;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@DynamoDBDocument
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Question {
    @DynamoDBAttribute(attributeName = "id")
    private String id;
    @DynamoDBAttribute(attributeName = "topic")
    private String topic;
    @DynamoDBAttribute(attributeName = "question")
    private String question;
    @DynamoDBAttribute(attributeName = "requirement")
    private String requirement;
    @DynamoDBAttribute(attributeName = "yesNo")
    private boolean yesNo;
    @DynamoDBAttribute(attributeName = "evidence")
    private String evidence;
    @DynamoDBAttribute(attributeName = "percentage")
    private int percentage;
}
