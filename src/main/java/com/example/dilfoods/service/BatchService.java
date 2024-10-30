package com.example.dilfoods.service;

import com.example.dilfoods.model.Batch;
import com.example.dilfoods.repository.BatchRepository;
import com.example.dilfoods.utils.QRCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class BatchService implements IBatchService {

    private static final Logger logger = LoggerFactory.getLogger(BatchService.class);

    @Autowired
    private BatchRepository batchRepository;

    @Autowired
    private QRCodeUtil qrCodeUtil;

    @Override
    public Batch createBatch(Batch batch) {
        logger.info("Creating batch with ID: {}", batch.getBatchId());

        String qrCodePath = qrCodeUtil.generateQRCode(batch.getBatchId());
//        batch.setQrCodeUrl(qrCodePath);
        batch.setQrCodeUrl("http://localhost:8080/api/qrcodes/" + batch.getBatchId()); // Update to serve QR code URL

        return batchRepository.save(batch);
    }

    @Override
    public Batch getBatch(String batchId) {
        return batchRepository.findById(batchId).orElseThrow();
    }

    // Retrieve a batch by scanning its QR code
    @Override
    public Batch getBatchFromQRCode(String qrCodeFilePath) {
        String batchId = qrCodeUtil.scanQRCode(qrCodeFilePath);

        return batchRepository.findById(batchId).orElseThrow();
    }
}
