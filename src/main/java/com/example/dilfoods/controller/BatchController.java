package com.example.dilfoods.controller;

import com.example.dilfoods.model.Batch;
import com.example.dilfoods.service.IBatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/batches")
public class BatchController {
    private static final String QR_CODE_IMAGE_PATH = "./qrcodes/";
    private static final String absolutePath  = "/Users/arpitdhamija/Downloads/dilfoods 2/qrcodes/";

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
    public ResponseEntity<FileSystemResource> getQRCode(@PathVariable String batchId) {
        File file = new File(absolutePath + batchId + ".png");
        if (file.exists()) {
//            return ResponseEntity.ok(new FileSystemResource(file));
            Resource resource = new FileSystemResource(file);
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .body((FileSystemResource) resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}