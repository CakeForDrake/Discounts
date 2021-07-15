package com.exadel.sandbox.team5.service;

import java.io.File;

public interface QRCodeService {
    /**
     * Generate QR code.
     * @return byte array as QR code image representation.
     */
    byte[] generateQRCode(String promocode);

    /**
     * Read QR code.
     * @return String text from image.
     */
    String readQRCode(File file);

    /**
     * @param uuid unique code from link
     * @return true if this uuid exists in database
     */
    boolean checkUUID(String uuid);

    /**
     * @return true if uuid and promocode exists in DB
     */
    boolean ifQRCodeIsValid(String uuid);
}
