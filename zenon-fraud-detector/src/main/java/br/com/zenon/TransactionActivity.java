package br.com.zenon;

import java.math.BigDecimal;

public record TransactionActivity(String name
        , BigDecimal oldBalanceOrg
        , BigDecimal newBalanceOrig) {
}
