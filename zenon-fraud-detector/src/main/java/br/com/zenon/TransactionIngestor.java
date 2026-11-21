package br.com.zenon;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Stream;

public class TransactionIngestor {

    void main() {
        var path = "E:\\DEV\\faculdade\\data\\";
        var filename = "PS_20174392719_1491204439457_log.csv";

        try {
//            Usando JAVA NIO
//            readNio(filename, path);

//            Usando File InputStream
//            var lst = readFI(path.concat(filename));
//            lst.stream().limit(10).forEach(IO::println);

//            Usando File InputStream
            var lst = readNioMap(filename, path);
            lst.stream().limit(10).forEach(IO::println);
            
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //Método que eu criei
    private void readNio(String filename, String path) throws IOException{
        try(Stream<String> lines = Files.lines(readFile(filename, path))){
            List<String> linhas = lines
                    .limit(1001)
                    .toList();

            List<Transaction> lstTransaction = new ArrayList<>();

            var cont = 0;
            for(var col : linhas){
                cont++;
                if(cont == 1) continue;
                var tr = createTr(col);
                lstTransaction.add(tr.orElse(null));
            }

            cont = 0;
            for(var transaction : lstTransaction){
                IO.println(transaction);
                cont++;
                if(cont == 10) break;
            }
        }
    }

    //Método resposta da aula
    public List<Transaction> readNioMap(String filename, String path) {

        var pathFilename = readFile(filename, path);

        try{
            List<String> lines = Files.readAllLines(pathFilename);
            return lines.stream()
                    .skip(1)
                    .limit(1000)
//                    .<Transaction>mapMulti((line, consumer) -> {
//                        try {
//                            var tr = createTr(line);
//                            consumer.accept(tr.orElse(null)); // Só envia para a lista final se não der erro
//                        } catch (Exception e) {
//                            System.err.println("Erro na linha [" + line + "]: " + e.getMessage());
//                        }
//                    })
                    .map(this::createTr)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .toList();
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    private Path readFile(String fileName, String path) {
        return Path.of(path.concat(fileName));
    }

    //Método usando File Inputa Instream
    public List<Transaction> readFI(String filename) throws IOException {
        List<Transaction>  lstTransaction = new ArrayList<>();

        try(FileInputStream fis = new FileInputStream(filename);){
            Scanner sc = new Scanner(fis);

            int lineCount = 0;

            while(sc.hasNextLine()){
                String line = sc.nextLine();
                lineCount++;

                if(lineCount == 1)continue;
                if(lineCount > 1001)break;

                var tr=  createTr(line);

                lstTransaction.add(tr.orElse(null));
            }

        } catch (Exception e) {
            throw new RuntimeException("erro ao ler o arquivo" + filename, e);
        }
        return lstTransaction;
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
