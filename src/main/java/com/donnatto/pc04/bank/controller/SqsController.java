package com.donnatto.pc04.bank.controller;

import com.donnatto.pc04.bank.model.Payment;
import com.donnatto.pc04.bank.service.SqsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sqs")
@Slf4j
public class SqsController {

    SqsService service;

    public SqsController(SqsService service) {
        this.service = service;
    }

    @GetMapping("/queues")
    public List<String> getQueues() {
        return service.listQueues();
    }

    @PostMapping("/payment")
    public String payOrder(@RequestBody Payment payment) {
        log.info("Payment: {}", payment);
        return service.sendMessage(payment);
    }

    @GetMapping("/payment")
    public List<Payment> getPayments() {
        log.info("About to receive payments");
        return service.receiveMessages();
    }



}
