package com.example.dilfoods.scheduledTask;


import com.example.dilfoods.dto.PredictionResponse;
import com.example.dilfoods.repository.InventoryRepository;
import com.example.dilfoods.service.MLPredictionService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class InventoryPredictionScheduler {

    private static final Logger logger = LoggerFactory.getLogger(InventoryPredictionScheduler.class);

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private MLPredictionService mlPredictionService;

    @Scheduled(cron = "0 0 * * * *")  // Every hour
    public void scheduledPrediction() {
        LocalDateTime now = LocalDateTime.now();
        String date = now.toLocalDate().toString();
        int hourOfDay = now.getHour();

        inventoryRepository.findAll().forEach(inventory -> {
            try {
                PredictionResponse predictedStock = mlPredictionService.predictInventory(date, hourOfDay, inventory.getCurrentStock());
                logger.info("Predicted stock for item {}: {}", inventory.getItemId(), predictedStock);
            } catch (Exception e) {
                logger.error("Error predicting stock for item {}: {}", inventory.getItemId(), e.getMessage());
            }
        });
    }
}
