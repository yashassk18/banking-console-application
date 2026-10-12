
package com.banking.model;

public class Customer {
    private final BankAccount bankAccount;
    private final String customerId;
    private final String name;
    private final String phoneNumber;
    private final String email;

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

        if (phoneNumber == null || phoneNumber.isBlank()) {
            throw new IllegalArgumentException(
                    "Phone number cannot be empty");
        }

        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException(
                    "Email cannot be empty");
        }

        this.customerId = customerId;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.bankAccount=bankAccount;
    }
    public BankAccount getBankAccount() {
        return bankAccount;
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
}
