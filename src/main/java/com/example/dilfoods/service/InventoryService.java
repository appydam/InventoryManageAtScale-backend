package com.example.dilfoods.service;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.example.dilfoods.model.Inventory;
import com.example.dilfoods.model.InventoryHistory;
import com.example.dilfoods.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InventoryService implements IInventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    @Override
    public Inventory createInventory(Inventory inventory) {
        System.out.println("inventory creation started");
        return inventoryRepository.save(inventory);
    }

//    @Override
//    public Inventory updateStock(String itemId, int newStock) {
//        Inventory inventory = inventoryRepository.findById(itemId).orElseThrow();
//        inventory.setCurrentStock(newStock);
//        return inventoryRepository.save(inventory);
//    }

//    @Override
//    public Inventory updateStock(String itemId, int newStock) {
//        Inventory inventory = inventoryRepository.findById(itemId)
//                .orElseThrow(() -> new RuntimeException("Item not found: " + itemId));
//        inventory.setCurrentStock(newStock);
//        return inventoryRepository.save(inventory);
//    }

    public Inventory updateStock(String itemId, int newStock) {
        Inventory inventory = inventoryRepository.findById(itemId).orElseThrow();
        inventory.setCurrentStock(newStock);
        Inventory updatedInventory = inventoryRepository.save(inventory);

        // Log to InventoryHistory
        InventoryHistory history = new InventoryHistory();
        history.setItemId(itemId);
        history.setStockLevel(newStock);
        history.setTimestamp(LocalDateTime.now());
        dynamoDBMapper.save(history);

        return updatedInventory;
    }

    public List<Inventory> findItemsByStockLevel(int stockLevel) {
        Map<String, AttributeValue> expressionAttributeValues = new HashMap<>();
        expressionAttributeValues.put(":stockLevel", new AttributeValue().withN(Integer.toString(stockLevel)));

        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("currentStock = :stockLevel")
                .withExpressionAttributeValues(expressionAttributeValues);

        return dynamoDBMapper.scan(Inventory.class, scanExpression);
    }

    public List<Inventory> findItemsWithStockBelowThreshold(int threshold) {
        Map<String, AttributeValue> expressionAttributeValues = new HashMap<>();
        expressionAttributeValues.put(":threshold", new AttributeValue().withN(Integer.toString(threshold)));

        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("currentStock < :threshold")
                .withExpressionAttributeValues(expressionAttributeValues);

        return dynamoDBMapper.scan(Inventory.class, scanExpression);
    }
}
