package com.example.dilfoods.controller;


import com.example.dilfoods.dto.PredictionRequest;
import com.example.dilfoods.dto.PredictionResponse;
import com.example.dilfoods.model.Inventory;
import com.example.dilfoods.repository.InventoryRepository;
import com.example.dilfoods.service.MLPredictionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/ml")
public class PredictionController {

    @Autowired
    private MLPredictionService predictionService;

    @PostMapping("/train")
    public ResponseEntity<String> trainModel() {
        predictionService.trainModel();
        return ResponseEntity.ok("Model training initiated.");
    }

    // Endpoint to predict inventory data
    @PostMapping("/predict")
    public ResponseEntity<PredictionResponse> predictStock(@RequestBody PredictionRequest request) {
        String dateString = request.getDate().toString();

        PredictionResponse predictedStock = predictionService.predictInventory(
                dateString,
                request.getHourOfDay(),
                request.getCurrentStock()
        );
        System.out.println("predictedStock = " + predictedStock);
        return ResponseEntity.ok(predictedStock);
    }

}
