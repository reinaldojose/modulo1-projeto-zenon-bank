package br.com.zenon;

import java.math.BigDecimal;

public record Transaction(int step
        , TransactionType type
        , BigDecimal amount
        , TransactionActivity origin
        , TransactionActivity recipient
        , boolean isFraud
        , boolean isFlaggedFraud) {}
