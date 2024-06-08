/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

 /*
 *
 * @author JIGNESH
 */
package Farmer;

import config.Connect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Farmer {

    static Connection conn = Connect.getConnection();
    String username;
    String password;

    public Farmer() {
        Scanner sc = new Scanner(System.in);

        try {
            System.out.println("Welcome to farmer page");

            System.out.println("Enter the username : ");
            username = sc.next();

            System.out.println("Enter the password : ");
            password = sc.next();

            if (validateUser(username, password)) {
                System.out.println("Farmer login successful");
                int choice;
                do {
                    System.out.println("1. Add product");
                    System.out.println("2. Product requests");
                    System.out.println("3. Modify product");
                    System.out.println("4. Logout");
                    System.out.print("Enter the choice for farmer menu : ");
                    choice = sc.nextInt();

                    switch (choice) {
                        case 1:
                            addProduct();
                            break;
                        case 2:
                            productRequest();
                            break;
                        case 3:
                            modifyProduct();
                            break;
                        case 4:
                            System.out.println("farmer logout successfully");
                            break;
                        default:
                            System.out.println("Invalid choice");
                    }
                } while (choice != 4);
            } else {
                System.out.println("Invalid username or password,so try again");

            }
        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }

    private boolean validateUser(String username, String password) throws SQLException {
        String query = "SELECT * FROM farmer WHERE username = ? AND password = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    public void addProduct() throws SQLException {
        try {
            Statement stmt = conn.createStatement();

            Scanner sc = new Scanner(System.in);
            ResultSet rs = stmt.executeQuery("select farmerid from farmer where username = '" + username + "';");
            int fid = 0;
            while (rs.next()) {
                fid = rs.getInt(1);
            }
            System.out.println("Enter Product name : ");
            String name = sc.next();

            System.out.println("Enter Product Cost : ");
            Float price = sc.nextFloat();

            System.out.println("Enter Product Description : ");
            String desc = sc.next();

            System.out.println("Enter Product Quantity : ");
            int quantity = sc.nextInt();

            stmt.executeUpdate("insert into product(productname,description,price,quantity,farmerid,farmername) values('" + name + "','" + desc + "'," + price + "," + quantity + "," + fid + ",'" + username + "');");
            System.out.println("Product Added in System Successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void productRequest() throws SQLException {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from `order` where farmername = '" + username + "';");

            System.out.println("All Order Response Details..\n");
            System.out.println("+--------+----------+----------------+----------------------+----------------------+----------------------+--------------+");
            System.out.println("| OrderID| ProductID| FarmerName     | ProductName          | UserName             | Date&Time            | OrderStatus  |");
            System.out.println("+--------+----------+----------------+----------------------+----------------------+----------------------+--------------+");
            while (rs.next()) {
                System.out.printf("| %-7d| %-9d| %-15s| %-20s| %-20s| %-20s| %-12s|\n",
                        rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7));
            }
            System.out.println("+--------+----------+----------------+----------------------+----------------------+----------------------+--------------+");

            int choice;
            Scanner sc = new Scanner(System.in);
            do {
                System.out.println("Are You want to Update OrderStatus of any Product ?");
                System.out.println("1. Yes");
                System.out.println("2. No");
                System.out.print("Enter your choice : ");

                // Check if the next token is an integer
                if (sc.hasNextInt()) {
                    choice = sc.nextInt();

                    if (choice == 1) {
                        System.out.println("Enter Order ID : ");
                        int oid = sc.nextInt();

                        stmt.executeUpdate("update `order` set orderstatus='accepted' where orderid=" + oid);
                        System.out.println("Accepted order successfully");
                    } else if (choice == 2) {
                        System.out.println("you not update orderstatus");
                    } else {
                        System.out.println("Invalid Choice Plaease Enter valid choice!");
                    }
                } else {
                    // Clear the invalid input from the scanner
                    sc.next();
                    System.out.println("Invalid input. Please enter an integer value.");
                    choice = -1; // Set choice to an invalid value to continue the loop
                }
            } while (choice != 2);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void modifyProduct() throws SQLException {
        try {
            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery("select productid,productname,description,price,quantity from product;");

            System.out.println("All Product Details..\n");
            System.out.println("+------------+------------------+---------------------+-----------+----------------+");
            System.out.println("| Product ID | Productname      | Description         | Price     | ProductQuantity|");
            System.out.println("+------------+------------------+---------------------+-----------+----------------+");
            while (rs.next()) {
                System.out.printf("| %-10d | %-16s | %-20s | %-9.2f | %-14d |\n",
                        rs.getInt(1), rs.getString(2), rs.getString(3), rs.getFloat(4), rs.getInt(5));
            }
            System.out.println("+------------+------------------+---------------------+-----------+----------------+");

            int choice;
            Scanner sc = new Scanner(System.in);

            do {
                System.out.println("Are You want to update or Delete a Product ?");
                System.out.println("1. Update");
                System.out.println("2. Delete");
                System.out.println("0. Back Menu");
                System.out.print("Enter Your Choice : ");
                if (sc.hasNextInt()) {
                choice = sc.nextInt();

                if (choice == 1) {
                    try {
                        System.out.println("Enter Product ID/No which you want to update : ");
                        int pid = sc.nextInt();

                        System.out.println("Enter Product name : ");
                        String pname = sc.next();

                        System.out.println("Enter Product Cost : ");
                        Float pcost = sc.nextFloat();

                        System.out.println("Enter Product Description : ");
                        String pdesc = sc.next();

                        System.out.println("Enter Product Quantity : ");
                        int pquantity = sc.nextInt();

                        stmt.executeUpdate("update product set productname = '" + pname + "',price = " + pcost + " ,description = '" + pdesc + "',quantity = " + pquantity + " where productid=" + pid);
                        System.out.println("Product Details Updated Successfully");
                    } catch (Exception e) {
                        System.out.println("Exception : " + e);
                    }
                } else if (choice == 2) {
                    try {
                        System.out.println("Enter Product ID/No : ");
                        int cpid = sc.nextInt();
                        stmt.executeUpdate("delete from product where productid=" + cpid);
                        System.out.println("Product Deleted Successfully Successfully");
                    } catch (Exception e) {
                        System.out.println("Exception : " + e);
                    }
                } else if (choice == 0) {

                    System.out.println("Not changes in product");
                } else {
                    System.out.println("Invalid Choice Plaease Enter valid choice!");
                }} else {
                sc.next();
                System.out.println("Invalid input. Please enter an integer value.");
                choice = -1;
            }
            } while (choice != 0);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Farmer();
    }

}
