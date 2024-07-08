package uol.compass.calculate.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import uol.compass.calculate.dto.request.RuleRequest;
import uol.compass.calculate.dto.response.RuleResponse;

public interface RuleService {

    RuleResponse createRule(RuleRequest request);

    PagedModel<RuleResponse> getRules(Pageable pageable);

    RuleResponse getRule(Long id);

    RuleResponse updateRule(Long id, RuleRequest request);

    void deleteRule(Long id);

}