package uol.compass.calculate.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Tests for OpenApi configuration")
public class OpenApiConfigurationTest {

    private final OpenApiConfiguration configuration = new OpenApiConfiguration();

    @Test
    @DisplayName("Test for OpenApi configuration")
    void testOpenApiConfiguration() {
        final var openApi = this.configuration.openAPI();
        assertThat(openApi).isInstanceOf(OpenAPI.class);
    }

}