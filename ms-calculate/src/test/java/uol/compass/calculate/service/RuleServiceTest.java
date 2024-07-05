package uol.compass.calculate.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import uol.compass.calculate.configuration.MapperConfiguration;
import uol.compass.calculate.exception.rule.RuleAlreadyExistsException;
import uol.compass.calculate.exception.rule.RuleNotFoundException;
import uol.compass.calculate.repository.RuleRepository;
import uol.compass.calculate.service.impl.RuleServiceImpl;
import uol.compass.calculate.util.RuleUtil;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@DisplayName("Tests for Rule Service")
@ContextConfiguration(classes= MapperConfiguration.class)
public class RuleServiceTest {

    private RuleService service;

    @Mock
    private RuleRepository ruleRepository;

    @Autowired
    private ModelMapper modelMapper;

    @BeforeEach
    void setup() {
        this.service = new RuleServiceImpl(this.ruleRepository, this.modelMapper);
    }

    @Test
    @DisplayName("Service create rule")
    void testCreateRule() {
        final var request = RuleUtil.getRequest();
        final var rule = RuleUtil.getRule();

        when(this.ruleRepository.existsByCategoryIgnoreCase(rule.getCategory())).thenReturn(false);
        when(this.ruleRepository.save(any())).thenReturn(rule);

        final var response = this.service.createRule(request);

        Assertions.assertEquals(rule.getId(), response.getId());
        Assertions.assertEquals(rule.getCategory(), response.getCategory());
        Assertions.assertEquals(rule.getParity(), response.getParity());
    }

    @Test
    @DisplayName("Service create a rule with existing category")
    void testCreateRuleWithExistingCategory() {
        final var request = RuleUtil.getRequest();

        when(this.ruleRepository.existsByCategoryIgnoreCase(request.getCategory())).thenReturn(true);
        Assertions.assertThrows(RuleAlreadyExistsException.class, () -> this.service.createRule(request));
    }

    @Test
    @DisplayName("Service get rules")
    void testGetRules() {
        final var rule = RuleUtil.getRule();
        final var pageable = new PageImpl<>(List.of(rule));

        when(this.ruleRepository.findAll(any())).thenReturn(pageable);
        final var response = this.service.getRules(mock(Pageable.class));

        Assertions.assertNotNull(response.getMetadata());
        Assertions.assertEquals(1, response.getMetadata().totalElements());
        Assertions.assertEquals(rule.getId(), response.getContent().get(0).getId());
        Assertions.assertEquals(rule.getCategory(), response.getContent().get(0).getCategory());
        Assertions.assertEquals(rule.getParity(), response.getContent().get(0).getParity());
    }

    @Test
    @DisplayName("Service get rule by id")
    void testGetRule() {
        final var rule = RuleUtil.getRule();

        when(this.ruleRepository.findById(rule.getId())).thenReturn(Optional.of(rule));
        final var response = this.service.getRule(rule.getId());

        Assertions.assertEquals(rule.getId(), response.getId());
        Assertions.assertEquals(rule.getCategory(), response.getCategory());
        Assertions.assertEquals(rule.getParity(), response.getParity());
    }

    @Test
    @DisplayName("Service get rule by id not found")
    void testGetRuleNotFound() {
        final var rule = RuleUtil.getRule();

        when(this.ruleRepository.findById(rule.getId())).thenReturn(Optional.empty());
        Assertions.assertThrows(RuleNotFoundException.class, () -> this.service.getRule(rule.getId()));
    }

    @Test
    @DisplayName("Service update rule")
    void testUpdateRule() {
        final var updateRule = RuleUtil.getUpdateRule();
        final var updateRequest = RuleUtil.getUpdateRequest();

        when(this.ruleRepository.findById(updateRule.getId())).thenReturn(Optional.of(RuleUtil.getRule()));
        when(this.ruleRepository.existsByCategory(updateRule.getId(), updateRequest.getCategory())).thenReturn(false);
        when(this.ruleRepository.save(any())).thenReturn(updateRule);

        final var response = this.service.updateRule(updateRule.getId(), updateRequest);

        Assertions.assertEquals(updateRule.getId(), response.getId());
        Assertions.assertEquals(updateRule.getCategory(), response.getCategory());
        Assertions.assertEquals(updateRule.getParity(), response.getParity());
    }

    @Test
    @DisplayName("Service update rule with existing category")
    void testUpdateRuleWithExistingCategory() {
        final var updateRule = RuleUtil.getUpdateRule();
        final var updateRequest = RuleUtil.getUpdateRequest();

        when(this.ruleRepository.findById(updateRule.getId())).thenReturn(Optional.of(RuleUtil.getRule()));
        when(this.ruleRepository.existsByCategory(updateRule.getId(), updateRequest.getCategory())).thenReturn(true);

        Assertions.assertThrows(RuleAlreadyExistsException.class, () -> this.service.updateRule(updateRule.getId(), updateRequest));
    }

    @Test
    @DisplayName("Service update rule not found")
    void testUpdateRuleNotFound() {
        final var updateRule = RuleUtil.getUpdateRule();
        final var updateRequest = RuleUtil.getUpdateRequest();

        when(this.ruleRepository.findById(updateRule.getId())).thenReturn(Optional.empty());

        Assertions.assertThrows(RuleNotFoundException.class, () -> this.service.updateRule(updateRule.getId(), updateRequest));
    }

    @Test
    @DisplayName("Service delete rule")
    void testDeleteRule() {
        final var rule = RuleUtil.getRule();

        when(this.ruleRepository.deleteRuleById(rule.getId())).thenReturn(1);
        this.service.deleteRule(rule.getId());
    }

    @Test
    @DisplayName("Service delete rule not found")
    void testDeleteRuleNotFound() {
        final var rule = RuleUtil.getRule();

        when(this.ruleRepository.deleteRuleById(rule.getId())).thenReturn(0);
        Assertions.assertThrows(RuleNotFoundException.class, () -> this.service.deleteRule(rule.getId()));
    }

}