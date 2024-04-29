/**
 *
 *  @author Kurzau Kiryl S24911
 *
 */

package zad2;


public class Purchase implements Comparable<Purchase>{

    String id;
    String surname;
    String name;
    String nazwa_towaru;
    double cena;
    double zakupiona_ilosc;

    public Purchase(String id, String surname, String name, String nazwa_towaru, double cena, double zakupiona_ilość) {
        this.id = id;
        this.surname = surname;
        this.name = name;
        this.nazwa_towaru = nazwa_towaru;
        this.cena = cena;
        this.zakupiona_ilosc = zakupiona_ilość;
    }

    public static Purchase of(String str){
        String[] result = str.split(";");
        String[] name = result[1].split(" ");

        return new Purchase(result[0], name[0], name[1],
                result[2], Double.parseDouble(result[3]), Double.parseDouble(result[4]));


    }

    @Override
    public String toString() {
        return  id+";"+surname+";"+name+";"+nazwa_towaru+";"+cena+";"+zakupiona_ilosc;
    }



    @Override
    public int compareTo(Purchase o) {
        return 0;
    }
}
