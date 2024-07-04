package uol.compass.customer.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import java.io.IOException;
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

    private String getExtensionFromBase64(byte[] base64) {
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(base64)) {
            final var contentType = URLConnection.guessContentTypeFromStream(inputStream);
            if (contentType == null) throw new InvalidFileTypeException();
            return contentType.split("/")[1];
        } catch (IOException | NullPointerException | ArrayIndexOutOfBoundsException exception) {
            log.warn("Error reading file", exception);
            throw new InvalidFileTypeException();
        }
    }

}
