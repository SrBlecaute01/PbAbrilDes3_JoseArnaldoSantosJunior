package uol.compass.calculate.service;

import uol.compass.calculate.dto.request.RuleRequest;
import uol.compass.calculate.dto.response.RuleResponse;

public interface RuleService {

    RuleResponse createRule(RuleRequest request);

    RuleResponse getRule(Long id);

    RuleResponse updateRule(Long id, RuleRequest request);

    void deleteRule(Long id);

}