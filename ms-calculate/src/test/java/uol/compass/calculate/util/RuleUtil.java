package uol.compass.calculate.util;

import lombok.Getter;
import lombok.experimental.UtilityClass;
import uol.compass.calculate.dto.request.RuleRequest;
import uol.compass.calculate.dto.response.RuleResponse;
import uol.compass.calculate.model.Rule;

@UtilityClass
public class RuleUtil {

    @Getter
    private static final Rule rule = new Rule(1L, "electronics", 10);

    @Getter
    private static final Rule updateRule = new Rule(1L, "games", 20);

    @Getter
    private static final RuleRequest request = new RuleRequest(rule.getCategory(), rule.getParity());

    @Getter
    private static final RuleRequest updateRequest = new RuleRequest(updateRule.getCategory(), updateRule.getParity());

    @Getter
    private static final RuleResponse response = new RuleResponse(rule.getId(), rule.getCategory(), rule.getParity());

    @Getter
    private static final RuleResponse updateResponse = new RuleResponse(rule.getId(), updateRule.getCategory(), updateRule.getParity());

}