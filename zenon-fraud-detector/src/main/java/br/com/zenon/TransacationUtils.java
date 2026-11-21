package br.com.zenon;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.Optional;

public class TransacationUtils {

    public Path readFile(String fileName, String path) {
        return Path.of(path.concat(fileName));
    }

    public Optional<Transaction> createTr(String line) {
        try {
            var col = line.split(",",-1);
            return Optional.of(new Transaction(Integer.parseInt(col[0])
                    , TransactionType.valueOf(col[1])
                    , newBigDecimal(col[2])
                    , new TransactionActivity(col[3], newBigDecimal(col[4]), newBigDecimal(col[5]) )
                    , new TransactionActivity(col[6], newBigDecimal(col[7]), newBigDecimal(col[8]) )
                    , newBoolean((col[9]))
                    , newBoolean((col[10]))));
        } catch(Exception e){
            System.err.println("Erro na linha [" + line + "]: " + e.getMessage());
            return Optional.empty();
        }
    }

    private BigDecimal newBigDecimal(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("O valor para BigDecimal não pode ser nulo ou vazio.");
        }
        return new BigDecimal(value.trim());
    }

    private boolean newBoolean(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("O flag não pode ser nulo ou vazio.");
        }
        return switch (value.trim()) {
            case "1" -> true;
            case "0" -> false;
            default -> throw new IllegalArgumentException("O flag deve ser '0' ou '1'.");
        };
    }

}
