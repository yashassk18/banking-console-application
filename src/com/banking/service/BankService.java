package com.banking.service;
import java.math.BigDecimal;
import com.banking.model.BankAccount;
import com.banking.model.Customer;
import com.banking.dao.CustomerDAO;
import com.banking.dao.BankAccountDAO;
import com.banking.config.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;

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
    private final CustomerDAO customerDAO = new CustomerDAO();
    private final BankAccountDAO bankAccountDAO = new BankAccountDAO();

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

        try (Connection connection = DatabaseConnection.getConnection()) {

            connection.setAutoCommit(false);

            customerDAO.saveCustomer(connection, customer);

            bankAccountDAO.saveBankAccount(
                    connection, account, customerId);

            connection.commit();

            customers.put(customerId, customer);
            accounts.put(accountNumber, account);

            return customer;

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Failed to save customer to database", e);
        }
    }

    public Customer findCustomerById(String customerId) {

        Customer customer = customers.get(customerId);

        if (customer != null) {
            return customer;
        }

        try (Connection connection = DatabaseConnection.getConnection()) {

            customer = customerDAO.findCustomerById(connection, customerId);

            if (customer == null) {
                throw new IllegalArgumentException(
                        "Customer not found: " + customerId);
            }

            BankAccount account = customer.getBankAccount();

            customers.put(customerId, customer);
            accounts.put(account.getAccountNumber(), account);

            return customer;

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Failed to retrieve customer from database", e);
        }
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
