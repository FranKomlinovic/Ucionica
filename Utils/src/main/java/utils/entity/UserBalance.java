package utils.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@DynamoDBTable(tableName = "UcionicaUserBalance")
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserBalance {
    @DynamoDBHashKey(attributeName = "userId")
    private String userId;
    @DynamoDBAttribute(attributeName = "balance")
    private BigDecimal balance;
}
