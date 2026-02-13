package org.codeit.sb06.team03.mopl.storage.infra;

import org.codeit.sb06.team03.mopl.storage.domain.model.StoragePayload;
import org.codeit.sb06.team03.mopl.storage.domain.service.FileUploadPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.nio.file.Path;

@Component
@Retryable(retryFor = RuntimeException.class)
public class FileUploadAdapter implements FileUploadPort {

    private final String bucketName;
    private final S3Client client;

    public FileUploadAdapter(@Value("${mopl.storage.bucket-name}") String bucketName, S3Client client) {
        this.bucketName = bucketName;
        this.client = client;
    }

    @Override
    @Retryable
    public void upload(StoragePayload payload) {
        final String fileKey = payload.fileKey();
        final String filePath = payload.filePath();
        final String contentType = payload.contentType();
        final long contentLength = payload.contentLength();
        upload(fileKey, filePath, contentType, contentLength);
    }

    private void upload(String fileKey, String filePath, String contentType, long contentLength) {
        final Path path = Path.of(filePath);
        final PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(fileKey)
                .contentType(contentType)
                .contentLength(contentLength)
                .build();
        client.putObject(request, path);
    }
}
