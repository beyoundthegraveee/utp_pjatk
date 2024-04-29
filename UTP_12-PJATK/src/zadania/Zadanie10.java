package zadania;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;
import java.lang.reflect.*;

public class Zadanie10 {

    Connection connection;

    DatabaseMetaData md;

    String driveName = "org.apache.derby.jdbc.EmbeddedDriver";

    String url = "jdbc:derby:C:\\Users\\jacke\\Apache\\db-derby-10.17.1.0-bin\\bin\\ksidb";



    public Zadanie10(){
        try {
            Class.forName(driveName);
            connection = DriverManager.getConnection(url);
            md = connection.getMetaData();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }



    void reportInfo() throws SQLException {

        info("getDatabaseProductName");
        info("getDatabaseProductVersion");
        info("getDriverName");
        info("getURL");
        info("getUserName");

        info("supportsAlterTableWithAddColumn");
        info("supportsAlterTableWithDropColumn");
        info("supportsANSI92FullSQL");
        info("supportsBatchUpdates");
        info("supportsMixedCaseIdentifiers");
        info("supportsMultipleTransactions");
        info("supportsPositionedDelete");
        info("supportsPositionedUpdate");
        info("supportsSchemasInDataManipulation");
        info("supportsTransactions");

        System.out.println("ResultSet  TYPE_SCROLL_INSENSITIVE :" +
                md.supportsResultSetType(ResultSet.TYPE_SCROLL_INSENSITIVE));
        System.out.println("ResultSet  TYPE_SCROLL_SENSITIVE :" +
                md.supportsResultSetType(ResultSet.TYPE_SCROLL_SENSITIVE));
        System.out.println("insertsAreDetected :" +
                md.insertsAreDetected(ResultSet.TYPE_SCROLL_INSENSITIVE));
        System.out.println("updatesAreDetected :" +
                md.updatesAreDetected(ResultSet.TYPE_SCROLL_INSENSITIVE));
    }

    void info(String metName) {
        Class mdc  = DatabaseMetaData.class;
        Class[] paramTypes =  { };
        Object[] params =  { };
        String infoTyp;
        if (metName.startsWith("get"))
            infoTyp = metName.substring(3,metName.length());
        else infoTyp = metName;
        try  {
            Method m = mdc.getDeclaredMethod(metName, paramTypes);
            System.out.println(infoTyp + ": " + m.invoke(md, params));  // dynamiczne wywołanie metody
        } catch(Exception exc)  {   // Możliwe powody wyjątków: nie ma takiej metody, niewłaściwe wywołanie
            System.out.println(exc);
        }
    }

    public static void main(String[] args) {
        Zadanie10 zadanie10 = new Zadanie10();
        try {
            zadanie10.reportInfo();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
