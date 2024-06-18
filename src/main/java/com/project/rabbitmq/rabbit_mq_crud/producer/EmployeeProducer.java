package com.project.rabbitmq.rabbit_mq_crud.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.rabbitmq.rabbit_mq_crud.config.MessagingConfig;
import com.project.rabbitmq.rabbit_mq_crud.dto.EmployeeDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
@Slf4j
public class EmployeeProducer {

    private final RabbitTemplate template;
    private final ObjectMapper objectMapper;

    public void insert(EmployeeDTO employee) {
        try {
            byte[] employeeAsBytes = objectMapper.writeValueAsBytes(employee);
            MessageProperties messageProperties = new MessageProperties();
            messageProperties.setHeader("CONSUMER_OPERATION", "insert");
            Message message = MessageBuilder.withBody(employeeAsBytes)
                    .andProperties(messageProperties)
                    .build();
            template.send(MessagingConfig.EXCHANGE, MessagingConfig.ROUTING_KEY, message);
            log.info("Request insert employee --> {}", employee);
        } catch (Exception e) {
            log.error("Error while serializing employee object", e);
        }
    }

    public void update(String employeeId, EmployeeDTO employee) {
        try {
            employee.setEmployeeId(employeeId);
            byte[] employeeAsBytes = objectMapper.writeValueAsBytes(employee);
            MessageProperties messageProperties = new MessageProperties();
            messageProperties.setHeader("CONSUMER_OPERATION", "update");
            Message message = MessageBuilder.withBody(employeeAsBytes)
                    .andProperties(messageProperties)
                    .build();
            template.send(MessagingConfig.EXCHANGE, MessagingConfig.ROUTING_KEY, message);
            log.info("Request update employee --> {}", employee);
        } catch (Exception e) {
            log.error("Error while serializing employee object", e);
        }
    }

    public void delete(String employeeId) {
        try {
            EmployeeDTO employee = new EmployeeDTO();
            employee.setEmployeeId(employeeId);
            byte[] employeeAsBytes = objectMapper.writeValueAsBytes(employee);
            MessageProperties messageProperties = new MessageProperties();
            messageProperties.setHeader("CONSUMER_OPERATION", "delete");
            Message message = MessageBuilder.withBody(employeeAsBytes)
                    .andProperties(messageProperties)
                    .build();
            template.send(MessagingConfig.EXCHANGE, MessagingConfig.ROUTING_KEY, message);
            log.info("Request delete employee --> {}", employee);
        } catch (Exception e) {
            log.error("Error while serializing employee object", e);
        }
    }
}
