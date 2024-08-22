package com.matejamusa.personal_finance.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GraphType {
    EXPENSE("EXPENSE"),
    INCOME("INCOME");

    private final String value;
}