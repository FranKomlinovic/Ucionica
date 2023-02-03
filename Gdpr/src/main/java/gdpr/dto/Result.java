package gdpr.dto;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@DynamoDBDocument
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result {
    @DynamoDBAttribute(attributeName = "resultByFields")
    private List<ResultByField> resultByFields;
    @DynamoDBAttribute(attributeName = "resultByCategories")
    private List<ResultByCategory> resultByCategories;
    @DynamoDBAttribute(attributeName = "finalResult")
    private double finalResult;
}
