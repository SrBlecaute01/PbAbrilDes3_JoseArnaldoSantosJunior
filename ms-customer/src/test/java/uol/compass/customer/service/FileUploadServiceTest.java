package uol.compass.customer.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;
import software.amazon.awssdk.core.exception.SdkException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Utilities;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import uol.compass.customer.exception.file.FileUploadException;
import uol.compass.customer.exception.file.InvalidBase64FileException;
import uol.compass.customer.exception.file.InvalidFileTypeException;
import uol.compass.customer.service.impl.AwsS3UploadServiceImpl;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
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
        this.service = new AwsS3UploadServiceImpl(client);

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
    @SuppressWarnings("unchecked")
    @DisplayName("Service upload without folder name")
    void testUploadWithFolderName() throws MalformedURLException {
        ReflectionTestUtils.setField(this.service, "folderName", null);

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
        final var base64 =
                "PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0idXRmLTgiPz48IS0tIFVwbG9hZGVkIHRvOiBTVkcgUmVwbywgd3d3LnN2Z3JlcG8uY29tLCBHZ" +
                        "W5lcmF0b3I6IFNWRyBSZXBvIE1peGVyIFRvb2xzIC0tPg0KPHN2ZyB3aWR0aD0iODAwcHgiIGhlaWdodD0iODAwcHgiIHZpZXdCb3g9Ij" +
                        "AgMCAyNCAyNCIgZmlsbD0ibm9uZSIgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4NCjxwYXRoIGQ9Ik0yMiA3LjgxVjE" +
                        "yLjVIMTcuOTJDMTcuOCAxMi40OSAxNy41NCAxMi4zNCAxNy40OCAxMi4yM0wxNi40NCAxMC4yNkMxNi4wMyA5LjQ4IDE1LjMyIDkuMDQg" +
                        "MTQuNTYgOS4wOEMxMy44IDkuMTIgMTMuMTUgOS42MyAxMi44MiAxMC40NkwxMS40NCAxMy45MkwxMS4yNCAxMy40QzEwLjc1IDEyLjEzI" +
                        "DkuMzUgMTEuMTcgNy45NyAxMS4xN0wyIDExLjJWNy44MUMyIDQuMTcgNC4xNyAyIDcuODEgMkgxNi4xOUMxOS44MyAyIDIyIDQuMTcgMj" +
                        "IgNy44MVoiIGZpbGw9IiMyOTJEMzIiLz4NCjxwYXRoIGQ9Ik0yMiAxNi4xODg3VjEzLjk5ODdIMTcuOTJDMTcuMjUgMTMuOTk4NyAxNi4" +
                        "0NiAxMy41MTg3IDE2LjE1IDEyLjkyODdMMTUuMTEgMTAuOTU4N0MxNC44MyAxMC40Mjg3IDE0LjQzIDEwLjQ1ODcgMTQuMjEgMTEuMDA4" +
                        "N0wxMS45MSAxNi44MTg3QzExLjY2IDE3LjQ2ODcgMTEuMjQgMTcuNDY4NyAxMC45OCAxNi44MTg3TDkuODQgMTMuOTM4N0M5LjU3IDEzL" +
                        "jIzODcgOC43MyAxMi42Njg3IDcuOTggMTIuNjY4N0wyIDEyLjY5ODdWMTYuMTg4N0MyIDE5Ljc2ODcgNC4xIDIxLjkyODcgNy42MyAyMS" +
                        "45ODg3QzcuNzQgMjEuOTk4NyA3Ljg2IDIxLjk5ODcgNy45NyAyMS45OTg3SDE1Ljk3QzE2LjEyIDIxLjk5ODcgMTYuMjcgMjEuOTk4NyA" +
                        "xNi40MSAyMS45ODg3QzE5LjkyIDIxLjkwODcgMjIgMTkuNzU4NyAyMiAxNi4xODg3WiIgZmlsbD0iIzI5MkQzMiIvPg0KPHBhdGggZD0i" +
                        "TTIuMDAwNyAxMi42OTkyVjE2LjAwOTJDMS45ODA3IDE1LjY4OTIgMS45NzA3IDE1LjM0OTIgMS45NzA3IDE0Ljk5OTJWMTIuNjk5MkgyL" +
                        "jAwMDdaIiBmaWxsPSIjMjkyRDMyIi8+DQo8L3N2Zz4=";

        Assertions.assertThrows(InvalidFileTypeException.class, () -> this.service.uploadBase64File("test", base64));
    }

    @Test
    @DisplayName("Service upload with sdk exception.")
    void testUploadWithSdkException() {
        final var base64 =
                "iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAABHNCSVQICAgIfAhkiAAAAAlwSFlzAAAAkQAAAJEBVAVoGQAAA" +
                        "Bl0RVh0U29mdHdhcmUAd3d3Lmlua3NjYXBlLm9yZ5vuPBoAAAFISURBVDiNpZM/S0JRGMZ/71GIiAaXhghqymtj4WKDyM2PEB" +
                        "Z9gPZWRyen9r5DBS2FV1EvdT+AS44FURE5mUT+OW+LhpSa2bOcw/O+v+c8yxFV5T8KDy63rrtKqJMWzLKqBIutj5uVIHgHeEg" +
                        "k5psLc9simsDqvZieFy1cPwKIqlLfSR4iHAPzX9HKnQ2bNIDpWg9hbejhFsiR41VOpO4mUypaAmREw5f+uTRipoimwohmx8Dj" +
                        "wIEEJGsUNicsTZayZYDXmQOgYQS82Qto0fS6Jgc0Z+DfCJEzG+Xys7GSAdp/gDsiHMSu/CcDsF6qXIqwC3SmgVUkEy1ULwDMw" +
                        "O0be780aYPuxwqV84FhhqeOVz0T1ThQGwHXRDXueP7psGm+b0WLfs1GGnGBPGABK5C3kUY8WvR/BMuk31h3kwkAp1QNxu1MDJ" +
                        "hGny82eMnIn1weAAAAAElFTkSuQmCC";

        when(client.putObject(any(PutObjectRequest.class), any(RequestBody.class))).thenThrow(SdkException.class);
        Assertions.assertThrows(FileUploadException.class, () -> this.service.uploadBase64File("test", base64));
    }

    @Test
    @DisplayName("Service upload with base64 IoException.")
    void testUploadWithBase64IoException() {
        final var base64 =
                "iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAABHNCSVQICAgIfAhkiAAAAAlwSFlzAAAAkQAAAJEBVAVoGQAAA" +
                        "Bl0RVh0U29mdHdhcmUAd3d3Lmlua3NjYXBlLm9yZ5vuPBoAAAFISURBVDiNpZM/S0JRGMZ/71GIiAaXhghqymtj4WKDyM2PEB" +
                        "Z9gPZWRyen9r5DBS2FV1EvdT+AS44FURE5mUT+OW+LhpSa2bOcw/O+v+c8yxFV5T8KDy63rrtKqJMWzLKqBIutj5uVIHgHeEg" +
                        "k5psLc9simsDqvZieFy1cPwKIqlLfSR4iHAPzX9HKnQ2bNIDpWg9hbejhFsiR41VOpO4mUypaAmREw5f+uTRipoimwohmx8Dj" +
                        "wIEEJGsUNicsTZayZYDXmQOgYQS82Qto0fS6Jgc0Z+DfCJEzG+Xys7GSAdp/gDsiHMSu/CcDsF6qXIqwC3SmgVUkEy1ULwDMw" +
                        "O0be780aYPuxwqV84FhhqeOVz0T1ThQGwHXRDXueP7psGm+b0WLfs1GGnGBPGABK5C3kUY8WvR/BMuk31h3kwkAp1QNxu1MDJ" +
                        "hGny82eMnIn1weAAAAAElFTkSuQmCC";

        try (final var mock = mockStatic(URLConnection.class)) {
            mock.when(() -> URLConnection.guessContentTypeFromStream(any(InputStream.class))).thenThrow(IOException.class);
            Assertions.assertThrows(InvalidFileTypeException.class, () -> this.service.uploadBase64File("test", base64));
        }
    }

    @Test
    @DisplayName("Service upload with null contentType")
    void testUploadWithNullContentType() {
        final var base64 =
                "iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAABHNCSVQICAgIfAhkiAAAAAlwSFlzAAAAkQAAAJEBVAVoGQAAA" +
                        "Bl0RVh0U29mdHdhcmUAd3d3Lmlua3NjYXBlLm9yZ5vuPBoAAAFISURBVDiNpZM/S0JRGMZ/71GIiAaXhghqymtj4WKDyM2PEB" +
                        "Z9gPZWRyen9r5DBS2FV1EvdT+AS44FURE5mUT+OW+LhpSa2bOcw/O+v+c8yxFV5T8KDy63rrtKqJMWzLKqBIutj5uVIHgHeEg" +
                        "k5psLc9simsDqvZieFy1cPwKIqlLfSR4iHAPzX9HKnQ2bNIDpWg9hbejhFsiR41VOpO4mUypaAmREw5f+uTRipoimwohmx8Dj" +
                        "wIEEJGsUNicsTZayZYDXmQOgYQS82Qto0fS6Jgc0Z+DfCJEzG+Xys7GSAdp/gDsiHMSu/CcDsF6qXIqwC3SmgVUkEy1ULwDMw" +
                        "O0be780aYPuxwqV84FhhqeOVz0T1ThQGwHXRDXueP7psGm+b0WLfs1GGnGBPGABK5C3kUY8WvR/BMuk31h3kwkAp1QNxu1MDJ" +
                        "hGny82eMnIn1weAAAAAElFTkSuQmCC";

        try (final var mock = mockStatic(URLConnection.class)) {
            mock.when(() -> URLConnection.guessContentTypeFromStream(any(InputStream.class))).thenReturn(null);
            Assertions.assertThrows(InvalidFileTypeException.class, () -> this.service.uploadBase64File("test", base64));
        }
    }



}