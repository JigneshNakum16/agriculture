/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.util.*;
import Admin.Admin;
import Farmer.Farmer;
import User.User;
import Registration.Registration;

/**
 *
 * @author JIGNESH
 */
import java.util.Scanner;

public class index {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("1. Admin Login");
            System.out.println("2. Farmer Login");
            System.out.println("3. User Login");
            System.out.println("4. Registration");
            System.out.println("5. Exit System");
            System.out.print("Enter your choice : ");

            // Check if the next token is an integer
            if (sc.hasNextInt()) {
                int choice = sc.nextInt();

                switch (choice) {
                    case 1:
                        new Admin();
                        break;
                    case 2:
                        new Farmer();
                        break;
                    case 3:
                        new User();
                        break;
                    case 4:
                        new Registration();
                        break;
                    case 5:
                        System.out.println("Exit Program");
                        System.exit(0); // Exit the program
                    default:
                        System.out.println("Invalid choice, please try again");
                }
            } else {
                sc.next();
                System.out.println("Invalid input. Please enter an integer value.");
            }
        }
    }
}
