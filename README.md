# DilFoods Backend

## Overview
This DilFoods project aims to develop a comprehensive full-stack web application designed to manage inventory using QR codes, enhanced with a predictive analytics feature to forecast inventory needs through machine learning which enables businesses to optimize their stock levels and reduce wastage.

Designed this app with scalability and extensibility in mind, and follows the best coding practices and industry standards

**Backend** - Java Springboot (a robust framework widely adopted by leading companies worldwide for its modularity and performance) 

**Database** - DynamoDB (highly scalable distributed database)

**Author**: Arpit Dhamija | [linkedin](https://www.linkedin.com/in/arpitdhamija/)


## Table of Contents

1. [How to Run backend](#how-to-run)
2. [API Documentation](#api-documentation)
    - [Batch Management API](#batch-management-api)
    - [Inventory Management API](#inventory-management-api)
    - [Machine Learning Prediction API](#machine-learning-prediction-api)
3. [Inventory Prediction Model Documentation](#inventory-prediction-model-documentation)
4. [License](#license)

---

## How to Run 

### Setup DynamoDB Locally

1. **Download DynamoDB Local:**  
   Follow the instructions [here](https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/DynamoDBLocal.DownloadingAndRunning.html) to download DynamoDB Local.

2. **Extract and Run DynamoDB Local:**  
   Open a terminal in the folder where you extracted DynamoDB Local and run:
   ```bash
   java -Djava.library.path=./DynamoDBLocal_lib -jar DynamoDBLocal.jar -sharedDb
   ```

3. **Configure AWS CLI:**  
   Run the following command to configure AWS CLI:
   ```bash
   aws configure
   ```
   Set the region to `ap-south-1` and endpoint to `8000`.

4. **Create DynamoDB Tables:**  
   Create the required tables by running the following commands in your terminal:

   ```bash
   aws dynamodb create-table        
   --table-name Inventory        
   --attribute-definitions  AttributeName=itemId,AttributeType=S        
   --key-schema  AttributeName=itemId,KeyType=HASH        
   --provisioned-throughput  ReadCapacityUnits=5,WriteCapacityUnits=5        
   --endpoint-url http://localhost:8000
   ```

   ```bash
   aws dynamodb create-table       
    --table-name Batch       
    --attribute-definitions  AttributeName=batchId,AttributeType=S        
   --key-schema  AttributeName=batchId,KeyType=HASH        
   --provisioned-throughput  ReadCapacityUnits=5,WriteCapacityUnits=5        
   --endpoint-url http://localhost:8000
   ```
   
    ```bash
      aws dynamodb create-table \
        --table-name InventoryHistory \
        --attribute-definitions AttributeName=id,AttributeType=S \
        --key-schema AttributeName=id,KeyType=HASH \
        --provisioned-throughput ReadCapacityUnits=5,WriteCapacityUnits=5 \
        --endpoint-url http://localhost:8000
    
    ```

5. **Managing Tables:**
    - **Delete Table:**
      ```bash
      aws dynamodb delete-table --table-name Batches --endpoint-url http://localhost:8000
      ```

    - **Scan Table:**
      ```bash
      aws dynamodb list-tables --endpoint-url http://localhost:8000
      ```

### Prerequisites

- **Java Version:** Ensure you are using Java 17.
- **IDE:** Use IntelliJ IDEA for running the project.
- **NoSQL Workbench:** Download this to visually see DB tables

---

## API Documentation

### Batch Management API

#### 1. Create Batch

**Endpoint:**  
`POST /api/batches`

**Request Body:**
```json
{
    "batchId": "string",
    "productName": "string",
    "quantity": 100,
    "expiryDate": "2024-12-31"
}
```

**Response:**  
Returns the created Batch object.

Example output:
```
{
    "batchId": "B001",
    "productId": "P124",
    "receivedDate": "2024-10-29T12:00:00",
    "quantity": 100,
    "qrCodeUrl": "http://localhost:8080/api/qrcodes/B001"
}
```
This QR code is mapped to BatchId: `B001`

![Project Dashboard](qrcodes/B001.png)

---

#### 2. Get Batch

**Endpoint:**  
`GET /api/batches/{batchId}`

**Path Variable:**
- `batchId`: ID of the batch to retrieve.

**Response:**  
Returns the Batch object for the specified `batchId`.

---

#### 3. Get Batch from QR Code

**Endpoint:**  
`GET /api/batches/qrCode`

**Request Parameter:**
- `qrCodeFilePath`: Path to the QR code file.

```bash
curl --location 'http://localhost:8080/api/batches/qrCode?qrCodeFilePath=.%2Fqrcodes%2FB001.png'
```

**Response:**  
Returns the Batch object decoded from the specified QR code.

---

#### 4. Get QR Code

**Endpoint:**  
`GET /api/batches/qrcodebaby/{batchId}`

**Path Variable:**
- `batchId`: ID of the batch for which the QR code is requested.

**Response:**  
Returns the QR code image as a _byte array_.
---

### Inventory Management API

#### 1. Create Inventory

**Endpoint:**  
`POST /api/inventory`

**Request Body:**
```json
{
    "itemId": "string",
    "itemName": "string",
    "currentStock": 100,
    "category": "string"
}
```

**Response:**  
Returns the created Inventory object.

---

#### 2. Update Stock

**Endpoint:**  
`PUT /api/inventory/{itemId}/stock`

**Path Variable:**
- `itemId`: ID of the inventory item.

**Request Parameter:**
- `newStock`: The new stock level to be set.

**Response:**  
Returns the updated Inventory object.

---

#### 3. Get All Inventory

**Endpoint:**  
`GET /api/inventory`

**Response:**  
Returns a list of all Inventory objects.

---

#### 4. Get Inventory

**Endpoint:**  
`GET /api/inventory/{item_id}`

**Response:**  
Returns a Inventory object.

---

### Machine Learning Prediction API

#### 1. Train Model

**Endpoint:**  
`POST /api/ml/train`
```bash
curl --location --request POST 'http://localhost:8080/api/ml/train'
```

**Response:**  
Returns a message indicating that model training has been initiated.

---

#### 2. Predict Stock

**Endpoint:**  
`POST /api/ml/predict`

**Request Body:**
```json
{
    "date": "2024-10-30",
    "hourOfDay": 16,
    "currentStock": 200
}
```
```bash
curl --location --request POST 'http://localhost:8080/api/inventory/train'
```

**Response:**  
Returns a PredictionResponse object containing the predicted stock level.
```
{
    "predicted_stock": 240.92391304347802
}
```
---

## Inventory Prediction Model Documentation

### Overview

This document describes an inventory prediction model built using Python, leveraging linear regression for forecasting stock levels based on historical inventory data. The model takes into account the day of the week, hour of the day, and historical stock levels to predict future stock levels.

### Prerequisites

Ensure you have the following installed:

- Python 3.6 or higher
- Required Python libraries:
    - `pandas`
    - `matplotlib`
    - `scikit-learn`
    - `pickle`

Create a Virtual Environment

```bash
cd /path/to/ML-model-folder
python -m venv venv
```
Activate the Virtual Environment:
```
venv\Scripts\activate  [for windows]
source venv/bin/activate  [for mac]
```
Install Required Packages
```
pip install pandas matplotlib scikit-learn
```
Deactivate the Virtual Environment:
```deactivate```
### File Structure

The main script for the model is `inventory_model.py`. The historical inventory data should be stored in a CSV file named `historical_inventory.csv`, structured as follows:

#### Example CSV Format

```
date,timestamp,item_id,stock_level
2024-10-25,2024-10-25 08:00:00,ITEM001,200
2024-10-25,2024-10-25 12:00:00,ITEM001,180
...
```

### Functions

#### `train_model()`

- **Description**: Trains the linear regression model on historical inventory data.
- **Input**: None (reads from `historical_inventory.csv`).
- **Output**: Saves the trained model to a file named `inventory_model.pkl` and generates a visualization of stock levels over time.

#### `predict_stock(date_str, hour_of_day, current_stock)`

- **Description**: Predicts the stock level for a given day of the week and hour of the day based on current stock levels.
- **Inputs**:
    - `date_str` (string): The date for prediction in `YYYY-MM-DD` format.
    - `hour_of_day` (int): The hour of the day (0-23).
    - `current_stock` (int): The current stock level.
- **Output**: Returns the predicted stock level as a float.

### Command-Line Usage

First, Create a virtual environment to run this ML model

#### Training the Model

To train the model on the historical inventory data, make sure you are in same folder of this ML model script in terminal while executing this command, run the following command:

```bash
python3 ml_api.py train
```

#### Making Predictions

To predict the stock level for a specific date, hour, and current stock, use the following command:

```bash
python3 ml_api.py predict <date_str> <hour_of_day> <current_stock>
```

#### Example

```bash
python3 ml_api.py predict 2024-10-30 16 200
```

### Visualizations

The model generates a plot of stock levels over time during the training phase, which is saved as `stock_levels_over_time.png`. This visualization helps to understand historical trends and stock fluctuations.

### Error Handling

- The model includes basic error handling during the date parsing process. If an invalid date format is provided, the program will raise an error.
- Ensure that the input data in `historical_inventory.csv` is correctly formatted and free of missing or invalid timestamps.

### Conclusion

This inventory prediction model serves as a foundational tool for managing stock levels effectively. By utilizing historical data and linear regression, it provides insights that can help optimize inventory management strategies.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.
