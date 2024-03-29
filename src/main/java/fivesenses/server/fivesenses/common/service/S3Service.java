package fivesenses.server.fivesenses.common.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class S3Service {

    private final static String DIR_IMAGES = "images/";

    private AmazonS3 s3Client;

    @Value("${cloud.aws.credentials.accessKey}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secretKey}")
    private String secretKey;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.region.static}")
    private String region;

    @PostConstruct
    public void setS3Client() {
        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);

        s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(this.region)
                .build();
    }

    public String uploadWithUUID(MultipartFile file) {
        String fileName = getFileNameWithUUID(file);
        try {
            uploadOnS3(file, fileName);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("S3 예외 발생 : uploadWithUUID");
        }

        return s3Client.getUrl(bucket, fileName).toString();
    }

    public String upload(MultipartFile file, String dirName) {
        String fileName = getFileName(file, dirName);

        try {
            uploadOnS3(file, fileName);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("S3 예외 발생 : upload");
        }

        return s3Client.getUrl(bucket, fileName).toString();
    }

    public void deleteFile(String fileName) {
        String path = DIR_IMAGES + fileName;
        s3Client.deleteObject(bucket, path);
    }

    private void uploadOnS3(MultipartFile file, String fileName) throws IOException {
        s3Client.putObject(new PutObjectRequest(bucket, fileName, file.getInputStream(), null)
                .withCannedAcl(CannedAccessControlList.PublicRead));
    }

    private String getFileNameWithUUID(MultipartFile file) {
        StringBuffer sb = new StringBuffer();
        return sb.append(DIR_IMAGES)
                .append(UUID.randomUUID())
                .append("_")
                .append(file.getOriginalFilename())
                .toString();
    }

    private String getFileName(MultipartFile file, String dirName) {
        StringBuffer sb = new StringBuffer();
        return sb.append(dirName)
                .append(file.getOriginalFilename())
                .toString();
    }
}