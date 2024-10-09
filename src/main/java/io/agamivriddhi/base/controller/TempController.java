package io.agamivriddhi.base.controller;

import io.agamivriddhi.base.entity.Customer;
import io.agamivriddhi.base.repository.CustomerRepository;
import io.agamivriddhi.base.service.TempService;
import jakarta.annotation.PostConstruct;
import org.jmolecules.architecture.layered.ApplicationLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@ApplicationLayer
public class TempController {

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

    @GetMapping("/customers")
    public List<Customer> getAllUsers() {
        return customerRepository.findAll();
    }

}
