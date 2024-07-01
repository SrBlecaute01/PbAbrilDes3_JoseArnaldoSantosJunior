package uol.compass.calculate.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uol.compass.calculate.dto.request.CalculateRequest;
import uol.compass.calculate.dto.response.CalculateResponse;
import uol.compass.calculate.model.Rule;
import uol.compass.calculate.repository.RuleRepository;
import uol.compass.calculate.service.CalculateService;

@Service
@RequiredArgsConstructor
public class CalculateServiceImpl implements CalculateService {

    private final RuleRepository repository;

    @Override
    public CalculateResponse calculate(CalculateRequest request) {
        final var parity = this.repository.findById(request.getCategoryId()).map(Rule::getParity).orElse(1);
        final var result = request.getValue() * parity;
        return new CalculateResponse(result);
    }

}