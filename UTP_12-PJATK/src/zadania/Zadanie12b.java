package zadania;

import java.sql.*;

public class Zadanie12b {


    PreparedStatement stmt;

    Connection connection;

    String url = "jdbc:derby:C:\\Users\\jacke\\Apache\\db-derby-10.17.1.0-bin\\bin\\ksidb";


    Zadanie12b(){
        try {
            connection = DriverManager.getConnection(url);
        } catch (Exception exc) {
            System.out.println(exc);
            System.exit(1);
        }

        String[] wyd =  { "PWN", "PWE", "Czytelnik", "Amber", "HELION", "MIKOM" };

        int beginKey = 15;

        int delCount = 0;

        try {
            stmt = connection.prepareStatement("DELETE FROM WYDAWCA WHERE WYDID = ? AND NAME = ? ");
            for (int i = 0; i < wyd.length; i++) {
                stmt.setInt(1,beginKey);
                stmt.setString(2,wyd[i]);
                stmt.executeUpdate();
                delCount++;
                beginKey++;
            }
            connection.close();
        } catch (SQLException e) {
            System.out.println(e);
        }


        System.out.println("Usunięto " + delCount + " rekordów do tabeli WYDAWCA.");


    }

    public static void main(String[] args) {
        Zadanie12b zadanie12b = new Zadanie12b();
    }
}