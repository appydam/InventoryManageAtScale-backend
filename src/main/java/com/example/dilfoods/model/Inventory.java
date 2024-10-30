package com.example.dilfoods.model;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import lombok.Data;

@Data
@DynamoDBTable(tableName = "Inventory")
public class Inventory {
    @DynamoDBHashKey(attributeName = "itemId")
    private String itemId;

    @DynamoDBAttribute
    private String batchId;

    @DynamoDBAttribute
    private int currentStock;
}
