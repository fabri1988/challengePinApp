package com.pinapp.customer.service;

import com.pinapp.customer.dto.CustomerResponse;
import com.pinapp.customer.model.Customer;
import com.pinapp.customer.queu.CustomerProducer;
import com.pinapp.customer.repository.CustomerRepository;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Mock
    private CustomerRepository repository;

    @Mock
    private CustomerProducer producer;

    @InjectMocks
    private CustomerService customerService;


    @BeforeEach
    void setUp() {
        MeterRegistry meterRegistry = new SimpleMeterRegistry();
        customerService = new CustomerService(repository, producer, meterRegistry);
    }

    @Test
    void shouldCreateCustomerAndSendNotification() {
        Customer newCustomer = Customer.builder()
                .id(1L)
                .name("Juan")
                .surname("Perez")
                .dateOfBirth(LocalDate.of(1990, 5, 15))
                .build();

        when(repository.save(any(Customer.class))).thenReturn(newCustomer);

        ArgumentCaptor<Customer> customerCaptor = ArgumentCaptor.forClass(Customer.class);

        Customer customerCreated = customerService.createCustomer(newCustomer);

        verify(repository, times(1)).save(newCustomer);

        verify(producer, times(1)).sendNotification(customerCaptor.capture());
        assertEquals(customerCreated, customerCaptor.getValue());


        assertNotNull(newCustomer.getId());
        assertEquals("Juan", newCustomer.getName());
        assertEquals("Perez", newCustomer.getSurname());
        assertEquals(LocalDate.of(1990, 5, 15), newCustomer.getDateOfBirth());
        assertEquals(35, newCustomer.getAge());
    }

    @Test
    void shoutGetAllCustomers() {
        Customer customer1 = Customer.builder().id(1L).name("Juan").surname("Martinez").dateOfBirth(LocalDate.of(1990, 1, 1)).build();
        Customer customer2 = Customer.builder().id(2L).name("Marcelo").surname("Rodriguez").dateOfBirth(LocalDate.of(2000, 1, 1)).build();
        Customer customer3 = Customer.builder().id(3L).name("Tomas").surname("Moyano").dateOfBirth(LocalDate.of(1995, 1, 1)).build();

        List<Customer> mockCustomers = Arrays.asList(customer1, customer2, customer3);

        when(repository.findAll()).thenReturn(mockCustomers);

        CustomerResponse response = customerService.listCustomers();


        verify(repository, times(1)).findAll();

        assertNotNull(response.getCustomers());
        assertEquals(3, response.getCustomers().size());
        assertEquals("Juan", response.getCustomers().getFirst().getName());

        assertEquals(30, response.getAverageAge());
    }
}
