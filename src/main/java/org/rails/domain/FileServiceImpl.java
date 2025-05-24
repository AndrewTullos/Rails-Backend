package org.rails.domain;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import jakarta.annotation.PostConstruct;
import org.rails.models.FileUploadResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class FileServiceImpl implements FileService {

    private static final Logger log = LoggerFactory.getLogger(FileServiceImpl.class);

    @Value("${aws.s3.bucketName}")
    private String bucketName;

    @Value("${aws.s3.accessKey}")
    private String accessKey;

    @Value("${aws.s3.secretKey}")
    private String secretKey;

    private AmazonS3 s3Client;

    @PostConstruct
    private void initialize() {
        try {
            log.info("Initializing S3 client with bucket: {}", bucketName);
            BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
            s3Client = AmazonS3ClientBuilder.standard()
                    .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                    .withRegion(Regions.US_WEST_1)
                    .build();
            log.info("S3 client initialized successfully");
        } catch (Exception e) {
            log.error("Failed to initialize S3 client: {}", e.getMessage(), e);
            throw new RuntimeException("S3 initialization failed", e);
        }
    }

    @Override
    public FileUploadResponse uploadFile(MultipartFile multipartFile) {
        if (multipartFile == null || multipartFile.isEmpty()) {
            log.error("MultipartFile is null or empty");
            throw new FileUploadException("No file provided for upload");
        }

        FileUploadResponse fileUploadResponse = new FileUploadResponse();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String todayDate = dateTimeFormatter.format(LocalDate.now());
        String filePath = todayDate + "/" + multipartFile.getOriginalFilename();

        try {
            log.info("Uploading file to S3: bucket={}, path={}", bucketName, filePath);

            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(multipartFile.getContentType());
            objectMetadata.setContentLength(multipartFile.getSize());

            s3Client.putObject(bucketName, filePath, multipartFile.getInputStream(), objectMetadata);
            String fileUrl = s3Client.getUrl(bucketName, filePath).toString();

            log.info("File uploaded successfully: {}", fileUrl);

            fileUploadResponse.setFilePath(filePath);
            fileUploadResponse.setFileUrl(fileUrl);
            fileUploadResponse.setDateTime(LocalDateTime.now());
        } catch (IOException e) {
            log.error("IO Error during upload: {}", e.getMessage(), e);
            throw new FileUploadException("Error occurred in file upload: " + e.getMessage());
        } catch (Exception e) {
            log.error("Unexpected error during upload: {}", e.getMessage(), e);
            throw new FileUploadException("Unexpected error in file upload: " + e.getMessage());
        }
        return fileUploadResponse;
    }
}