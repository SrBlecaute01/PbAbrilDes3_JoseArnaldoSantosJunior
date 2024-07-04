package uol.compass.calculate.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.web.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import uol.compass.calculate.dto.request.RuleRequest;
import uol.compass.calculate.exception.handler.RestExceptionHandler;
import uol.compass.calculate.exception.rule.RuleAlreadyExistsException;
import uol.compass.calculate.exception.rule.RuleNotFoundException;
import uol.compass.calculate.service.RuleService;
import uol.compass.calculate.util.RuleUtil;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RuleController.class)
@Import({RuleController.class, RestExceptionHandler.class})
@ExtendWith(SpringExtension.class)
@DisplayName("Tests for Rule controller")
public class RuleControllerTest {

    private static final String PATH = "/v1/rules";

    private MockMvc mockMvc;

    @Autowired private WebApplicationContext webContext;
    @Autowired private ObjectMapper objectMapper;

    @MockBean
    private RuleService service;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webContext).build();
    }

    @Test
    @DisplayName("Controller create rule")
    void testCreateRule() throws Exception {
        final var request = RuleUtil.getRequest();
        final var response = RuleUtil.getResponse();

        when(this.service.createRule(any())).thenReturn(RuleUtil.getResponse());

        this.mockMvc.perform(post(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(response.getId()))
                .andExpect(jsonPath("$.category").value(response.getCategory()))
                .andExpect(jsonPath("$.parity").value(response.getParity()));
    }

    @Test
    @DisplayName("Controller create a rule with existing category")
    void testCreateRuleWithExistingCategory() throws Exception {
        final var request = RuleUtil.getRequest();

        when(this.service.createRule(any())).thenThrow(new RuleAlreadyExistsException());

        this.mockMvc.perform(post(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict());
    }

    @Test
    @DisplayName("Controller create a rule with empty category")
    void testCreateRuleWithEmptyCategory() throws Exception {
        final var request = new RuleRequest(null, RuleUtil.getRequest().getParity());

        this.mockMvc.perform(post(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @DisplayName("Controller get rules")
    void testGetRules() throws Exception {
        final var rule = RuleUtil.getResponse();
        final var pageable = new PagedModel<>(new PageImpl<>(List.of(rule)));

        when(this.service.getRules(any())).thenReturn(pageable);

        final var metadata = pageable.getMetadata();
        Assertions.assertNotNull(metadata);

        this.mockMvc.perform(get(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(RuleUtil.getRequest())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content[0].id").value(rule.getId()))
                .andExpect(jsonPath("$.content[0].category").value(rule.getCategory()))
                .andExpect(jsonPath("$.content[0].parity").value(rule.getParity()))
                .andExpect(jsonPath("$.page.totalElements").value(metadata.totalElements()));
    }

    @Test
    @DisplayName("Controller get rule by id")
    void testGetRule() throws Exception {
        final var rule = RuleUtil.getResponse();

        when(this.service.getRule(rule.getId())).thenReturn(rule);

        this.mockMvc.perform(get(PATH + "/{id}", rule.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(RuleUtil.getRequest())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(rule.getId()))
                .andExpect(jsonPath("$.category").value(rule.getCategory()))
                .andExpect(jsonPath("$.parity").value(rule.getParity()));
    }

    @Test
    @DisplayName("Controller get rule by id not found")
    void testGetRuleNotFound() throws Exception {
        final var rule = RuleUtil.getResponse();

        when(this.service.getRule(rule.getId())).thenThrow(new RuleNotFoundException());

        this.mockMvc.perform(get(PATH + "/{id}", rule.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(RuleUtil.getRequest())))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Controller update rule")
    void testUpdateRule() throws Exception {
        final var request = RuleUtil.getUpdateRequest();
        final var response = RuleUtil.getUpdateResponse();

        when(this.service.updateRule(any(), any())).thenReturn(response);

        this.mockMvc.perform(put(PATH + "/{id}", response.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(response.getId()))
                .andExpect(jsonPath("$.category").value(response.getCategory()))
                .andExpect(jsonPath("$.parity").value(response.getParity()));
    }

    @Test
    @DisplayName("Controller update rule with existing category")
    void testUpdateRuleWithExistingCategory() throws Exception {
        final var request = RuleUtil.getUpdateRequest();
        final var response = RuleUtil.getUpdateResponse();

        when(this.service.updateRule(any(), any())).thenThrow(new RuleAlreadyExistsException());

        this.mockMvc.perform(put(PATH + "/{id}", response.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict());
    }

    @Test
    @DisplayName("Controller update rule with empty category")
    void testUpdateRuleWithEmptyCategory() throws Exception {
        final var request = new RuleRequest(null, RuleUtil.getUpdateRequest().getParity());

        this.mockMvc.perform(put(PATH + "/{id}", RuleUtil.getUpdateResponse().getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @DisplayName("Controller update rule not found")
    void testUpdateRuleNotFound() throws Exception {
        final var request = RuleUtil.getUpdateRequest();
        final var response = RuleUtil.getUpdateResponse();

        when(this.service.updateRule(any(), any())).thenThrow(new RuleNotFoundException());

        this.mockMvc.perform(put(PATH + "/{id}", response.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Controller delete rule")
    void testDeleteRule() throws Exception {
        final var rule = RuleUtil.getResponse();

        doNothing().when(this.service).deleteRule(rule.getId());

        this.mockMvc.perform(delete(PATH + "/{id}", rule.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(RuleUtil.getRequest())))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Controller delete rule not found")
    void testDeleteRuleNotFound() throws Exception {
        final var rule = RuleUtil.getResponse();

        doThrow(new RuleNotFoundException()).when(this.service).deleteRule(rule.getId());

        this.mockMvc.perform(delete(PATH + "/{id}", rule.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(RuleUtil.getRequest())))
                .andExpect(status().isNotFound());
    }


}
