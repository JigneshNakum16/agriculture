/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author JIGNESH
 */
package Registration;

import config.Connect;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Connection;

public class Registration {

    static Connection conn = Connect.getConnection();
    String name;
    String email;
    long mobile;
    String gender;
    String address;
    String username;
    String password;
    String userType;

    public Registration() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Welcome to Registration ");

        try {
            System.out.println("Select the user type : ");
            System.out.println("1. farmer ");
            System.out.println("2. user ");
            System.out.print("Enter your choice for user type : ");
            int choiceRole;

            // Input validation for user type
            while (true) {
                if (sc.hasNextInt()) {
                    choiceRole = sc.nextInt();
                    if (choiceRole == 1 || choiceRole == 2) {
                        break;
                    } else {
                        System.out.println("Invalid input. Please enter 1 for farmer or 2 for user.");
                    }
                } else {
                    System.out.println("Invalid input. Please enter an integer value.");
                    sc.next(); // Clear the invalid input
                }
            }

            userType = (choiceRole == 1) ? "farmer" : "user";

            System.out.print("Enter the name : ");
            name = sc.next();

            // Input validation for email
            while (true) {
                System.out.print("Enter the email : ");
                email = sc.next();
                if (isValidEmail(email)) {
                    break;
                } else {
                    System.out.println("Invalid email format. Please enter a valid email address.");
                }
            }

            System.out.print("Enter the mobile : ");

            // Input validation for mobile number
            while (true) {
                if (sc.hasNextLong()) {
                    mobile = sc.nextLong();
                    break;
                } else {
                    System.out.println("Invalid input. Please enter a valid mobile number.");
                    sc.next(); // Clear the invalid input
                }
            }

            System.out.println("select the gender : ");
            System.out.println("1. male ");
            System.out.println("2. female ");
            System.out.print("Enter your choice for gender : ");
            int choiceGender;

            // Input validation for gender
            while (true) {
                if (sc.hasNextInt()) {
                    choiceGender = sc.nextInt();
                    if (choiceGender == 1 || choiceGender == 2) {
                        break;
                    } else {
                        System.out.println("Invalid input. Please enter 1 for male or 2 for female.");
                    }
                } else {
                    System.out.println("Invalid input. Please enter an integer value.");
                    sc.next(); // Clear the invalid input
                }
            }

            gender = (choiceGender == 1) ? "male" : "female";

            System.out.print("Enter the address : ");
            address = sc.next();

            System.out.print("Enter the user name : ");
            username = sc.next();

            System.out.print("Enter the password : ");
            password = sc.next();

            String insertQuery = "INSERT INTO " + userType + " (name, email, mobile, gender, address, username, password) VALUES (?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = conn.prepareStatement(insertQuery);
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setLong(3, mobile);
            ps.setString(4, gender);
            ps.setString(5, address);
            ps.setString(6, username);
            ps.setString(7, password);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Registration successful!");
            } else {
                System.out.println("Registration failed.");
            }

        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        } 
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static void main(String[] args) {
        new Registration();
    }
}
