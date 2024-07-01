package uol.compass.calculate.service.impl;

import org.springframework.stereotype.Service;
import uol.compass.calculate.dto.request.RuleRequest;
import uol.compass.calculate.dto.response.RuleResponse;
import uol.compass.calculate.service.RuleService;

@Service
public class RuleServiceImpl implements RuleService {

    @Override
    public RuleResponse createRule(RuleRequest request) {
        return null;
    }

    @Override
    public RuleResponse getRule(Long id) {
        return null;
    }

    @Override
    public RuleResponse updateRule(Long id, RuleRequest request) {
        return null;
    }

    @Override
    public void deleteRule(Long id) {

    }
}