package br.com.zenon;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
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
                lstTransaction.add(tr);
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
    public List<Transaction> readNioMap(String filename, String path) throws IOException{

        var pathFilename = readFile(filename, path);

        try{
            List<String> lines = Files.readAllLines(pathFilename);
            return lines.stream()
                    .skip(1)
                    .limit(1000)
                    .map(this::createTr)
                    .toList();
        } catch(Exception e){
            throw new RuntimeException(e);
        }

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

                var tr = createTr(line);

                lstTransaction.add(tr);
            }

        } catch (Exception e) {
            throw new RuntimeException("erro ao ler o arquivo" + filename, e);
        }
        return lstTransaction;
    }

    public Transaction createTr(String line) {
        var col = line.split(",");
        return new Transaction(Integer.parseInt(col[0])
                , TransactionType.valueOf(col[1])
                , new BigDecimal(col[2])
                , new TransactionActivity(col[3], new BigDecimal(col[4]), new BigDecimal(col[5]) )
                , new TransactionActivity(col[6], new BigDecimal(col[7]), new BigDecimal(col[8]) )
                , "1".equals(col[9])
                , "1".equals(col[10]));
    }
}
