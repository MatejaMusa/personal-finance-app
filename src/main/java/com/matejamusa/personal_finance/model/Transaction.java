package com.matejamusa.personal_finance.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class Transaction {
    private Long id;
    private Long accountId;
    private Long categoryId;
    private Double amount;
    private LocalDateTime transactionDate;
}
