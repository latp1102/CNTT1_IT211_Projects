package org.example.project.service;

import com.cloudinary.Cloudinary;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class FileUploadService {

    private final Cloudinary cloudinaryConfig;

    public String upload(
            MultipartFile file
    ){

        try{

            Map result =
                    cloudinaryConfig.uploader()
                            .upload(
                                    file.getBytes(),
                                    Map.of()
                            );

            return result
                    .get("secure_url")
                    .toString();

        }catch (Exception ex){

            throw new RuntimeException(
                    "Cloud upload failed"
            );
        }
    }
}
