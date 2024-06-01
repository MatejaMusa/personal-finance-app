package com.matejamusa.personal_finance.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class Account {
    private Long id;
    private Long userId;
    private String name;
    private String description;
    private String currency;
    private String priority;
    private float balance;
}
