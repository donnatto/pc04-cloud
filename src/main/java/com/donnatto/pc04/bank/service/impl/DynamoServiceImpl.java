package com.donnatto.pc04.bank.service.impl;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.donnatto.pc04.bank.model.Payment;
import com.donnatto.pc04.bank.service.DynamoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DynamoServiceImpl implements DynamoService {

    @Value("${AWS_TABLE_NAME}")
    String tableName;

    private ObjectMapper mapper;
    private DynamoDB db;

    @Autowired
    public DynamoServiceImpl(ObjectMapper mapper, DynamoDB db) {
        this.mapper = mapper;
        this.db = db;
    }

    @Override
    public void loadData(Payment payment) {
        try {
            Table table = db.getTable(tableName);

            Item item = new Item().withPrimaryKey("transactionId", payment.getTransactionId().toString())
                    .withString("documentNumber", payment.getDocumentNumber())
                    .withString("firstName", payment.getFirstName())
                    .withString("lastName", payment.getLastName())
                    .withString("payment", payment.getPayment())
                    .withFloat("transactionAmount", payment.getTransactionAmount())
                    .withString("transactionDate", payment.getTransactionDate());
            table.putItem(item);

            log.info("Added to table: {}", tableName);

        } catch (Exception e) {
            log.error("Error: {}", e.getMessage());
        }
    }
}
