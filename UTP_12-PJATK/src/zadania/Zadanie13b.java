package zadania;

import java.sql.*;

public class Zadanie13b {


    Connection connection;

    String url = "jdbc:derby:C:\\Users\\jacke\\Apache\\db-derby-10.17.1.0-bin\\bin\\ksidb";

    Zadanie13b(){
        try {
            connection = DriverManager.getConnection(url);
        } catch (Exception exc) {
            System.out.println(exc);
            System.exit(1);
        }

        String sel = "SELECT * FROM AUTOR " +
                "ORDER BY AUTID DESC";
        try {
            Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                                                        ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stmt.executeQuery(sel);
            ResultSetMetaData rsmd = rs.getMetaData();
            int cc = rsmd.getColumnCount();
            for (int i = 1; i <= cc; i++)
                System.out.print(rsmd.getColumnLabel(i) + "     ");
            System.out.println("\n------------------------------ przewijanie do góry");
            while (rs.next()) {
                for (int i = 1; i <= cc; i++) {
                    System.out.print(rs.getString(i) + "     ");
                }
                System.out.println("");
            }
            System.out.println("\n----------------------------- pozycjonowanie abs.");
            int[] poz =  { 3, 7, 9  };
            for (int p = 0; p < poz.length; p++)  {
                System.out.print("[ " + poz[p] + " ] ");
                if(rs.absolute(poz[p])){
                    for (int i = 1; i <= cc; i++) System.out.print(rs.getString(i) + ", ");
                    System.out.println("");
                }
            }
            stmt.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public static void main(String[] args) {
        Zadanie13b zadanie13b = new Zadanie13b();
    }
}