package com.pinapp.customer.queu;

import com.pinapp.customer.model.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CustomerProducer {
    @Autowired
    private AmqpTemplate amqpTemplate;

    public void sendNotification(Customer customer) {
        log.info("Sending message to notification queue for customerId:{} and name {} ", customer.getId(), customer.getName());
        amqpTemplate.convertAndSend("notifications", customer);
    }
}
