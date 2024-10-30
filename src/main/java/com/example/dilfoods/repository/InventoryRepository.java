package com.example.dilfoods.repository;

import com.example.dilfoods.model.Inventory;
import org.socialsignin.spring.data.dynamodb.repository.DynamoDBCrudRepository;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@EnableScan
@Repository
public interface InventoryRepository extends DynamoDBCrudRepository<Inventory, String> {
}