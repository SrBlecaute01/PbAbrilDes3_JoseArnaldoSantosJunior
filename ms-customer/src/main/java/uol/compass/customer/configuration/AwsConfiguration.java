package uol.compass.customer.configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.codepipeline.model.AWSSessionCredentials;
import com.amazonaws.services.connect.model.Credentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AwsConfiguration {

    @Value("${aws.accessKey")
    private String accessKey;

    @Value("${aws.secretKey")
    private String secretKey;

    @Bean
    public AmazonS3 amazonS3() {
        final var credentials = new BasicAWSCredentials(accessKey, secretKey);
        final var credentialsProvider = new AWSStaticCredentialsProvider(credentials);

        return AmazonS3ClientBuilder.standard()
                .withCredentials(credentialsProvider)
                .withPathStyleAccessEnabled(false)
                .withChunkedEncodingDisabled(true)
                .build();
    }

}