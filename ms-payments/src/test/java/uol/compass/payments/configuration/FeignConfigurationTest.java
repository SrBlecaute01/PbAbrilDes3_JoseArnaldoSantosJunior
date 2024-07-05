package uol.compass.payments.configuration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Request;
import feign.Response;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import uol.compass.payments.exception.ApplicationErrorMessage;
import uol.compass.payments.exception.ApplicationException;

import java.nio.charset.Charset;
import java.util.HashMap;

@ExtendWith(SpringExtension.class)
@DisplayName("Tests for Feign configuration")
@ContextConfiguration(classes= ObjectMapperConfiguration.class)
public class FeignConfigurationTest {

    private static final Logger log = LoggerFactory.getLogger(FeignConfigurationTest.class);
    @Autowired
    private ObjectMapper mapper;

    private final FeignConfiguration configuration = new FeignConfiguration();

    @Test
    @DisplayName("Configuration feign decode")
    void testFeignDecode() throws JsonProcessingException {
        final var error = new ApplicationException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", "POST");

        final var request = Request.create(Request.HttpMethod.GET, "v1/test", new HashMap<>(), null, null, null);
        final var response = Response.builder()
                .status(500)
                .reason(HttpStatus.INTERNAL_SERVER_ERROR.toString()).request(request).headers(new HashMap<>())
                .body(this.mapper.writeValueAsString(error), Charset.defaultCharset())
                .build();

        final var decoder = this.configuration.errorDecoder(this.mapper);
        final var exception = decoder.decode("", response);

        Assertions.assertThat(exception.getMessage()).isEqualTo(error.getMessage());
    }

    @Test
    @DisplayName("Configuration feign decode with exception")
    void testFeignDecodeWithException() {
        final var request = Request.create(Request.HttpMethod.GET, "v1/test", new HashMap<>(), null, null, null);
        final var response = Response.builder()
                .status(500)
                .reason(HttpStatus.INTERNAL_SERVER_ERROR.toString()).request(request).headers(new HashMap<>())
                .build();

        final var exception = configuration.errorDecoder(mapper).decode("", response);
        Assertions.assertThat(exception).isInstanceOf(ApplicationException.class);
    }

}