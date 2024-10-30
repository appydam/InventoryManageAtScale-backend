package com.example.dilfoods.service;

import com.example.dilfoods.model.Inventory;

public interface IInventoryService {
    Inventory createInventory(Inventory inventory);
    Inventory updateStock(String itemId, int newStock);
    Inventory getInventory(String itemId);
}