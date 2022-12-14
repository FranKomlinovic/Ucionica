package utils.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@DynamoDBTable(tableName = "UcionicaPayments")
@ToString
public class PaymentTable {
    @DynamoDBHashKey(attributeName = "id")
    @DynamoDBAutoGeneratedKey
    private String id;
    @DynamoDBAttribute(attributeName = "userId")
    private String userId;
    @DynamoDBAttribute(attributeName = "description")
    private String description;
    @DynamoDBTypeConverted(converter = LocalDateTimeConverter.class)
    private LocalDateTime time;
    @DynamoDBAttribute(attributeName = "amount")
    private BigDecimal amount;
}