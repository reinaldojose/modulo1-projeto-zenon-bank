package br.com.zenon;

import java.math.BigDecimal;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    void main() {

//        Transaction transacao1 = new Transaction(1
//                , TransactionType.PAYMENT
//                , new BigDecimal("9839.64")
//                , new TransactionActivity("C1231006815", new BigDecimal("170136.0"), new BigDecimal("160296.36") )
//                , new TransactionActivity("M1979787155", new BigDecimal(0), new BigDecimal(0) )
//                ,false
//                ,false);
//
//        IO.println(transacao1);
//
//        var transacao2 = new Transaction(743
//                , TransactionType.CASH_OUT
//                , new BigDecimal("850002.52")
//                , new TransactionActivity("C1280323807", new BigDecimal("850002.52"), new BigDecimal(0) )
//                , new TransactionActivity("C873221189", new BigDecimal("6510099.11"), new BigDecimal("7360101.63") )
//                ,true
//                ,false);
//
//        IO.println(transacao2);

        var ti = new TransactionIngestor();
        var path = "E:\\DEV\\faculdade\\data\\";
//        var filename = "PS_20174392719_1491204439457_log.csv";
        var filename = "paysim_with_bad_data.csv";

        try {
//            Usando JAVA NIO
//            readNio(filename, path);

//            Usando File InputStream
//            var lst = readFI(path.concat(filename));
//            lst.stream().limit(10).forEach(IO::println);

//            Usando File InputStream
            var lst = ti.readNioMap(filename, path);
            lst.stream().limit(10).forEach(IO::println);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

