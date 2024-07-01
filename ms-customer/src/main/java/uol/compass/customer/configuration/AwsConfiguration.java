package uol.compass.customer.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsSessionCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class AwsConfiguration {

    @Value("${aws.accessKey}")
    private String accessKey;

    @Value("${aws.secretKey}")
    private String secretKey;

    @Value("${aws.accessToken}")
    private String accessToken;

    @Bean
    public S3Client amazonS3() {
        final var credentials = AwsSessionCredentials.create(this.accessKey, this.secretKey, this.accessToken);
        final var provider = StaticCredentialsProvider.create(credentials);

        return S3Client.builder().credentialsProvider(provider).build();
    }

}