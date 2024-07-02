package uol.compass.payments.client.decoder;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.antlr.v4.runtime.CharStreams;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import uol.compass.payments.exception.ApplicationException;

import java.nio.charset.StandardCharsets;

@Slf4j
@Component
@RequiredArgsConstructor
public class ClientErrorDecoder implements ErrorDecoder {

    private final ObjectMapper objectMapper;

    @Override
    public Exception decode(String methodKey, Response response) {
        try (final var reader = response.body().asReader(StandardCharsets.UTF_8)) {
            String result = CharStreams.fromReader(reader).toString();
            return this.objectMapper.readValue(result, ApplicationException.class);
        } catch (Exception exception) {
            return new ApplicationException(HttpStatus.valueOf(response.status()), response.reason(), methodKey);
        }
    }

}