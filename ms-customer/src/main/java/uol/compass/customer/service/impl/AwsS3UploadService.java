package uol.compass.customer.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.exception.SdkException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import uol.compass.customer.exception.file.FileUploadException;
import uol.compass.customer.exception.file.InvalidBase64FileException;
import uol.compass.customer.exception.file.InvalidFileTypeException;
import uol.compass.customer.service.FileUploadService;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.net.URLConnection;
import java.util.Base64;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AwsS3UploadService implements FileUploadService {

    private final S3Client client;

    @Value("${aws.bucket.name}")
    private String bucketName;

    @Value("${aws.bucket.folder}")
    private String folderName;

    @Value("${photo.extensions}")
    private List<String> availableExtensions;

    @Override
    public String uploadBase64File(String fileName, String base64) {
        try {
            final var decodedPhoto = Base64.getDecoder().decode(base64);
            final var extension = this.getExtensionFromBase64(decodedPhoto);
            if (!this.availableExtensions.contains(extension.toLowerCase())) {
                throw new InvalidFileTypeException();
            }

            final var path = (this.folderName != null ? this.folderName + "/" + fileName : fileName) + "." + extension;
            final var request = PutObjectRequest.builder()
                    .bucket(this.bucketName)
                    .key(path)
                    .contentType("image/" + extension)
                    .build();

            this.client.putObject(request, RequestBody.fromBytes(decodedPhoto));
            return this.client.utilities().getUrl(url -> url.bucket(this.bucketName).key(path)).toExternalForm();

        } catch (IllegalArgumentException exception) {
            throw new InvalidBase64FileException();
        } catch (SdkException exception) {
            log.warn("Error uploading file", exception);
            throw new FileUploadException();
        }
    }

    @Override
    public String uploadFile(String fileName, File file){
        final var extension = this.getFileExtension(file);
        if (!this.availableExtensions.contains(extension.toLowerCase())) {
            throw new InvalidFileTypeException();
        }

        try {
            final var path = (this.folderName != null ? this.folderName + "/" + fileName : fileName) + "." + extension;
            final var request = PutObjectRequest.builder()
                    .bucket(this.bucketName)
                    .key(path)
                    .build();

            this.client.putObject(request, RequestBody.fromFile(file));
            return this.client.utilities().getUrl(url -> url.bucket(this.bucketName).key(path)).toExternalForm();

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
