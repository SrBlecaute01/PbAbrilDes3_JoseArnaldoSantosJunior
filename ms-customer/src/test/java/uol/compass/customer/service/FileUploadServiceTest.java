package uol.compass.customer.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Utilities;
import uol.compass.customer.exception.file.InvalidBase64FileException;
import uol.compass.customer.exception.file.InvalidFileTypeException;
import uol.compass.customer.service.impl.AwsS3UploadService;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.function.Consumer;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@DisplayName("Tests for File Upload Service")
public class FileUploadServiceTest {

    private FileUploadService service;

    @Mock
    private S3Client client;

    @BeforeEach
    void setup() {
        this.service = new AwsS3UploadService(client);

        ReflectionTestUtils.setField(this.service, "bucketName", "test");
        ReflectionTestUtils.setField(this.service, "folderName", "profile");
        ReflectionTestUtils.setField(this.service, "availableExtensions", List.of("png", "jpg", "jpeg", "gif"));
    }

    @Test
    @SuppressWarnings("unchecked")
    @DisplayName("Service upload base64 file.")
    void testUploadBase64File() throws MalformedURLException {
        final var base64 =
                "iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAABHNCSVQICAgIfAhkiAAAAAlwSFlzAAAAkQAAAJEBVAVoGQAAA" +
                "Bl0RVh0U29mdHdhcmUAd3d3Lmlua3NjYXBlLm9yZ5vuPBoAAAFISURBVDiNpZM/S0JRGMZ/71GIiAaXhghqymtj4WKDyM2PEB" +
                "Z9gPZWRyen9r5DBS2FV1EvdT+AS44FURE5mUT+OW+LhpSa2bOcw/O+v+c8yxFV5T8KDy63rrtKqJMWzLKqBIutj5uVIHgHeEg" +
                "k5psLc9simsDqvZieFy1cPwKIqlLfSR4iHAPzX9HKnQ2bNIDpWg9hbejhFsiR41VOpO4mUypaAmREw5f+uTRipoimwohmx8Dj" +
                "wIEEJGsUNicsTZayZYDXmQOgYQS82Qto0fS6Jgc0Z+DfCJEzG+Xys7GSAdp/gDsiHMSu/CcDsF6qXIqwC3SmgVUkEy1ULwDMw" +
                "O0be780aYPuxwqV84FhhqeOVz0T1ThQGwHXRDXueP7psGm+b0WLfs1GGnGBPGABK5C3kUY8WvR/BMuk31h3kwkAp1QNxu1MDJ" +
                "hGny82eMnIn1weAAAAAElFTkSuQmCC";

        final var url = new URL("https://test.s3.amazonaws.com/profile/test.jpeg");

        when(client.utilities()).thenReturn(mock(S3Utilities.class));
        when(client.utilities().getUrl(any(Consumer.class))).thenReturn(url);

        final var value = this.service.uploadBase64File("test", base64);
        Assertions.assertEquals(url.toExternalForm(), value);
    }

    @Test
    @DisplayName("Service upload base64 file with invalid base64.")
    void testUploadBase64FileWithInvalidBase64() {
        final var base64 =
                "AAAAf8/9hAAAABHNCSVQICAgIfAhkiAAAAAlwSFlzAAAAkQAAAJEBVAVoGQAAA" +
                        "Bl0RVh0U29mdHdhcmUAd3d3Lmlua3NjYXBlLm9yZ5vuPBoAAAFISURBVDiNpZM/S0JRGMZ/71GIiAaXhghqymtj4WKDyM2PEB" +
                        "Z9gPZWRyen9r5DBS2FV1EvdT+AS44FURE5mUT+OW+LhpSa2bOcw/O+v+c8yxFV5T8KDy63rrtKqJMWzLKqBIutj5uVIHgHeEg" +
                        "k5psLc9simsDqvZieFy1cPwKIqlLfSR4iHAPzX9HKnQ2bNIDpWg9hbejhFsiR41VOpO4mUypaAmREw5f+uTRipoimwohmx8Dj" +
                        "wIEEJGsUNicsTZayZYDXmQOgYQS82Qto0fS6Jgc0Z+DfCJEzG+Xys7GSAdp/gDsiHMSu/CcDsF6qXIqwC3SmgVUkEy1ULwDMw" +
                        "O0be780aYPuxwqV84FhhqeOVz0T1ThQGwHXRDXueP7psGm+b0WLfs1GGnGBPGABK5C3kUY8WvR/BMuk31h3kwkAp1QNxu1MDJ" +
                        "hGny82eMnIn1weAAAAAElFTkSuQmCC";

        Assertions.assertThrows(InvalidBase64FileException.class, () -> this.service.uploadBase64File("test", base64));
    }

    @Test
    @DisplayName("Service upload base64 file with invalid file extension.")
    void testUploadBase64FileWithInvalidFileExtension() {
        final var base64 = "dGVzdA==";
        Assertions.assertThrows(InvalidFileTypeException.class, () -> this.service.uploadBase64File("test", base64));
    }

}