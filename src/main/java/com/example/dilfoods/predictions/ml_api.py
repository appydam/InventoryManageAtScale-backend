import pandas as pd
import matplotlib
matplotlib.use('Agg')
import matplotlib.pyplot as plt
from sklearn.linear_model import LinearRegression
import pickle
from datetime import datetime
from flask import Flask, request, jsonify

app = Flask(__name__)

model = None

def train_model():
    global model

    data = pd.read_csv("historical_inventory.csv", header=0)

    # Convert 'timestamp' to datetime
    data['timestamp'] = pd.to_datetime(data['timestamp'], errors='coerce')

    data = data.dropna(subset=['timestamp'])

    data['day_of_week'] = data['timestamp'].dt.dayofweek
    data['hour_of_day'] = data['timestamp'].dt.hour
    data['days_since_start'] = (data['timestamp'] - data['timestamp'].min()).dt.days  # Days since the first date

    X = data[['day_of_week', 'hour_of_day', 'days_since_start', 'stock_level']]
    y = data['stock_level'].shift(-1).fillna(0)

    # Visualization of the stock level over time
    plt.figure(figsize=(10, 6))
    plt.plot(data['timestamp'], data['stock_level'], marker='o', linestyle='-')
    plt.title('Stock Levels Over Time')
    plt.xlabel('Date')
    plt.ylabel('Stock Level')
    plt.xticks(rotation=45)
    plt.tight_layout()
    plt.savefig('stock_levels_over_time.png')
    plt.close()

    # Train the model
    model = LinearRegression()
    print("Training the model...")
    model.fit(X, y)

    # Save the trained model
    with open('inventory_model.pkl', 'wb') as file:
        pickle.dump(model, file)

    print("Model training completed and saved as inventory_model.pkl.")

@app.route('/train', methods=['POST'])
def train():
    train_model()
    return jsonify({'message': 'Model trained successfully.'})

@app.route('/predict', methods=['POST'])
def predict():
    data = request.json
    date_str = data['date']  # 'YYYY-MM-DD' format
    hour_of_day = data['hour_of_day']
    current_stock = data['current_stock']

    prediction = predict_stock(date_str, hour_of_day, current_stock)
    return jsonify({'predicted_stock': prediction})

def predict_stock(date_str, hour_of_day, current_stock):
    global model
    if model is None:
        with open('inventory_model.pkl', 'rb') as file:
            model = pickle.load(file)

    input_date = pd.to_datetime(date_str)

    # Calculate the day of the week and days since start
    day_of_week = input_date.dayofweek  # 0 = Monday, ..., 6 = Sunday
    start_date = pd.to_datetime('2024-10-25')
    days_since_start = (input_date - start_date).days

    input_data = pd.DataFrame([[day_of_week, hour_of_day, days_since_start, current_stock]],
                              columns=['day_of_week', 'hour_of_day', 'days_since_start', 'stock_level'])

    prediction = model.predict(input_data)
    return prediction[0]

if __name__ == "__main__":
    app.run(host='0.0.0.0', port=5001)
