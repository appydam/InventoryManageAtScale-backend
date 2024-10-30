package com.example.dilfoods.service;

import com.example.dilfoods.dto.PredictionResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Service
public class MLPredictionService {

    private final String ML_API_URL = "http://localhost:5001"; // Flask API base URL
    private final RestTemplate restTemplate;

    public void trainModel() {
        String url = ML_API_URL + "/train";
        restTemplate.postForEntity(url, null, Void.class); // Send a POST request to train the model
    }

    public PredictionResponse predictInventory(String date, int hourOfDay, int currentStock) {
        String url = ML_API_URL + "/predict";

        String requestBody = String.format("{\"date\": \"%s\", \"hour_of_day\": %d, \"current_stock\": %d}", date, hourOfDay, currentStock);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<PredictionResponse> response = restTemplate.postForEntity(url, entity, PredictionResponse.class);

        return response.getBody();
    }
}