package zadania;

import java.sql.*;

public class Zadanie11b {

    Statement stmt;

    Connection connection;



    String url = "jdbc:derby:C:\\Users\\jacke\\Apache\\db-derby-10.17.1.0-bin\\bin\\ksidb";

    public Zadanie11b(){
        try {
            connection = DriverManager.getConnection(url);
            stmt = connection.createStatement();
        } catch (Exception exc) {
            System.out.println(exc);
            System.exit(1);
        }
        dropTable("AUTOR");

        String crestmt = "CREATE TABLE AUTOR ("
                + "AUTID INT PRIMARY KEY,"
                + "NAME VARCHAR(255))";
        try {
            stmt.executeUpdate(crestmt);
        }catch (SQLException exc)  {                      // przechwycenie wyjątku:
            System.out.println("SQL except.: " + exc.getMessage());
            System.out.println("SQL state  : " + exc.getSQLState());
            System.out.println("Vendor errc: " + exc.getErrorCode());
            System.exit(1);
        } finally {
            try {
                stmt.close();
                connection.close();
            } catch(SQLException exc) {
                System.out.println(exc);
                System.exit(1);
            }
        }

    }



    private void dropTable(String tname){
        String delstmt = "DROP TABLE " + tname;
        try {
            stmt.executeUpdate(delstmt);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    public static void main(String[] args) {
        Zadanie11b zadanie11b = new Zadanie11b();
    }
}