package uol.compass.customer.configuration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import software.amazon.awssdk.auth.credentials.AwsSessionCredentials;
import software.amazon.awssdk.services.s3.S3Client;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

@DisplayName("Tests for AwsConfiguration")
public class AwsConfigurationTest {

    private final AwsConfiguration configuration = new AwsConfiguration();

    @Test
    @DisplayName("Configuration S3Client instance")
    public void testAmazonS3() {
        Mockito.mockStatic(AwsSessionCredentials.class)
                .when(() -> AwsSessionCredentials.create(any(), any(), any()))
                .thenReturn(Mockito.mock(AwsSessionCredentials.class));

        final var s3Client = this.configuration.amazonS3();
        assertThat(s3Client).isInstanceOf(S3Client.class);
    }

}