package zadania;

import java.sql.*;

public class Zadanie13 {


    Connection connection;

    String url = "jdbc:derby:C:\\Users\\jacke\\Apache\\db-derby-10.17.1.0-bin\\bin\\ksidb";

    Zadanie13(){
        try {
            connection = DriverManager.getConnection(url);
        } catch (Exception exc) {
            System.out.println(exc);
            System.exit(1);
        }

        String sel = "SELECT NAME, TYTUL, ROK, CENA FROM POZYCJE P " +
                "JOIN AUTOR A ON A.AUTID = P.AUTID "+
                "WHERE ROK > 2000 AND CENA > 30";
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sel);
            while (rs.next()){
                String nazwisko = rs.getString(1);
                String tytul = rs.getString(2);
                String rok = rs.getString(3);
                String cena = rs.getString(4);
                System.out.println("Autor: " + nazwisko);
                System.out.println("Tytul: " + tytul);
                System.out.println("Rok: " + rok);
                System.out.println("Cena: " + cena);
                System.out.println();
            }

            stmt.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public static void main(String[] args) {
        Zadanie13 zadanie13 = new Zadanie13();
    }
}
