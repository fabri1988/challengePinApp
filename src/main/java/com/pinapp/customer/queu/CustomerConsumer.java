package com.pinapp.customer.queu;

import com.pinapp.customer.model.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CustomerConsumer {
    @RabbitListener(queues = "notifications")
    public void receive(Customer customer) {
        log.info("Message received with customerId: {}", customer.getId());
    }
}
