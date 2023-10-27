package com.explotwons.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

@Service
public class AmazonS3Service {

    @Autowired
    private S3Client s3Client;

    @Value("${aws.s3.bucketName}")
    private String bucketName;

    @Value("${aws.region}")
    private String region;

    public void uploadFile(MultipartFile file, String keyName) throws IOException {

        // Create a PutObjectRequest
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(keyName)
                .build();

        // Convert MultipartFile to bytes
        byte[] bytes = file.getBytes();
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);

        // Upload file to S3
        s3Client.putObject(putObjectRequest, RequestBody.fromByteBuffer(byteBuffer));
    }

    public void deleteFile(String keyName) {
        s3Client.deleteObject(DeleteObjectRequest.builder().bucket(bucketName).key(keyName).build());
    }

    public String getBucketName() {
        return bucketName;
    }

    public String getRegion() {
        return region;
    }

}
