package io.agamivriddhi.base.controller;

import io.agamivriddhi.base.entity.Customer;
import io.agamivriddhi.base.repository.CustomerRepository;
import io.agamivriddhi.base.service.TempService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.jmolecules.architecture.layered.ApplicationLayer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@ApplicationLayer
@Slf4j
public class TempController {

    Logger LOGGER = LoggerFactory.getLogger(TempController.class);

    @Value(value = "${welcome.msg}")
    private String text;

    @Value(value = "${user.role}")
    private String role;

    TempService tempService;
    CustomerRepository customerRepository;

    // decide whether to Autowire the constructor or not
    @Autowired
    TempController(TempService tempService,
                   CustomerRepository customerRepository) {
        this.tempService = tempService;
        this.customerRepository = customerRepository;
    }

    @PostConstruct
    public void testSpringConfig() {
        System.out.println("MESSAGE " + text);
        System.out.println("ROLE " + role);
    }

    @GetMapping(value = "/ping")
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok(tempService.testServiceCall());
    }

    @Operation(summary = "Fetch all customers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fetched all customers",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Customer.class)) })
                    })
    @GetMapping("/customers")
    public List<Customer> getAllUsers() {
        LOGGER.info("Entering controller getAllUsers");
        return customerRepository.findAll();
    }

}
