package com.exadel.sandbox.team5.service.impl;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.exadel.sandbox.team5.dao.ImageDAO;
import com.exadel.sandbox.team5.dto.ImageDto;
import com.exadel.sandbox.team5.entity.Image;
import com.exadel.sandbox.team5.mapper.MapperConverter;
import com.exadel.sandbox.team5.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageDAO imageDAO;
    private final MapperConverter mapper;

    @Value("${app.bucketNameS3}")
    private String bucketName;

    private final AmazonS3 s3Client;


    public ImageDto getImage(Long id) {
        Image img = imageDAO.findById(id).orElseThrow(NoSuchElementException::new);
        ImageDto image = mapper.map(img, ImageDto.class);
        image.setContent(s3Client.getObject(bucketName, img.getName()).getObjectContent());
        return image;
    }

    public Long save(MultipartFile file) {
        ImageDto image = new ImageDto();
        try {
            image.setContent(file.getInputStream());
            String contentType = file.getContentType();
            if (contentType.equals("image/png")
                    || contentType.equals("image/jpg")
                    || contentType.equals("image/jpeg")) {
                image.setContentType(contentType);
            } else {
                throw new RuntimeException("Invalid image content type");
            }
        } catch (IOException e) {
            throw new RuntimeException("Incorrect file! Message: " + e.getMessage(), e);
        }
        Image img = mapper.map(image, Image.class);
        String imageName = parseImageName(image, UUID.randomUUID().toString());
        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(image.getContentType());
            PutObjectRequest request = new PutObjectRequest(bucketName, imageName, image.getContent(), metadata);
            s3Client.putObject(request);
        } catch (SdkClientException e) {
            throw new AmazonServiceException("File doesn't save! Message:  " + e.getMessage(), e);
        }
        img.setImageURL(bucketName);
        img.setName(imageName);
        return imageDAO.save(img).getId();
    }

    private String parseImageName(ImageDto image, String name) {
        String typeFile = image.getContentType().split("/")[1];
        return String.format("%s.%s", name, typeFile);
    }
}
