package br.com.zenon;

import java.math.BigDecimal;
import java.util.Objects;

public record TransactionActivity(String name
        , BigDecimal oldBalanceOrg
        , BigDecimal newBalanceOrig) {
    public TransactionActivity{
        Objects.requireNonNull(name, "O name da transação (name) não pode ser nulo");
        Objects.requireNonNull(oldBalanceOrg, "O oldBalanceOrg da transação (oldBalanceOrg não pode ser nulo");
        Objects.requireNonNull(newBalanceOrig, "O newBalanceOrig (newBalanceOrig) não pode ser nulo");

        if (oldBalanceOrg.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("O valor (oldBalanceOrg) não pode ser negativo");
        }

        if (newBalanceOrig.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("O valor (newBalanceOrig) não pode ser negativo");
        }
    }
}
