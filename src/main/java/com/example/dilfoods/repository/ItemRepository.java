package com.example.dilfoods.repository;

// ItemRepository.java
import com.example.dilfoods.model.Item;
import org.socialsignin.spring.data.dynamodb.repository.DynamoDBCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends DynamoDBCrudRepository<Item, String> {
}
