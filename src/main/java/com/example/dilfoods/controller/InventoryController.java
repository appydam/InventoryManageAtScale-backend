package com.example.dilfoods.controller;

import com.example.dilfoods.dto.PredictionRequest;
import com.example.dilfoods.dto.PredictionResponse;
import com.example.dilfoods.model.Inventory;
import com.example.dilfoods.repository.InventoryRepository;
import com.example.dilfoods.service.IInventoryService;
import com.example.dilfoods.service.MLPredictionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {
    @Autowired
    private IInventoryService inventoryService;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private MLPredictionService predictionService;

    @PostMapping
    public Inventory createInventory(@RequestBody Inventory inventory) {
        return inventoryService.createInventory(inventory);
    }

    @PutMapping("/{itemId}/stock")
    public Inventory updateStock(@PathVariable String itemId, @RequestParam int newStock) {
        return inventoryService.updateStock(itemId, newStock);
    }

    // get all inventory data
    @GetMapping
    public List<Inventory> getAllInventory() {
        return (List<Inventory>) inventoryRepository.findAll();
    }
}
