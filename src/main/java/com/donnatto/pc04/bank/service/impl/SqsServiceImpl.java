package com.donnatto.pc04.bank.service.impl;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.*;
import com.donnatto.pc04.bank.model.Payment;
import com.donnatto.pc04.bank.service.SqsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class SqsServiceImpl implements SqsService {

    @Value("${AWS_QUEUE_URL}")
    String queueUrl;

    private ObjectMapper mapper;
    private AmazonSQS sqsClient;

    @Autowired
    public SqsServiceImpl(AmazonSQS sqsClient, ObjectMapper mapper) {
        this.sqsClient = sqsClient;
        this.mapper = mapper;
    }

    @Override
    public List<String> listQueues() {
        log.info("Executing listQueues method");
        ListQueuesResult result = sqsClient.listQueues();
        return result.getQueueUrls();
    }

    @Override
    public String sendMessage(Payment payment) {

        String response = null;

        log.info("Executing send message");
        try {
            String messageBody = mapper.writeValueAsString(payment);
            log.info("message body: {}", messageBody);
            SendMessageRequest request = new SendMessageRequest()
                    .withQueueUrl(queueUrl)
                    .withMessageBody(messageBody);
            sqsClient.sendMessage(request);
            log.info("Order received");
            response = "Order received";
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            response = "Error processing order";
        }

        return response;
    }

    @Override
    public List<Payment> receiveMessages() {
        List<Payment> payments = new ArrayList<>();

        log.info("Executing receiveMessages method");
        try {
            ReceiveMessageRequest request = new ReceiveMessageRequest()
                    .withQueueUrl(queueUrl)
                    .withMaxNumberOfMessages(10);
            ReceiveMessageResult result = sqsClient.receiveMessage(request);
            log.info("result received");
            List<Message> messages = result.getMessages();
            log.info("About to log messages");
            for (Message message :
                    messages) {
                String body = message.getBody();
                log.info(body);
                payments.add(mapper.readValue(body, Payment.class));
                log.info("message: {}", body);
            }
        } catch (Exception e) {
            log.error("Couldn't receive messages: {}", e.getMessage());
        }

        return payments;
    }
}
