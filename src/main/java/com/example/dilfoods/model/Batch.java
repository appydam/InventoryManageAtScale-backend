package com.example.dilfoods.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;
import com.example.dilfoods.convertor.LocalDateTimeConverter;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@DynamoDBTable(tableName = "Batch")
public class Batch {
    @DynamoDBHashKey(attributeName = "batchId")
    private String batchId;

    @DynamoDBAttribute
    private String productId;

    @DynamoDBAttribute
    @DynamoDBTypeConverted(converter = LocalDateTimeConverter.class)
    private LocalDateTime receivedDate;

    @DynamoDBAttribute
    private int quantity;

    @DynamoDBAttribute
    private String qrCodeUrl;
}
