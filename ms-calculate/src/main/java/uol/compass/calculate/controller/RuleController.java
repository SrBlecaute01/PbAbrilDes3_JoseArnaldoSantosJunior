package uol.compass.calculate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import uol.compass.calculate.dto.request.RuleRequest;
import uol.compass.calculate.dto.response.RuleResponse;
import uol.compass.calculate.service.RuleService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("v1/rules")
public class RuleController {

    private final RuleService service;

    @PostMapping
    public ResponseEntity<RuleResponse> createRule(@Valid @RequestBody RuleRequest request) {
        final var rule = this.service.createRule(request);
        final var uri = UriComponentsBuilder.fromPath("/v1/rules/{id}").buildAndExpand(rule.getId()).toUri();
        return ResponseEntity.created(uri).body(rule);
    }

    @GetMapping("/{id}")
    public RuleResponse getRule(@PathVariable Long id) {
        return this.service.getRule(id);
    }

    @PutMapping("/{id}")
    public RuleResponse updateRule(@PathVariable Long id, @Valid @RequestBody RuleRequest request) {
        return this.service.updateRule(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRule(@PathVariable Long id) {
        this.service.deleteRule(id);
        return ResponseEntity.noContent().build();
    }

}