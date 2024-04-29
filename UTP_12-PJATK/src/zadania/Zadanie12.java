package zadania;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Zadanie12 {


    Statement stmt;

    Connection connection;

    String url = "jdbc:derby:C:\\Users\\jacke\\Apache\\db-derby-10.17.1.0-bin\\bin\\ksidb";


    Zadanie12(){
        try {
            connection = DriverManager.getConnection(url);
            stmt = connection.createStatement();
        } catch (Exception exc) {
            System.out.println(exc);
            System.exit(1);
        }

        String[] wyd =  { "PWN", "PWE", "Czytelnik", "Amber", "HELION", "MIKOM" };

        int beginKey = 15;
        

        String [] ins = new String[wyd.length];
        for (int i = 0; i < wyd.length; i++) {
            ins[i] = "INSERT INTO WYDAWCA(WYDID, NAME) VALUES (" + beginKey+", '" + wyd[i]+"')";
            beginKey++;
        }

        int insCount = 0;
        try{
            for (int i = 0; i < ins.length; i++) {
                stmt.executeUpdate(ins[i]);
                insCount++;

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Wprowadzono " + insCount + " rekordów do tabeli WYDAWCA.");


    }

    public static void main(String[] args) {
        Zadanie12 zadanie12 = new Zadanie12();
    }
}
