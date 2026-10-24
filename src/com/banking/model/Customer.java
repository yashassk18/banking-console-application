
package com.banking.model;

public class Customer {
    private final BankAccount bankAccount;//customer object hold a reference to their account.
    private final String customerId;
    private  String name;
    private  String phoneNumber;
    private  String email;

    public Customer(
            String customerId,
            String name,
            String phoneNumber,
            String email,
            BankAccount bankAccount) {
        if (bankAccount == null) {
            throw new IllegalArgumentException(
                    "Bank account cannot be null");
        }

        if (customerId == null || customerId.isBlank()) {
            throw new IllegalArgumentException(
                    "Customer ID cannot be empty");
        }

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(
                    "Customer name cannot be empty");
        }

        if (phoneNumber == null || !phoneNumber.matches("\\d{10}")) {//\\d{10} checks that the phone number contains exactly 10 digits.
            throw new IllegalArgumentException(
                    "Phone number must contain exactly 10 digits");
        }

        if (email == null ||
                !email.matches("^[\\w.-]+@[\\w.-]+\\.\\w+$")) {
            //@ requires an at-sign in the email.
            //\\. matches a literal dot, such as the one in .com.
            throw new IllegalArgumentException(
                    "Invalid email address");
        }

        this.customerId = customerId;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.bankAccount=bankAccount;
    }
    public BankAccount getBankAccount() {
        return bankAccount;// Returning a Bank Account Class
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void displayCustomerDetails() {
        System.out.println("Customer ID: " + customerId);
        System.out.println("Name: " + name);
        System.out.println("Phone: " + phoneNumber);
        System.out.println("Email: " + email);
        System.out.println("Account Number: " +
                bankAccount.getAccountNumber());
        System.out.println("Balance: " +
                bankAccount.getBalance());
    }
    public void updateDetails(
            String name,
            String phoneNumber,
            String email) {

        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }

        if (phoneNumber == null || !phoneNumber.matches("\\d{10}")) {
            throw new IllegalArgumentException(
                    "Phone number must contain exactly 10 digits");
        }

        if (email == null || !email.matches("^[\\w.-]+@[\\w.-]+\\.\\w+$")) {
            throw new IllegalArgumentException("Invalid email address");
        }

        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.bankAccount.updateAccountHolderName(name);//to update account holder name automatically
    }
}
