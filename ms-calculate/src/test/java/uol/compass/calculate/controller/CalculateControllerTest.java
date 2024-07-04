package uol.compass.calculate.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import uol.compass.calculate.dto.request.CalculateRequest;
import uol.compass.calculate.exception.handler.RestExceptionHandler;
import uol.compass.calculate.service.CalculateService;
import uol.compass.calculate.util.CalculateUtil;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CalculateController.class)
@Import({CalculateController.class, RestExceptionHandler.class})
@ExtendWith(SpringExtension.class)
@DisplayName("Tests for Calculate controller")
public class CalculateControllerTest {

    private static final String PATH = "/v1/calculate";

    private MockMvc mockMvc;

    @Autowired private WebApplicationContext webContext;
    @Autowired private ObjectMapper objectMapper;

    @MockBean
    private CalculateService service;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webContext).build();
    }

    @Test
    @DisplayName("Controller calculate")
    void testCalculate() throws Exception {
        final var request = CalculateUtil.getRequest();
        final var response = CalculateUtil.getResponse();

        when(this.service.calculate(any())).thenReturn(CalculateUtil.getResponse());

        this.mockMvc.perform(post(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.total").value(response.getTotal()));
    }

    @Test
    @DisplayName("Controller calculate with empty categoryId")
    void testCalculateWithEmptyCategory() throws Exception {
        final var request = new CalculateRequest(null, 100);

        this.mockMvc.perform(post(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnprocessableEntity());
    }

}