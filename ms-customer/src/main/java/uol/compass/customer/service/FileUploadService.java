package uol.compass.customer.service;

import java.io.File;

public interface FileUploadService {

    String uploadBase64File(String fileName, String base64);

    String uploadFile(String fileName, File file) throws InterruptedException;

}