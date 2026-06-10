package org.example.project.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;

import java.util.Map;

@Configuration
public class CloudinaryConfig {

    @Value("${cloudinary.cloud-name}")
    private String cloudName;

    @Value("${cloudinary.api-key}")
    private String apiKey;

    @Value("${cloudinary.api-secret}")
    private String apiSecret;

    @Bean
    public CloudinaryConfig cloudinary(){

        return new Cloudinary(
                Map.of(
                        "cloud_name",
                        cloudName,
                        "api_key",
                        apiKey,
                        "api_secret",
                        apiSecret
                )
        );
    }
}
