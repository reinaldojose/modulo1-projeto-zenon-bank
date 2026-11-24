package br.com.zenon;

import java.util.Optional;

public interface TransactionRepository {
    Optional<Transaction> findByNameOrigin(String name);

    Optional<Transaction> findByNameRecipient(String name);
}
