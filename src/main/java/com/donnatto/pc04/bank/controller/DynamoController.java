package com.donnatto.pc04.bank.controller;

import com.donnatto.pc04.bank.model.Payment;
import com.donnatto.pc04.bank.service.DynamoService;
import com.donnatto.pc04.bank.service.SqsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/dynamo")
@Slf4j
public class DynamoController {

    private DynamoService dbService;
    private SqsService sqsService;
    private ObjectMapper mapper;

    @Autowired
    public DynamoController(DynamoService dbService, SqsService sqsService, ObjectMapper mapper) {
        this.dbService = dbService;
        this.sqsService = sqsService;
        this.mapper = mapper;
    }

    @PostMapping("/process")
    public String processPayments() {
        String response = null;

        log.info("Initiating payment processing");
        try {
            List<Payment> payments = sqsService.receiveMessages();
            log.info("loading payment data to table");
            for (Payment payment:
                 payments) {
                dbService.loadData(payment);
                log.info("loaded one payment");
            }
            response = "Payments received";
        } catch (Exception e) {
            log.error("Error processing payments: {}", e.getMessage());
            response = "Error processing payments";
        }

        return response;
    }
}
