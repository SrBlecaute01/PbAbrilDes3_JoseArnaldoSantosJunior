package uol.compass.calculate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uol.compass.calculate.dto.request.CalculateRequest;
import uol.compass.calculate.dto.response.CalculateResponse;
import uol.compass.calculate.service.CalculateService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("v1/calculate")
public class CalculateController {

    private final CalculateService service;

    @PostMapping
    public CalculateResponse calculate(@Valid @RequestBody CalculateRequest request) {
        return this.service.calculate(request);
    }

}