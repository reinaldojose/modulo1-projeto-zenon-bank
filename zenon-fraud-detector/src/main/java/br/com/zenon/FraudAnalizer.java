package br.com.zenon;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FraudAnalizer extends TransacationUtils {

    private final List<Transaction> transactions;

    public FraudAnalizer() {
        var ti = new TransactionIngestor();
        this.transactions = ti.readNioMap();
    }

    public int quantityFrauds() {
        return (int) getFraudStream().count();
    }

    public List<BigDecimal> topFraudes(int quantity) {
        return getFraudStream()
                .map(Transaction::amount)
                .sorted(Comparator.reverseOrder())
                .limit(quantity)
                .toList();
    }

    public BigDecimal valorFraudes() {
           return getFraudStream()
                   .map(Transaction::amount)
                   .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public int fraudsPorTipo(String tipo) {
        return (int) getFraudStream()
                .filter(t -> t.type().equals(TransactionType.valueOf(tipo)))
                .count();
    }

    public List<String> suspiciousClient(int quantity) {
        return getFraudStream()
                .sorted(Comparator.comparing(Transaction::amount).reversed())
                .map(transaction -> transaction.origin().name())
                .distinct()
                .limit(quantity)
                .toList();
    }

    //Resposta da IA
    public List<String> clientesSuspeitos(int quantity){
        return getFraudStream()
                .filter(t -> t.origin() != null && t.origin().name() != null && t.amount() != null)
                .collect(Collectors.groupingBy(
                        t -> t.origin().name(),
                        Collectors.reducing(BigDecimal.ZERO, Transaction::amount, BigDecimal::add)
                ))
                .entrySet().stream()
                .sorted(Map.Entry.<String, BigDecimal>comparingByValue().reversed())
                .limit(quantity)
                .map(Map.Entry::getKey)
                .distinct()
                .toList();
    }

    private Stream<Transaction> getFraudStream() {
        return transactions.stream().filter(Transaction::isFraud);
    }
}
