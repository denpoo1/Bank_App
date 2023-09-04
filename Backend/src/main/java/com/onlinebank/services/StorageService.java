package com.onlinebank.services;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.onlinebank.models.AccountModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class StorageService {

    @Value("${application.bucket.name}")
    private String bucketName;

    @Autowired
    private AmazonS3 s3Client;

    @Value("${cloud.aws.region.static}")
    private String region;

    @Autowired
    private AccountService accountService;

    public String uploadFile(MultipartFile multipartFile, int accountId) {
        AccountModel accountModel = accountService.getAccountById(accountId);
        if (accountModel == null) {
            throw new IllegalArgumentException("account with id " + accountId + " does not exist");
        }
        File fileObj = convertMultipartFileToFile(multipartFile);
        String fileName = multipartFile.getOriginalFilename();
        String username = accountModel.getCustomerModel().getUsername();
        s3Client.putObject(new PutObjectRequest(bucketName, username, fileObj));
        accountModel.setAvatarUrl("https://" + bucketName + ".s3." + region + ".amazonaws.com/" + username);
        accountService.saveAccount(accountModel);
        fileObj.delete();
        return "File uploaded : " + fileName;
    }

    public byte[] downloadFile(String url) {
        String bucketName = this.bucketName;
        String objectKey = getObjectKeyFromS3URI(url);
        System.out.println("bucketName: " + bucketName);
        System.out.println("objectKey: " + objectKey);
        S3Object s3Object = s3Client.getObject(bucketName, objectKey);
        S3ObjectInputStream inputStream = s3Object.getObjectContent();
        try {
            byte[] content = IOUtils.toByteArray(inputStream);
            return content;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String deleteFile(String url) {
        String bucketName = this.bucketName;
        String objectKey = getObjectKeyFromS3URI(url);
        s3Client.deleteObject(bucketName, objectKey);
        return objectKey + " removed ...";
    }

    public static File convertMultipartFileToFile(MultipartFile multipartFile) {
        File file = new File(multipartFile.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(multipartFile.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    private static String getObjectKeyFromS3URI(String s3Uri) {
        String[] parts = s3Uri.split("/", 4);
        if (parts.length >= 4) {
            return parts[parts.length - 1];
        } else {
            throw new IllegalArgumentException("Invalid S3 URI format: " + s3Uri);
        }
    }

}
