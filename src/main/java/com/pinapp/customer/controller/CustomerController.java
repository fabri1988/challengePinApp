package com.pinapp.customer.controller;


import com.pinapp.customer.dto.CustomerResponse;
import com.pinapp.customer.model.Customer;
import com.pinapp.customer.service.CustomerService;
import io.micrometer.core.annotation.Timed;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/customers")
public class CustomerController {

    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @PostMapping
    @Timed(value = "customerController.create")
    public ResponseEntity<Customer> create(@RequestBody Customer customer) {
        Customer newCustomer = service.createCustomer(customer);
        return new ResponseEntity<>(newCustomer, HttpStatus.CREATED);
    }

    @GetMapping
    @Timed(value = "customerController.list")
    public ResponseEntity<CustomerResponse> listCustomers() {
        return ResponseEntity.ok(service.listCustomers());
    }

}