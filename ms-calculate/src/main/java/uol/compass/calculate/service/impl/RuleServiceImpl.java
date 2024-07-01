package uol.compass.calculate.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;
import uol.compass.calculate.dto.request.RuleRequest;
import uol.compass.calculate.dto.response.RuleResponse;
import uol.compass.calculate.exception.rule.RuleAlreadyExistsException;
import uol.compass.calculate.exception.rule.RuleNotFoundException;
import uol.compass.calculate.model.Rule;
import uol.compass.calculate.repository.RuleRepository;
import uol.compass.calculate.service.RuleService;

@Service
@RequiredArgsConstructor
public class RuleServiceImpl implements RuleService {

    private final RuleRepository repository;
    private final ModelMapper mapper;

    @Override
    public RuleResponse createRule(RuleRequest request) {
        if (this.repository.existsByCategoryIgnoreCase(request.getCategory())) {
            throw new RuleAlreadyExistsException();
        }

        final var rule = this.mapper.map(request, Rule.class);
        return this.mapper.map(this.repository.save(rule), RuleResponse.class);
    }

    @Override
    public PagedModel<RuleResponse> getRules(Pageable pageable) {
        return new PagedModel<>(this.repository.findAll(pageable)
                .map(rule -> this.mapper.map(rule, RuleResponse.class)));
    }

    @Override
    public RuleResponse getRule(Long id) {
        return this.repository.findById(id)
                .map(rule -> this.mapper.map(rule, RuleResponse.class))
                .orElseThrow(RuleNotFoundException::new);
    }

    @Override
    public RuleResponse updateRule(Long id, RuleRequest request) {
        final var rule = this.repository.findById(id).orElseThrow(RuleNotFoundException::new);
        if (this.repository.existsByCategory(id, request.getCategory())) {
            throw new RuleAlreadyExistsException();
        }

        rule.setCategory(request.getCategory());
        rule.setParity(request.getParity());

        return this.mapper.map(this.repository.save(rule), RuleResponse.class);
    }

    @Override
    public void deleteRule(Long id) {
        final var affected = this.repository.deleteRuleById(id);
        if (affected <= 0) throw new RuleNotFoundException();
    }

}