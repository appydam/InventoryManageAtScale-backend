package com.example.dilfoods.service;

import com.example.dilfoods.model.Batch;

public interface IBatchService {
    Batch createBatch(Batch batch);
    Batch getBatch(String batchId);
    Batch getBatchFromQRCode(String qrCodeFilePath);
}
