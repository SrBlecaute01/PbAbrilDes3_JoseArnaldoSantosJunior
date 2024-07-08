package uol.compass.calculate.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import uol.compass.calculate.dto.request.RuleRequest;
import uol.compass.calculate.dto.response.RuleResponse;
import uol.compass.calculate.exception.ApplicationException;
import uol.compass.calculate.service.RuleService;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/rules")
@Tag(name = "Rules", description = "Operations responsible for manage calculate rules.")
@ApiResponses(value = {
        @ApiResponse(responseCode = "400", description = "Bad Request",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApplicationException.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApplicationException.class)))
})
public class RuleController {

    private final RuleService service;

    @Operation(summary = "Create a rule.", description = "Create a rule to calculate a value.")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The request body to create a rule.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Rule created successfully.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = RuleResponse.class))),
            @ApiResponse(responseCode = "409", description = "Rule with this category already exists.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApplicationException.class))),
            @ApiResponse(responseCode = "422", description = "Method argument not valid.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApplicationException.class))),
    })
    @PostMapping
    public ResponseEntity<RuleResponse> createRule(@Valid @RequestBody RuleRequest request) {
        final var rule = this.service.createRule(request);
        final var uri = UriComponentsBuilder.fromPath("/v1/rules/{id}").buildAndExpand(rule.getId()).toUri();
        return ResponseEntity.created(uri).body(rule);
    }

    @Operation(summary = "Get rules.", description = "Get all rules.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rules retrieved successfully."),
    })
    @GetMapping
    public PagedModel<RuleResponse> getRules(@ParameterObject @PageableDefault Pageable pageable) {
        return this.service.getRules(pageable);
    }

    @Operation(summary = "Get a rule.", description = "Get a rule by id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rule retrieved successfully.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = RuleResponse.class))),
            @ApiResponse(responseCode = "404", description = "Rule not found.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApplicationException.class))),
    })
    @Parameters({@Parameter(name = "id", description = "The rule id.", required = true, example = "1")})
    @GetMapping("/{id}")
    public RuleResponse getRule(@PathVariable Long id) {
        return this.service.getRule(id);
    }

    @Operation(summary = "Update a rule.", description = "Update a existing rule.")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The request body to update a rule.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rule updated successfully.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = RuleResponse.class))),
            @ApiResponse(responseCode = "404", description = "Rule not found.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApplicationException.class))),
            @ApiResponse(responseCode = "409", description = "Rule with this category already exists.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApplicationException.class))),
            @ApiResponse(responseCode = "422", description = "Method argument not valid.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApplicationException.class))),
    })
    @Parameters({@Parameter(name = "id", description = "The rule id.", required = true, example = "1")})
    @PutMapping("/{id}")
    public RuleResponse updateRule(@PathVariable Long id, @Valid @RequestBody RuleRequest request) {
        return this.service.updateRule(id, request);
    }

    @Operation(summary = "Delete a rule.", description = "Delete a rule by id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Rule deleted successfully.",
                    content = @Content(mediaType = "application/json", schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "404", description = "Rule not found.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApplicationException.class))),
    })
    @Parameters({@Parameter(name = "id", description = "The rule id.", required = true, example = "1")})
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRule(@PathVariable Long id) {
        this.service.deleteRule(id);
        return ResponseEntity.noContent().build();
    }

}