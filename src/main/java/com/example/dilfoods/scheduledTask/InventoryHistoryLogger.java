package com.example.dilfoods.scheduledTask;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.example.dilfoods.model.InventoryHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Component
public class InventoryHistoryLogger {

    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    @Scheduled(cron = "0 0 * * * *")  // Every hour
    public void logInventoryHistoryToCSV() {
        List<InventoryHistory> historyRecords = dynamoDBMapper.scan(InventoryHistory.class, new DynamoDBScanExpression());
        appendToCSV(historyRecords);
    }

    private void appendToCSV(List<InventoryHistory> historyRecords) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("historical_inventory.csv", true))) {
            for (InventoryHistory record : historyRecords) {
                writer.write(String.format("%s,%s,%d%n",
                        record.getTimestamp(),
                        record.getItemId(),
                        record.getStockLevel()));
            }
            System.out.println("Successfully logged inventory history to CSV.");
        } catch (IOException e) {
            System.err.println("Error writing to CSV: " + e.getMessage());
        }
    }
}
