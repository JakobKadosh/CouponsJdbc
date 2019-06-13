package com.yakov.coupons.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.yakov.coupons.beans.Customer;
import com.yakov.coupons.enums.ErrorType;
import com.yakov.coupons.exceptions.MyException;
import com.yakov.coupons.utils.JdbcUtils;

@Repository
public class CustomerDAO {
	/**
	 * @param customer allows to manipulate data in the Customers table in the
	 *                 database
	 * @throws Exception , SQLException
	 */

	public boolean isCustomerExist(String email, String password) throws MyException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			// get connection
			connection = JdbcUtils.getConnection();
			// prepare query

			preparedStatement = connection
					.prepareStatement("SELECT *  FROM Customers WHERE email = ? AND password = ?");
			preparedStatement.setString(1, email);
			preparedStatement.setString(2, password);
			// execute
			resultSet = preparedStatement.executeQuery();
			if (!resultSet.next()) {
				return false;
			}
			// return answer
			throw new MyException(ErrorType.NAME_IS_ALREADY_EXISTS, "name or password are already exsists on the DB");
		} catch (SQLException e) {
			throw new MyException(ErrorType.GENERAL_ERROR, e,  "Faild. " + e.getMessage());
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}
	}

	public void addCustomer(Customer customer) throws MyException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			// get connection
			connection = JdbcUtils.getConnection();

			// prepare query
			String sql = "INSERT INTO Customers ( customer_id, first_name, last_name, email, password) VALUES(?,?,?,?,?)";

			preparedStatement = connection.prepareStatement(sql);
			// execute

			preparedStatement.setLong(1, customer.getCustomerId());
			preparedStatement.setString(2, customer.getFirstName());
			preparedStatement.setString(3, customer.getLastName());
			preparedStatement.setString(4, customer.getEmail());
			preparedStatement.setString(5, customer.getPassword());

			preparedStatement.executeUpdate();

			// get new id assigned by db
			System.out.println("Customer added Succesfully!");
		} catch (SQLException e) {
			throw new MyException(ErrorType.GENERAL_ERROR, e, "Faild. " + e.getMessage());
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);

		}
	}

	public void updateCustomer(Customer customer) throws MyException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			// get connection
			connection = JdbcUtils.getConnection();

			// prepare query
			String sql = "UPDATE Customers SET first_name = ?, last_name = ?, email = ?, password = ?"
					+ " WHERE customer_id = ?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, customer.getFirstName());
			preparedStatement.setString(2, customer.getLastName());
			preparedStatement.setString(3, customer.getEmail());
			preparedStatement.setString(4, customer.getPassword());
			preparedStatement.setLong(5, customer.getCustomerId());
			preparedStatement.executeUpdate();

			System.out.println("Customer updated successfully!");

		} catch (SQLException e) {
			throw new MyException(ErrorType.GENERAL_ERROR, e,  "Faild. " + e.getMessage());
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement);

		}

	}

	public void deleteCustomer(long customerID) throws MyException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = JdbcUtils.getConnection();

			preparedStatement = connection.prepareStatement("DELETE FROM Customers WHERE customer_id =?");
			preparedStatement.setLong(1, customerID);
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			throw new MyException(ErrorType.GENERAL_ERROR, e,  "Faild. " + e.getMessage());
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement);

		}
	}
	

	public List<Customer> getAllCustomers() throws MyException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList<Customer> allCustomers = new ArrayList<Customer>();

		try {
			connection = JdbcUtils.getConnection();
			String sql = "SELECT * FROM Customers";
			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				allCustomers.add(extractCustomerFromResultSet(resultSet));
			}
			return allCustomers;
		} catch (SQLException e) {
			throw new MyException(ErrorType.GENERAL_ERROR, e,  "Faild. " + e.getMessage());
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}
	}

	public Customer getCustomerByID(long customerID) throws MyException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Customer customer = new Customer();
		try {
			connection = JdbcUtils.getConnection();

			preparedStatement = connection.prepareStatement("SELECT * FROM Customers WHERE customer_id =?");
			preparedStatement.setLong(1, customerID);
			resultSet = preparedStatement.executeQuery();

			if (!resultSet.next()) {
				return null;
			}

			customer = extractCustomerFromResultSet(resultSet);
			return customer;

		} catch (SQLException e) {
			throw new MyException(ErrorType.GENERAL_ERROR, e, "Faild. " + e.getMessage());
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);

		}
	}

//this method extracts the customer object from the result set of the data base
	private Customer extractCustomerFromResultSet(ResultSet result) throws SQLException, MyException {
		Customer customer = new Customer();
		customer.setCustomerId(result.getLong("customer_id"));
		customer.setFirstName(result.getString("first_name"));
		customer.setLastName(result.getString("last_name"));
		customer.setEmail(result.getString("email"));
		customer.setPassword(result.getString("password"));

		return customer;
	}

}
