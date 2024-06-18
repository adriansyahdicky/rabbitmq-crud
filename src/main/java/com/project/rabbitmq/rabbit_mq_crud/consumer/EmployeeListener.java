package com.project.rabbitmq.rabbit_mq_crud.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.rabbitmq.rabbit_mq_crud.config.MessagingConfig;
import com.project.rabbitmq.rabbit_mq_crud.dto.EmployeeDTO;
import com.project.rabbitmq.rabbit_mq_crud.services.EmployeeServices;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.core.Message;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class EmployeeListener {

    private final EmployeeServices services;
    private final ObjectMapper objectMapper;

    @RabbitListener(queues = MessagingConfig.QUEUE)
    public void insert(Message message,
                       @Header("CONSUMER_OPERATION") String operation) {
        try {
            byte[] body = message.getBody();
            EmployeeDTO employeeDTO = objectMapper.readValue(body, EmployeeDTO.class);
            switch (operation) {
                case "insert":
                    services.insert(employeeDTO);
                    break;
                case "update":
                    services.update(employeeDTO);
                    break;
                case "delete":
                    services.delete(employeeDTO);
                    break;
                default:
                    log.warn("EmployeeListener.listen has an invalid CONSUMER_OPERATION header {}", operation);
                    break;
            }
        } catch (Exception e) {
            log.error("Error processing message: {}", e.getMessage());
        }
    }

}
