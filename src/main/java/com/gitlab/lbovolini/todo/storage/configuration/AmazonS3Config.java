package com.gitlab.lbovolini.todo.storage.configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmazonS3Config {

    // !todo usar ENV var
    private final AWSCredentials credentials = new BasicAWSCredentials(
            "AKIA6BD4DRY3M5CJECEV",
            "RpZciV8CqRNrfFh7t0jZUeTgFeCaFJU3avjZ5toW"
    );

    @Bean
    public AmazonS3 s3client() {
        return AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.SA_EAST_1)
                .build();
    }
}
