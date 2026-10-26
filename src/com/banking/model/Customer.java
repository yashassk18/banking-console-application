
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

        if (customerId == null || !customerId.matches("CUST\\d{4}")) {//CUST — the ID must start with these letters
            throw new IllegalArgumentException(                           //\\d — in Java, this represents a digit in the regular expression
                    "Customer ID must follow the format CUST1001");  //- 4} — exactly four digits must follow.
        }

        validateName(name);

        validatePhoneNumber(phoneNumber);

        validateEmail(email);

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
    private void validatePhoneNumber(String phoneNumber) {
        if (phoneNumber == null || !phoneNumber.matches("\\d{10}")) {
            throw new IllegalArgumentException(
                    "Phone number must contain exactly 10 digits");
        }
    }
    private void validateEmail(String email) {
        if (email == null ||
                !email.matches("^[\\w.-]+@[\\w.-]+\\.\\w+$")) {
            throw new IllegalArgumentException("Invalid email address");
        }
    }
    public void updateDetails(
            String name,
            String phoneNumber,
            String email) {

        validateName(name);

        validatePhoneNumber(phoneNumber);

        validateEmail(email);
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.bankAccount.updateAccountHolderName(name);//to update account holder name automatically

    }
    private void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(
                    "Customer name cannot be empty");
        }
    }
}
