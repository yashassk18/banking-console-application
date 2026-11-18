
package com.banking.dao;

import com.banking.model.BankAccount;
import java.sql.ResultSet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BankAccountDAO {

    public void saveBankAccount(Connection connection, BankAccount account,
                                String customerId) throws SQLException {

        String sql = """
                INSERT INTO bank_accounts
                (account_number, customer_id, balance)
                VALUES (?, ?, ?)
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, account.getAccountNumber());
            statement.setString(2, customerId);
            statement.setBigDecimal(3, account.getBalance());

            statement.executeUpdate();
        }
    }
    public BankAccount findAccountByCustomerId(
            Connection connection,
            String customerId) throws SQLException {

        String sql = """
        SELECT ba.account_number, ba.balance, c.name
        FROM bank_accounts ba
        JOIN customers c
        ON ba.customer_id = c.customer_id
        WHERE ba.customer_id = ?
        """;

        try (PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, customerId);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {

                    return new BankAccount(
                            resultSet.getString("account_number"),
                            resultSet.getString("name"),
                            resultSet.getBigDecimal("balance")
                    );
                }
            }
        }

        return null;
    }
}
