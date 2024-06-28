package uol.compass.customer.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import uol.compass.customer.exception.file.FileUploadException;
import uol.compass.customer.exception.file.InvalidBase64FileException;
import uol.compass.customer.exception.file.InvalidFileTypeException;
import uol.compass.customer.service.FileUploadService;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URLConnection;
import java.nio.file.Files;
import java.util.Base64;

@Slf4j
@Service
@RequiredArgsConstructor
public class AwsS3UploadService implements FileUploadService {

    private final AmazonS3 s3;

    @Value("${aws.bucket.name}")
    private String bucketName;

    @Value("${aws.bucket.folder}")
    private String folderName;

    @Override
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public String uploadBase64File(String fileName, String base64) {
        try {
            final var decodedPhoto = Base64.getDecoder().decode(base64);
            final var extension = getExtensionFromBase64(decodedPhoto);

            File file = null;
            try {
                file = File.createTempFile(fileName, "." + extension);
                Files.write(file.toPath(), decodedPhoto);
                return this.uploadFile(fileName, file);
            } catch (IOException exception) {
                log.warn("Error uploading file", exception);
                throw new FileUploadException();
            } finally {
                if (file != null) file.delete();
            }

        } catch (IllegalArgumentException exception) {
            throw new InvalidBase64FileException();
        }
    }

    @Override
    public String uploadFile(String fileName, File file){
        final var extension = this.getFileExtension(file);
        if (!extension.equals("png") && !extension.equals("jpeg")) {
            throw new InvalidFileTypeException();
        }

        try {
            final var path = (this.folderName != null ? this.folderName + "/" + fileName : fileName) + "." + extension;
            this.s3.putObject(this.bucketName, path, file);
            return this.s3.getUrl(this.bucketName, path).toExternalForm();
        } catch (Exception exception) {
            log.warn("Error uploading file", exception);
            throw new FileUploadException();
        }
    }

    @NotNull
    private String getFileExtension(@NotNull File file) {
        final var name = file.getName();
        final var index = name.lastIndexOf(".");
        return index == -1 || index == name.length() - 1 ? "" : name.substring(index + 1);
    }

    private String getExtensionFromBase64(byte[] base64) {
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(base64)) {
            final var contentType = URLConnection.guessContentTypeFromStream(inputStream);
            return contentType.split("/")[1];
        } catch (Exception exception) {
            log.warn("Error reading file", exception);
            throw new InvalidFileTypeException();
        }
    }

}
