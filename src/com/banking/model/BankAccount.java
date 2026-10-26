package com.banking.model;

import java.math.BigDecimal;

public class BankAccount {

    private final String accountNumber;
    private  String accountHolderName;
    private BigDecimal balance;//BigDecimal to represent and calculate decimal numbers with high precision,
    // especially when accuracy is important,

    public BankAccount(
            String accountNumber,
            String accountHolderName,
            BigDecimal initialBalance) {

        validateAccountNumber(accountNumber);

        if (accountHolderName == null
                || accountHolderName.isBlank()) {
            throw new IllegalArgumentException(
                    "Account holder name cannot be empty");
        }

        validateAmount(initialBalance, true);//to validate that input is correct or not

        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.balance = initialBalance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void deposit(BigDecimal amount) {
        validateAmount(amount, false);
        balance = balance.add(amount);
    }

    public void withdraw(BigDecimal amount) {
        validateAmount(amount, false);

        if (balance.compareTo(amount) < 0) {
            throw new IllegalStateException(
                    "Insufficient balance");
        }

        balance = balance.subtract(amount);//.subtract() is compatible only with BigDecimal datatype
    }

    private void validateAmount(
            BigDecimal amount, boolean allowZero) {

        if (amount == null) {
            throw new IllegalArgumentException(
                    "Amount cannot be null");
        }

        if (amount.scale() > 2) {//scale() returns the number of digits to the right of the decimal point in a BigDecimal
            throw new IllegalArgumentException(
                    "Amount cannot have more than 2 decimal places");
        }

        int comparison = amount.compareTo(BigDecimal.ZERO);

        if (allowZero ? comparison < 0 : comparison <= 0) {//If allowZero is true, use comparison < 0.
            // Otherwise, use comparison <= 0.

            //1. allowZero is true.
            //2. Java chooses comparison < 0 and ignores comparison <= 0.
            //3. comparison is 0, so 0 < 0 is false.
            //Therefore, result is false.

            //1. allowZero is false.
            //2. Java chooses comparison <= 0 and ignores comparison < 0.
            //3. comparison is 0, so 0 <= 0 is true.
            //Therefore, result is true

            //     When the expression evaluates to true, Java enters the if block and throws the exception.
            //     When it evaluates to false, Java skips the block.
            throw new IllegalArgumentException(
                    allowZero
                            ? "Initial balance cannot be negative"
                            : "Amount must be greater than zero");
        }
    }

    public void displayAccountDetails() {
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Account Holder: " + accountHolderName);
        System.out.println("Balance: ₹" + balance.toPlainString());
    }
    public void updateAccountHolderName(String newName) {
        if (newName == null || newName.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Account holder name cannot be empty");
        }

        this.accountHolderName = newName;
    }
    private void validateAccountNumber(String accountNumber) {
        if (accountNumber == null ||
                !accountNumber.matches("ACC\\d{4}")) {
            throw new IllegalArgumentException(
                    "Account number must follow the format ACC1234");
        }
    }
}

