package com.banking.dao;
import com.banking.model.BankAccount;
import com.banking.config.DatabaseConnection;
import com.banking.model.Customer;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CustomerDAO {



    public void saveCustomer(Connection connection, Customer customer)
            throws SQLException {

        String sql = """
            INSERT INTO customers
            (customer_id, name, phone_number, email)
            VALUES (?, ?, ?, ?)
            """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, customer.getCustomerId());
            statement.setString(2, customer.getName());
            statement.setString(3, customer.getPhoneNumber());
            statement.setString(4, customer.getEmail());

            statement.executeUpdate();
        }
    }

    public Customer findCustomerById(Connection connection,
                                     String customerId) throws SQLException {

        String sql = """
            SELECT c.customer_id, c.name, c.phone_number, c.email,
                   ba.account_number, ba.balance
            FROM customers c
            JOIN bank_accounts ba
            ON c.customer_id = ba.customer_id
            WHERE c.customer_id = ?
            """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, customerId);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {

                    BankAccount account = new BankAccount(
                            resultSet.getString("account_number"),
                            resultSet.getString("name"),
                            resultSet.getBigDecimal("balance")
                    );

                    return new Customer(
                            resultSet.getString("customer_id"),
                            resultSet.getString("name"),
                            resultSet.getString("phone_number"),
                            resultSet.getString("email"),
                            account
                    );
                }
            }
        }

        return null;
    }


}