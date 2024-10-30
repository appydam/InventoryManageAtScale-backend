package com.example.dilfoods.controller;

import com.example.dilfoods.model.Batch;
import com.example.dilfoods.service.IBatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/batches")
public class BatchController {
    private static final String QR_CODE_IMAGE_PATH = "./qrcodes/";
    private static final String absolutePath  = "/Users/arpitdhamija/Downloads/dilfoods 2/qrcodes";

    @Autowired
    private IBatchService batchService;

    @PostMapping
    public Batch createBatch(@RequestBody Batch batch) {
        return batchService.createBatch(batch);
    }

    @GetMapping("/{batchId}")
    public Batch getBatch(@PathVariable String batchId) {
        return batchService.getBatch(batchId);
    }

    // Retrieve batch information by scanning a QR code
    @GetMapping("/qrCode")
    public Batch getBatchFromQRCode(@RequestParam String qrCodeFilePath) {
        return batchService.getBatchFromQRCode(qrCodeFilePath);
    }

    @GetMapping("/qrcodebaby/{batchId}")
    public byte[] getQRCode(@PathVariable String batchId) throws Exception {
        System.out.println("we are in get QR code API");
        System.out.println("batchId = " + batchId);
//        Path path = Paths.get(QR_CODE_IMAGE_PATH + batchId + ".png");
        Path path = Paths.get(QR_CODE_IMAGE_PATH + batchId + ".png");
        System.out.println("Path = " + path);
        return Files.readAllBytes(path);
    }
}