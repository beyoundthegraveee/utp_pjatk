/**
 *
 *  @author Kurzau Kiryl S24911
 *
 */

package zad2;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CustomersPurchaseSortFind {

    private List<Purchase> list = new ArrayList<>();
    public void readFile(String fname) {
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(Paths.get(fname).toFile()))) {
            String line = "";
            while ((line = bufferedReader.readLine())!=null){
                list.add(Purchase.of(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void showSortedBy(String filter) {

        if(filter == "Nazwiska") {
            System.out.println("Nazwiska");
             List<Purchase> result = list.stream()
                     .sorted((s1,s2)->{
                         return s1.surname.compareTo(s2.surname);
                     }).collect(Collectors.toList());

             for (Purchase str : result) {
                 System.out.println(str.toString());
             }
        }else {
            System.out.println("Koszty");
            List<Purchase> result = list.stream()
                    .sorted((s1,s2)-> {
                                double count = (s2.zakupiona_ilosc * s2.cena) - (s1.zakupiona_ilosc * s1.cena);

                                if (count == 0) {
                                    return s1.id.compareTo(s2.id);
                                }

                                return (int) count;
                            }

                    ).collect(Collectors.toList());
            for (Purchase str : result) {
                System.out.println(str.toString() + " (koszt: " + str.cena*str.zakupiona_ilosc + ")");
            }


        }


        
    }

    public void showPurchaseFor(String id) {
        System.out.println("Klient " + id);
        for (Purchase str : list){
            if (str.toString().startsWith(id)){
                System.out.println(str);
            }
        }
    }
}
