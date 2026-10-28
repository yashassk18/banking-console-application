package com.banking.service;
import java.math.BigDecimal;
import com.banking.model.BankAccount;
import com.banking.model.Customer;

import java.util.HashMap;
import java.util.Map;

import java.util.ArrayList;
import java.util.List;

//BankService will manage registered customers and their bank accounts.
//      Method	           Purpose
//createCustomer()	     Create a customer and their bank account
//findCustomerById()	 Find a registered customer
//findAccountByNumber()  Find an account using its account number


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

    public void deposit(String accountNumber, BigDecimal amount) {
        BankAccount account = findAccountByNumber(accountNumber);
        account.deposit(amount);
    }

    public void withdraw(String accountNumber, BigDecimal amount) {
        BankAccount account = findAccountByNumber(accountNumber);
        account.withdraw(amount);
    }

    public BigDecimal checkBalance(String accountNumber) {
        //Why add this method when BankAccount already has getBalance()?
        //Because callers can request a balance through BankService without
        // needing to retrieve and manage the account object themselves.
        // This also gives us a central place to add authorization or other business rules later
        BankAccount account = findAccountByNumber(accountNumber);
        return account.getBalance();
    }
    public List<Customer> getAllCustomers() {
        return new ArrayList<>(customers.values());
    }
    public List<BankAccount> getAllAccounts() {
        return new ArrayList<>(accounts.values());
    }
    public void updateCustomerDetails(
            String customerId,
            String name,
            String phoneNumber,
            String email) {

        Customer customer = findCustomerById(customerId);//finds customer by id

        customer.updateDetails(name, phoneNumber, email);
    }



}
