package com.donnatto.pc04.bank.service;

import com.donnatto.pc04.bank.model.Payment;

import java.util.List;
import java.util.UUID;

public interface SqsService {
    List<String> listQueues();
    String sendMessage(Payment payment);
    List<Payment> receiveMessages();
}
