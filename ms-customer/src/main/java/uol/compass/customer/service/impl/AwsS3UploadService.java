package uol.compass.customer.service.impl;

import com.amazonaws.services.s3.transfer.TransferManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import uol.compass.customer.service.FileUploadService;

import java.io.File;

@Service
@RequiredArgsConstructor
public class AwsS3UploadService implements FileUploadService {

    @Value("${aws.bucket.name}")
    private String bucketName;

    @Value("${aws.bucket.folder}")
    private String folderName;

    private final TransferManager transferManager;

    @Override
    public void uploadFile(File file) {
        final var path = this.folderName != null ? this.folderName + "/" + file.getName() : file.getName();
        this.transferManager.upload(this.bucketName, path, file);
    }

}
