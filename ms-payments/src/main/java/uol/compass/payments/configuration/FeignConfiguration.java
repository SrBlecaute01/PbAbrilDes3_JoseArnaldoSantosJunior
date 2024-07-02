package uol.compass.payments.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.codec.ErrorDecoder;
import feign.okhttp.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uol.compass.payments.client.decoder.ClientErrorDecoder;

@Configuration
public class FeignConfiguration {

    @Bean
    public OkHttpClient client() {
        return new OkHttpClient();
    }

    @Bean
    public ErrorDecoder errorDecoder(ObjectMapper objectMapper) {
        return new ClientErrorDecoder(objectMapper);
    }

}