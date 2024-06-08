/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package config;

/**
 *
 * @author JIGNESH
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {

  private static final String URL =
    "jdbc:mysql://localhost:3306/agriculture_management_system";
  private static final String USERNAME = "root";
  private static final String PASSWORD = "JjigneshH@#40";

  public static Connection getConnection() {
    Connection connection = null;
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
    } catch (ClassNotFoundException e) {
      System.err.println(
        "MySQL JDBC Driver not found. Include it in your library path."
      );
      e.printStackTrace();
    } catch (SQLException e) {
      System.err.println("Connection failed! Check output console.");
      e.printStackTrace();
    }
    return connection;
  }

  public static void closeConnection(Connection connection) {
    if (connection != null) {
      try {
        connection.close();
      } catch (SQLException e) {
        System.err.println("Error while closing connection.");
        e.printStackTrace();
      }
    }
  }
}


