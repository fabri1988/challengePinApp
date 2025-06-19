package com.pinapp.customer.service;


import com.pinapp.customer.dto.CustomerResponse;
import com.pinapp.customer.model.Customer;
import com.pinapp.customer.queu.CustomerProducer;
import com.pinapp.customer.repository.CustomerRepository;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.OptionalDouble;

@Service
@Slf4j
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerProducer customerProducer;
    private final MeterRegistry registry;

    public CustomerService(CustomerRepository customerRepository, CustomerProducer customerProducer, MeterRegistry registry) {
        this.customerRepository = customerRepository;
        this.customerProducer = customerProducer;
        this.registry = registry;

    }

    public Customer createCustomer(Customer customer) {
        Customer newCustomer = customerRepository.save(customer);
        log.info("new Customer created with ID: {}", newCustomer.getId());
        customerProducer.sendNotification(newCustomer);
        registry.counter("customer.total").increment();
        return newCustomer;
    }

    public CustomerResponse listCustomers() {
        List<Customer> customers = customerRepository.findAll();
        log.info("Obtaining customers from the database. Customers size:{}", customers.size());


        OptionalDouble averageAgeOptional = customers.stream()
                .mapToInt(Customer::getAge)
                .average();

        double averageAge = averageAgeOptional.orElse(0.0);
        return CustomerResponse.builder()
                .customers(customers)
                .averageAge(averageAge)
                .build();
    }

}