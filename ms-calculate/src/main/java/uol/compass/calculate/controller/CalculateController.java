package uol.compass.calculate.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uol.compass.calculate.dto.request.CalculateRequest;
import uol.compass.calculate.dto.response.CalculateResponse;
import uol.compass.calculate.exception.ApplicationException;
import uol.compass.calculate.service.CalculateService;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/calculate")
@Tag(name = "Calculate", description = "Operations responsible for calculating a value established in the rules.")
public class CalculateController {

    private final CalculateService service;

    @Operation(summary = "Calculate a value.", description = "Calculate a value based on the rules and category.")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The request body to calculate a value.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Value calculated successfully.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CalculateResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApplicationException.class))),
            @ApiResponse(responseCode = "422", description = "Invalid input data.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApplicationException.class))),
    })
    @PostMapping
    public CalculateResponse calculate(@Valid @RequestBody CalculateRequest request) {
        return this.service.calculate(request);
    }

}