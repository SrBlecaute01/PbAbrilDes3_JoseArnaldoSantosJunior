package uol.compass.customer.controller;

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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import uol.compass.customer.dto.request.CustomerRequest;
import uol.compass.customer.dto.response.CustomerResponse;
import uol.compass.customer.exception.ApplicationException;
import uol.compass.customer.service.CustomerService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("v1/customers")
@Tag(name = "Customer", description = "Operations responsible for manage customers.")
@ApiResponses(value = {
        @ApiResponse(responseCode = "400", description = "Bad Request",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApplicationException.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApplicationException.class)))
})
public class CustomerController {

    private final CustomerService service;

    @Operation(summary = "Create a customer.", description = "Create a customer.")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The request body to create a customer.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Customer created successfully.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerResponse.class))),
            @ApiResponse(responseCode = "409", description = "Customer with this cpf or email already exists.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApplicationException.class))),
            @ApiResponse(responseCode = "422", description = "Method argument not valid.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApplicationException.class))),
    })
    @PostMapping
    public ResponseEntity<CustomerResponse> createCustomer(@Valid @RequestBody CustomerRequest request) {
        final var customer = this.service.createCustomer(request);
        final var uri = UriComponentsBuilder.fromPath("/v1/customers/{id}").buildAndExpand(customer.getId()).toUri();
        return ResponseEntity.created(uri).body(customer);
    }

    @Operation(summary = "Get a customer.", description = "Get a customer by id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer found successfully.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerResponse.class))),
            @ApiResponse(responseCode = "404", description = "Customer not found.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApplicationException.class))),
    })
    @Parameters({@Parameter(name = "id", description = "The customer id.", example = "1")})
    @GetMapping("/{id}")
    public CustomerResponse getCustomer(@PathVariable Long id) {
        return this.service.getCustomer(id);
    }

    @Operation(summary = "Update a customer.", description = "Update a customer by id.")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The request body to update a customer.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer updated successfully.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerResponse.class))),
            @ApiResponse(responseCode = "404", description = "Customer not found.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApplicationException.class))),
            @ApiResponse(responseCode = "409", description = "Customer with this cpf or email already exists.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApplicationException.class))),
            @ApiResponse(responseCode = "422", description = "Method argument not valid.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApplicationException.class))),
    })
    @Parameters({@Parameter(name = "id", description = "The customer id.", example = "1")})
    @PutMapping("/{id}")
    public CustomerResponse updateCustomer(@PathVariable Long id, @Valid @RequestBody CustomerRequest request) {
        return this.service.updateCustomer(id, request);
    }

    @Operation(summary = "Delete a customer.", description = "Delete a customer by id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Customer deleted successfully.",
                    content = @Content(mediaType = "application/json", schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "404", description = "Customer not found.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApplicationException.class))),
    })
    @Parameters({@Parameter(name = "id", description = "The customer id.", example = "1")})
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Long id) {
        this.service.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }

}