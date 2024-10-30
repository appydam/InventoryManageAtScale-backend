package com.example.dilfoods.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import lombok.Data;

import java.time.LocalDate;

@Data
@DynamoDBTable(tableName = "HistoricalData")
public class HistoricalData {
    @DynamoDBHashKey(attributeName = "id")
    private String id;

    @DynamoDBAttribute(attributeName = "productId")
    private String productId;

    @DynamoDBAttribute(attributeName = "date")
    private LocalDate date;

    @DynamoDBAttribute(attributeName = "sales")
    private int sales;
}
