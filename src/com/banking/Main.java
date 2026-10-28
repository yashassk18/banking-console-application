

package com.banking;
import com.banking.service.BankService;
import com.banking.model.Customer;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        BankService bankService = new BankService();

        boolean running = true;

        while (running) {
            System.out.println("\n===== BANKING APPLICATION =====");
            System.out.println("1. Create Customer");
            System.out.println("2. Deposit Money");
            System.out.println("3. Withdraw Money");
            System.out.println("4. Check Balance");
            System.out.println("5. View Customer Details");
            System.out.println("6. Exit");

            System.out.print("Enter your choice: ");

            if (!scanner.hasNextInt()) {//hasNextInt() checks whether the next input is an integer.
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // Consume the invalid input
                continue;       // Return to the menu
            }

            int choice = scanner.nextInt();
            switch (choice) {

                case 1:
                    System.out.print("Enter Customer ID (e.g., CUST1001): ");
                    String customerId = scanner.next();

                    System.out.print("Enter customer name: ");
                    scanner.nextLine(); // Consume the leftover newline
                    String name = scanner.nextLine();

                    System.out.print("Enter phone number: ");
                    String phoneNumber = scanner.next();

                    System.out.print("Enter email: ");
                    String email = scanner.next();

                    System.out.print("Enter Account Number (e.g., ACC1001): ");
                    String accountNumber = scanner.next();

                    System.out.print("Enter initial balance: ");
                    java.math.BigDecimal initialBalance =
                            scanner.nextBigDecimal();

                    try {
                        bankService.createCustomer(
                                customerId,
                                name,
                                phoneNumber,
                                email,
                                accountNumber,
                                initialBalance
                        );

                        System.out.println("Customer created successfully!");

                    } catch (IllegalArgumentException e) {
                        System.out.println("Error: " + e.getMessage());
                    }

                    break;


                case 2:
                    System.out.print("Enter account number: ");
                    String depositAccountNumber = scanner.next();

                    System.out.print("Enter deposit amount: ");
                    java.math.BigDecimal depositAmount =
                            scanner.nextBigDecimal();

                    try {
                        bankService.deposit(
                                depositAccountNumber,
                                depositAmount
                        );

                        System.out.println("Deposit successful!");
                        System.out.println(
                                "Updated balance: ₹" +
                                        bankService.checkBalance(depositAccountNumber)
                        );

                    } catch (IllegalArgumentException |
                             IllegalStateException e) {
                        System.out.println("Error: " + e.getMessage());
                    }

                    break;



                case 3:
                    System.out.print("Enter account number: ");
                    String withdrawAccountNumber = scanner.next();

                    System.out.print("Enter withdrawal amount: ");
                    java.math.BigDecimal withdrawAmount =
                            scanner.nextBigDecimal();

                    try {
                        bankService.withdraw(
                                withdrawAccountNumber,
                                withdrawAmount
                        );

                        System.out.println("Withdrawal successful!");
                        System.out.println(
                                "Updated balance: ₹" +
                                        bankService.checkBalance(withdrawAccountNumber)
                        );

                    } catch (IllegalArgumentException |
                             IllegalStateException e) {
                        System.out.println("Error: " + e.getMessage());
                    }

                    break;


                case 4:
                    System.out.print("Enter account number: ");
                    String balanceAccountNumber = scanner.next();

                    try {
                        java.math.BigDecimal balance =
                                bankService.checkBalance(balanceAccountNumber);

                        System.out.println("Current balance: ₹" + balance);

                    } catch (IllegalArgumentException e) {
                        System.out.println("Error: " + e.getMessage());
                    }

                    break;



                case 5:
                    System.out.print("Enter Customer ID: ");
                    String searchCustomerId = scanner.next();

                    try {
                        Customer customer =
                                bankService.findCustomerById(searchCustomerId);

                        customer.displayCustomerDetails();

                    } catch (IllegalArgumentException e) {
                        System.out.println("Error: " + e.getMessage());
                    }

                    break;

                case 6:
                    running = false;
                    System.out.println("Thank you for using our bank!");
                    break;

                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }

        scanner.close();
    }
}
