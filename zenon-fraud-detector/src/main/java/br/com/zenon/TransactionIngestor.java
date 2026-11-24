package br.com.zenon;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Stream;

public class TransactionIngestor extends TransacationUtils {

    int limitSize = 100000;

    final String path = "E:\\DEV\\faculdade\\data\\";
    final String filename = "PS_20174392719_1491204439457_log.csv";

    public TransactionIngestor(){
        this.limitSize = this.limitSize + 1;
    }

    public List<Transaction> readNioMap() {
        return readNioMap(filename, path);
    }

    //Método que eu criei
    private void readNio(String filename, String path) throws IOException{
        try(Stream<String> lines = Files.lines(readFile(filename, path))){
            List<String> linhas = lines
                    .limit(limitSize)
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
        try{
            List<String> lines = Files.readAllLines(readFile(filename, path));
            return lines.stream()
                    .skip(1)
                    .limit(limitSize)
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
}
