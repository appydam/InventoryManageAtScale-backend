package com.example.dilfoods.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@Component
public class QRCodeUtil {

    private static final String QR_CODE_IMAGE_PATH = "./qrcodes/";

    // Generate a QR code image based on a given batch ID
    public String generateQRCode(String batchId) {
        try {
            File directory = new File(QR_CODE_IMAGE_PATH);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            String filePath = QR_CODE_IMAGE_PATH + batchId + ".png";
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            Map<EncodeHintType, ErrorCorrectionLevel> hints = new HashMap<>();
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

            BitMatrix bitMatrix = qrCodeWriter.encode(batchId, BarcodeFormat.QR_CODE, 200, 200, hints);
            Path path = Paths.get(filePath);
            MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);

            return filePath;
        } catch (Exception e) {
            throw new RuntimeException("Error generating QR Code", e);
        }
    }

    // Decode a QR code image to retrieve the batch ID
    public String scanQRCode(String filePath) {
        try {
            BufferedImage bufferedImage = ImageIO.read(new File(filePath));
            BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(bufferedImage)));
            Result qrCodeResult = new MultiFormatReader().decode(binaryBitmap);

            return qrCodeResult.getText();
        } catch (Exception e) {
            throw new RuntimeException("Error decoding QR Code", e);
        }
    }
}

