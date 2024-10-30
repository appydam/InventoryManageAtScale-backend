package com.example.dilfoods.repository;

import com.example.dilfoods.model.Batch;
import org.socialsignin.spring.data.dynamodb.repository.DynamoDBCrudRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BatchRepository extends DynamoDBCrudRepository<Batch, String> {
}
