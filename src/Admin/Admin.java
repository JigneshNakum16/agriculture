/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author JIGNESH
 * // */
package Admin;

import config.Connect;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Admin {

    static Connection conn = Connect.getConnection();

    public Admin() {
        Scanner sc = new Scanner(System.in);

        try {
            System.out.println("Welcome to admin page ");

            System.out.println("Enter the username :");
            String username = sc.next();

            System.out.println("Enter the password : ");
            String password = sc.next();

            if (username.equals("admin") && password.equals("admin@123")) {
                System.out.println("Admin login successful");
                int choice;
                do {
                    System.out.println("1. View farmers");
                    System.out.println("2. View users");
                    System.out.println("3. View products");
                    System.out.println("4. View user feedback");
                    System.out.println("5. Logout");
                    System.out.print("Enter the choice for admin menu : ");
                    choice = sc.nextInt();

                    switch (choice) {
                        case 1:
                            viewFarmers();
                            break;
                        case 2:
                            viewUsers();
                            break;
                        case 3:
                            viewProducts();
                            break;
                        case 4:
                            viewFeedback();
                            break;
                        case 5:
                            System.out.println("Admin logout successfully");
                            break;
                        default:
                            System.out.println("Invalid choice");
                    }
                } while (choice != 5);
            } else {
                System.out.println("Invalid credentials");
            }

        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.out.println("Error closing connection: " + e.getMessage());
            }
        }
    }

    void viewFarmers() throws SQLException {
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery("SELECT farmerid, name, email, mobile, gender, address, username FROM farmer")) {

            System.out.println("All Farmer Details..\n");
            System.out.println("+------------+----------------------+--------------------------------+---------------+----------+------------------------------------------+---------------+");
            System.out.println("| FarmerID   | Name                 | Email                          | Mobile        | Gender   | Address                                  | Username      |");
            System.out.println("+------------+----------------------+--------------------------------+---------------+----------+------------------------------------------+---------------+");
            while (rs.next()) {
                System.out.printf("| %-10d | %-20s | %-30s | %-13d | %-8s | %-40s | %-13s |\n", rs.getInt(1), rs.getString(2), rs.getString(3), rs.getLong(4), rs.getString(5), rs.getString(6), rs.getString(7));
            }
            System.out.println("+------------+----------------------+--------------------------------+---------------+----------+------------------------------------------+---------------+");
        }
    }

    void viewUsers() throws SQLException {
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery("SELECT userid, name, email, mobile, gender, address, username FROM user")) {

            System.out.println("All User Details..\n");
            System.out.println("+--------+----------------------+--------------------------------+---------------+----------+------------------------------------------+---------------+");
            System.out.println("| UserID | Name                 | Email                          | Mobile        | Gender   | Address                                  | Username      |");
            System.out.println("+--------+----------------------+--------------------------------+---------------+----------+------------------------------------------+---------------+");
            while (rs.next()) {
                System.out.printf("| %-6d | %-20s | %-30s | %-13d | %-8s | %-40s | %-13s |\n", rs.getInt(1), rs.getString(2), rs.getString(3), rs.getLong(4), rs.getString(5), rs.getString(6), rs.getString(7));
            }
            System.out.println("+--------+----------------------+--------------------------------+---------------+----------+------------------------------------------+---------------+");
        }
    }

    void viewProducts() throws SQLException {
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery("SELECT productid, productname, description, price, quantity, farmerid, farmername FROM Product")) {

            System.out.println("All Product Details..\n");
            System.out.println("+------------+----------------------+--------------------------------+---------------+----------+---------------+----------------------+");
            System.out.println("| ProductID  | ProductName          | ProductDesc                    | ProductCost   | Quantity | FarmerID      | FarmerUname          |");
            System.out.println("+------------+----------------------+--------------------------------+---------------+----------+---------------+----------------------+");
            while (rs.next()) {
                System.out.printf("| %-10d | %-20s | %-30s | %-13.2f | %-8d | %-13d | %-20s |\n", rs.getInt(1), rs.getString(2), rs.getString(3), rs.getFloat(4), rs.getInt(5), rs.getInt(6), rs.getString(7));
            }
            System.out.println("+------------+----------------------+--------------------------------+---------------+----------+---------------+----------------------+");
        }
    }

    public void viewFeedback() throws SQLException {
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery("SELECT username, farmername, description FROM feedback")) {

            System.out.println("User Feedbacks..\n");
            System.out.println("+-----------------+-----------------+-----------------------------------------------+");
            System.out.println("| Farmername      | UserName        | Feedback Description                         |");
            System.out.println("+-----------------+-----------------+-----------------------------------------------+");
            while (rs.next()) {
                System.out.printf("| %-15s | %-15s | %-45s |\n", rs.getString(1), rs.getString(2), rs.getString(3));
            }
            System.out.println("+-----------------+-----------------+-----------------------------------------------+");
        }
    }

    public static void main(String[] args) {
        new Admin();
    }
}
