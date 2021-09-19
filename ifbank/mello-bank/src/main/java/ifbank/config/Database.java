package ifbank.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    
    private static Connection connection = null;

    public static Connection getConnection(){

        if (connection == null) {

            if (connection == null) {

                String dbUrl = "jdbc:postgresql://" + 
                "ec2-44-198-151-32.compute-1.amazonaws.com" + ":" // HOST
                        + "5432" // PORTA
                        + "/d9tbth1dj3edr2"; // BANCO
    
                String username = "csfrccugjrkhub";
                String password = "53fcf48f1db55416d9c2a6f3edfdb4a8c4310255991002b2849373cbe936fb36";
    
                try {
                    connection = DriverManager.getConnection(dbUrl, username, password);
                    System.out.println("Conectou...");
                } catch (SQLException error) {
                    System.out.println("Não foi possivel conectar ao banco");
                    error.printStackTrace();
                }
            }
    
            return connection;
        }

        return connection;
    }

}
