package uol.compass.payments.controller;

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
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import uol.compass.payments.dto.request.PaymentRequest;
import uol.compass.payments.dto.response.PaymentResponse;
import uol.compass.payments.exception.ApplicationErrorMessage;
import uol.compass.payments.service.PaymentService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/payments")
@Tag(name = "Payment", description = "Operations responsible for manage payments.")
@ApiResponses(value = {
        @ApiResponse(responseCode = "400", description = "Bad Request",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApplicationErrorMessage.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApplicationErrorMessage.class)))
})
public class PaymentController {

    private final PaymentService service;

    @Operation(summary = "Create a payment.", description = "Create a payment.")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The request body to create a payment.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Payment created successfully.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PaymentResponse.class))),
            @ApiResponse(responseCode = "404", description = "Customer not found.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApplicationErrorMessage.class))),
            @ApiResponse(responseCode = "422", description = "Method argument not valid.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApplicationErrorMessage.class))),
    })
    @PostMapping
    public ResponseEntity<PaymentResponse> createPayment(@Valid @RequestBody PaymentRequest request) {
        final var payment = this.service.createPayment(request);
        final var uri = UriComponentsBuilder.fromPath("/v1/payments/{id}").buildAndExpand(payment.getId()).toUri();
        return ResponseEntity.created(uri).body(payment);
    }

    @Operation(summary = "Get a payment.", description = "Get a payment by id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Payment found successfully.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PaymentResponse.class))),
            @ApiResponse(responseCode = "404", description = "Payment not found.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApplicationErrorMessage.class))),
    })
    @Parameters({@Parameter(name = "id", description = "The payment id.", example = "1fe3886d-e941-4cd2-a2c4-19871e1695dd")})
    @GetMapping("/{id}")
    public PaymentResponse getPayment(@PathVariable("id") UUID id) {
        return service.getPayment(id);
    }

    @Operation(summary = "Get payments.", description = "Get payments of a customer.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Payments found successfully."),
            @ApiResponse(responseCode = "404", description = "Customer not found.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApplicationErrorMessage.class)))
    })
    @Parameters({@Parameter(name = "id", description = "The customer id.", example = "1")})
    @GetMapping("/user/{id}")
    public PagedModel<PaymentResponse> getPaymentsByCustomer(@PathVariable("id") Long customerId, @ParameterObject @PageableDefault Pageable pageable) {
        return service.getPaymentsByCustomer(customerId, pageable);
    }

}
