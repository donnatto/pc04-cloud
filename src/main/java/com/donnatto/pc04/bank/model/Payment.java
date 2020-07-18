package com.donnatto.pc04.bank.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    @Nullable
    private UUID transactionId = UUID.randomUUID();
    @JsonProperty("documentNumber")
    private String documentNumber;
    @JsonProperty("firstName")
    private String firstName;
    @JsonProperty("lastName")
    private String lastName;
    @JsonProperty("payment")
    private String payment;
    @JsonProperty("transactionAmount")
    private float transactionAmount;
    @JsonProperty("transactionDate")
    private String transactionDate;

}
