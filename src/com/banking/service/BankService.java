package com.banking.service;
import java.math.BigDecimal;
import com.banking.model.BankAccount;
import com.banking.model.Customer;

import java.util.HashMap;
import java.util.Map;

public class BankService {

    private final Map<String, Customer> customers = new HashMap<>();
    private final Map<String, BankAccount> accounts = new HashMap<>();


    public Customer createCustomer(
            String customerId,
            String name,
            String phoneNumber,
            String email,
            String accountNumber,
            BigDecimal initialBalance) {

        if (customers.containsKey(customerId)) {
            throw new IllegalArgumentException(
                    "Customer ID already exists");
        }

        if (accounts.containsKey(accountNumber)) {
            throw new IllegalArgumentException(
                    "Account number already exists");
        }

        BankAccount account = new BankAccount(
                accountNumber, name, initialBalance);

        Customer customer = new Customer(
                customerId, name, phoneNumber, email, account);

        customers.put(customerId, customer);
        accounts.put(accountNumber, account);

        return customer;
    }
    public Customer findCustomerById(String customerId) {
        Customer customer = customers.get(customerId);

        if (customer == null) {
            throw new IllegalArgumentException(
                    "Customer not found: " + customerId);
        }

        return customer;
    }
    public BankAccount findAccountByNumber(String accountNumber) {
        BankAccount account = accounts.get(accountNumber);

        if (account == null) {
            throw new IllegalArgumentException(
                    "Account not found: " + accountNumber);
        }

        return account;
    }
}
