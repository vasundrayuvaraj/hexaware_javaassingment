// DBConnUtil.java
package com.hexaware.studentinformationsystem.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnUtil {
    public static Connection getConnection() {
        try {
           Connection connection = null ;
           connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sisdb","Admin","Admin@123") ;
         if (connection != null) {
                return connection ;
            } else {
                System.out.println("Failed to establish a database connection.");
                return null;
            }
        } catch (SQLException ex) {
                     System.out.println("ex");

            return null;
        }
    }

    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException ex) {
                           System.out.println("ex");

            }
        }
    }
}
