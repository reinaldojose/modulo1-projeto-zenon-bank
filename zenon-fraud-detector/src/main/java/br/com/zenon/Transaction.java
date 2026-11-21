package br.com.zenon;

import java.math.BigDecimal;
import java.util.Objects;

public record Transaction(int step
        , TransactionType type
        , BigDecimal amount
        , TransactionActivity origin
        , TransactionActivity recipient
        , boolean isFraud
        , boolean isFlaggedFraud) {
    public Transaction {
        Objects.requireNonNull(type, "O tipo da transação (type) não pode ser nulo");
        Objects.requireNonNull(amount, "O valor (amount) não pode ser nulo");
        Objects.requireNonNull(origin, "A origem (origin) não pode ser nula");
        Objects.requireNonNull(recipient, "O destinatário (recipient) não pode ser nulo");

        if (step < 1) {
            throw new IllegalArgumentException("O step deve ser igual ou maior que 1");
        }

        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("O valor (amount) não pode ser negativo");
        }

        if (amount.compareTo(BigDecimal.ZERO) < 0 && amount != null) {
            throw new IllegalArgumentException("O valor (amount) não pode ser negativo");
        }
    }
}
