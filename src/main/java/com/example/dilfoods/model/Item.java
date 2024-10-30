package com.example.dilfoods.model;

// Item.java
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamoDBTable(tableName = "Item")
public class Item {

    @DynamoDBHashKey(attributeName = "id")
    private String id;

    @DynamoDBAttribute
    private String name;

    @DynamoDBAttribute
    private String description;
}
