package uol.compass.calculate.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import uol.compass.calculate.repository.RuleRepository;
import uol.compass.calculate.service.impl.CalculateServiceImpl;
import uol.compass.calculate.util.CalculateUtil;
import uol.compass.calculate.util.RuleUtil;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@DisplayName("Tests for Calculate Service")
public class CalculateServiceTest {

    private CalculateService service;

    @Mock
    private RuleRepository ruleRepository;

    @BeforeEach
    void setup() {
        this.service = new CalculateServiceImpl(this.ruleRepository);
    }

    @Test
    @DisplayName("Service calculate with a existing rule.")
    void testCalculateWithValidRule() {
        final var rule = RuleUtil.getRule();
        final var request = CalculateUtil.getRequest();

        when(this.ruleRepository.findById(rule.getId())).thenReturn(Optional.of(rule));
        final var response = this.service.calculate(request);

        Assertions.assertEquals(request.getValue() * rule.getParity(), response.getTotal());
    }

    @Test
    @DisplayName("Service calculate with a non-existing rule.")
    void testCalculateWithInvalidRule() {
        final var request = CalculateUtil.getRequest();
        when(this.ruleRepository.findById(request.getCategoryId())).thenReturn(Optional.empty());

        final var response = this.service.calculate(request);

        Assertions.assertEquals(request.getValue(), response.getTotal());
    }

}
