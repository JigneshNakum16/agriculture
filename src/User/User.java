/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package User;

/**
 *
 * @author JIGNESH
 */
import config.Connect;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.Scanner;
import java.util.Date;
import java.text.SimpleDateFormat;

public class User {

    static Connection conn = Connect.getConnection();
    String username;
    String password;

    public User() {
        Scanner sc = new Scanner(System.in);

        try {
            System.out.println("Welcome to user page");

            System.out.println("Enter the username :");
            username = sc.next();

            System.out.println("Enter the password : ");
            password = sc.next();

            String query = "SELECT * FROM user WHERE username = ? AND password = ?";

            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();

            if (validateUser(username, password)) {
                System.out.println("User login successful");
                int choice;
                do {
                    System.out.println("1. View orders");
                    System.out.println("2. Product order");
                    System.out.println("3. Send feedback");
                    System.out.println("4. Logout");
                    System.out.print("Enter the choice for user menu : ");
                    choice = sc.nextInt();

                    switch (choice) {
                        case 1:
                            viewOrder();
                            break;
                        case 2:
                            productOrder();
                            break;
                        case 3:
                            postFeedback();
                            break;
                        case 4:
                            System.out.println("user logout successfully");
                            break;
                        default:
                            System.out.println("Invalid choice");
                    }
                } while (choice != 4);
            } else {
                System.out.println("Invalid username or password, so try again");

            }

        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
        }

    }

    private boolean validateUser(String username, String password) throws SQLException {
        String query = "SELECT * FROM user WHERE username = ? AND password = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    public void viewOrder() throws SQLException {
        try {
            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery("select * from `order`;");

            System.out.println("All Order Response Details..\n");
            System.out.println("+--------+------------+----------------------+----------------------+---------------+----------------------+---------------+");
            System.out.println("| OrderID| ProductID  | FarmerName           | ProductName          | UserName      | Date&Time            | OrderStatus   |");
            System.out.println("+--------+------------+----------------------+----------------------+---------------+----------------------+---------------+");

            while (rs.next()) {
                System.out.printf("| %-6d | %-10d | %-20s | %-20s | %-13s | %-20s | %-13s |%n",
                        rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4),
                        rs.getString(5), rs.getString(6), rs.getString(7));
            }
            System.out.println("+--------+------------+----------------------+----------------------+---------------+----------------------+---------------+");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void postFeedback() throws SQLException {
        try {
            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery("select farmerid,username from farmer;");

            System.out.println("Enter Feedback Details...\n");
            System.out.println("Select Farmer Username : ");
            System.out.println("+------------------+------------------------+");
            System.out.println("| Farmer ID        | Farmer Username        |");
            System.out.println("+------------------+------------------------+");

            int noOfFarmer = 1;
            while (rs.next()) {
                System.out.printf("| %-16d | %-22s |\n", rs.getInt(1), rs.getString(2));
                noOfFarmer++;
            }
            System.out.println("+------------------+------------------------+");

            Scanner sc = new Scanner(System.in);
            System.out.print("Enter the farmerid : ");
            int fid = sc.nextInt();

            System.out.print("Enter Feedback message : ");
            String description = sc.next();

            ResultSet fn = stmt.executeQuery("select username from farmer where farmerid = " + fid + ";");
            String funame = null;
            while (fn.next()) {
                funame = fn.getString(1);
            }

            stmt.executeUpdate("insert into feedback(username,farmername,description) values('" + funame + "','" + username + "','" + description + "');");
            System.out.println("Feedback Submit Successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void productOrder() throws SQLException {
        try {

            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery("select productid,productname,description,price,quantity from product;");

            System.out.println("All Product Details..\n");
            System.out.println("+------------+------------------+---------------------+-----------+----------------+");
            System.out.println("| Product ID | Productname      | ProductDesc         | ProductCost| ProductQuantity|");
            System.out.println("+------------+------------------+---------------------+-----------+----------------+");

            while (rs.next()) {
                System.out.printf("| %-10d | %-16s | %-20s | %-9.2f | %-14d |\n",
                        rs.getInt(1), rs.getString(2), rs.getString(3), rs.getFloat(4), rs.getInt(5));
            }
            System.out.println("+------------+------------------+---------------------+-----------+----------------+");

            int choice;

            do {
                System.out.println("Are You want to Order any Product ?");
                System.out.println("1. Yes");
                System.out.println("2. No");
                System.out.print("Enter Your Choice : ");
                Scanner sc = new Scanner(System.in);
                choice = sc.nextInt();

                if (choice == 1) {
                    try {
                        Date d = new Date();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String formattedDateTime = sdf.format(d);

                        System.out.println("Enter Product ID : ");
                        int pid = sc.nextInt();

                        String fusername = "";
                        String pname = "";

                        ResultSet rs2 = stmt.executeQuery("select farmername,productname from product where productid = " + pid + ";");

                        while (rs2.next()) {
                            fusername = rs2.getString(1);
                            pname = rs2.getString(2);
                        }

                        stmt.executeUpdate("INSERT INTO `order` (productid, farmername, productname, username, datetime, orderstatus) "
                                + "VALUES (" + pid + ", '" + fusername + "', '" + pname + "', '" + username + "', '" + formattedDateTime + "', 'waiting')");

                        System.out.println("Product Ordered Successfully");
                    } catch (SQLException e) {
                        System.out.println("SQLException : " + e);
                    } catch (Exception e) {
                        System.out.println("Exception : " + e);
                    }
                } else if (choice == 2) {
                    //Back menu 
                    System.out.println("You have not placed an order");
                } else {
                    System.out.println("Invalid Choice Plaease Enter valid choice!");
                }
            } while (choice != 2);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new User();
    }

}
