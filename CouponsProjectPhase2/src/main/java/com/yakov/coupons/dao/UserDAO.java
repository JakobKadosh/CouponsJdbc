package com.yakov.coupons.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.yakov.coupons.beans.User;
import com.yakov.coupons.enums.ClientType;
import com.yakov.coupons.enums.ErrorType;
import com.yakov.coupons.exceptions.MyException;
import com.yakov.coupons.utils.JdbcUtils;

@Repository
public class UserDAO  {

	public long addUser(User user) throws MyException {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = JdbcUtils.getConnection();
			String sqlStatement = null;
			/*
			 * there's a problem with setting a user object from or to the Data Base. 
			 * because some users have company id and some don't. 
			 * here i'm dealing with the problem with an if and else statement. 
			 */
			
			if (user.getCompanyId() == null) {
				sqlStatement = "INSERT INTO Users (user_name, user_password, client_type) VALUES(?,?,?)";
				preparedStatement = connection.prepareStatement(sqlStatement, PreparedStatement.RETURN_GENERATED_KEYS);
				preparedStatement.setString(1, user.getUserName());
				preparedStatement.setString(2, user.getUserPassword());
				preparedStatement.setString(3, user.getClientType().name());

			} else {

				sqlStatement = "INSERT INTO Users (user_name, user_password, client_type,company_id) VALUES(?,?,?,?)";
				preparedStatement = connection.prepareStatement(sqlStatement, PreparedStatement.RETURN_GENERATED_KEYS);

				preparedStatement.setString(1, user.getUserName());
				preparedStatement.setString(2, user.getUserPassword());
				preparedStatement.setString(3, user.getClientType().name());
				preparedStatement.setLong(4, user.getCompanyId());

			}

			preparedStatement.executeUpdate();
			resultSet = preparedStatement.getGeneratedKeys();
			if (!resultSet.next()) {
				throw new MyException(ErrorType.GENERAL_ERROR, "Faild to insert a user");
			}
			long id = resultSet.getLong(1);
			user.setUserId(id);
			System.out.println("User added successfully");
			return id;
		} catch (SQLException e) {
			throw new MyException(ErrorType.GENERAL_ERROR, e, " Create User failed");
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}
	}

	public ClientType login(String user, String password) throws MyException {
		// Turn on the connections
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		try {
			connection = JdbcUtils.getConnection();

			preparedStatement = connection.prepareStatement("SELECT * FROM Users WHERE user_name = ? AND user_password = ?");

			preparedStatement.setString(1, user);
			preparedStatement.setString(2, password);

			result = preparedStatement.executeQuery();
			if (!result.next()) {
				throw new MyException(ErrorType.LOGIN_FAILED," Login Failed");
			}

			ClientType clientType = ClientType.valueOf(result.getString("client_type"));
			return clientType;
		} catch (SQLException e) {
			throw new MyException(ErrorType.GENERAL_ERROR, e," Get user has failed");
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement, result);
		}
	}

	public boolean isUserExistByID(long userID) throws MyException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = JdbcUtils.getConnection();


			preparedStatement = connection.prepareStatement("SELECT * FROM Users WHERE user_id = ?");
			preparedStatement.setLong(1, userID);
			resultSet = preparedStatement.executeQuery();
			if (!resultSet.next()) {
				return false;
			}
			return true;
		} catch (SQLException e) {
			throw new MyException(ErrorType.GENERAL_ERROR, e, "Faild. " + e.getMessage());
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);

		}

	}

	public boolean isUserExistByName(String userName) throws MyException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = JdbcUtils.getConnection();

			preparedStatement = connection.prepareStatement("SELECT * FROM Users WHERE user_name = ?");
			preparedStatement.setString(1, userName);
			resultSet = preparedStatement.executeQuery();
			if (!resultSet.next()) {
				return false;
			}
			return true;
		} catch (SQLException e) {
			throw new MyException(ErrorType.GENERAL_ERROR, e,"Faild. " + e.getMessage());
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);

		}

	}

	public User getUser(long userID) throws MyException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = JdbcUtils.getConnection();

			preparedStatement = connection.prepareStatement("SELECT * FROM Users WHERE user_id = ?");
			preparedStatement.setLong(1, userID);
			resultSet = preparedStatement.executeQuery();

			if (!resultSet.next()) {
				return null;
			}
			User user = new User();
			user = extractUserFromResltSet(resultSet);
			return user;

		} catch (SQLException e) {
			throw new MyException(ErrorType.GENERAL_ERROR, e,"Faild. " + e.getMessage());
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}
	}
	
	public User getUserByName(String userName) throws MyException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = JdbcUtils.getConnection();

			preparedStatement = connection.prepareStatement("SELECT * FROM Users WHERE user_name= ?");
			preparedStatement.setString(1, userName);
			resultSet = preparedStatement.executeQuery();

			if (!resultSet.next()) {
				return null;
			}
			User user = new User();
			user = extractUserFromResltSet(resultSet);
			return user;

		} catch (SQLException e) {
			throw new MyException(ErrorType.GENERAL_ERROR, e,"Faild. " + e.getMessage());
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}
	}
	
	public List<User> getUsersByCompanyId(long companyId) throws MyException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList<User>usersByCompany=new ArrayList<User>();
		
		try {
			connection = JdbcUtils.getConnection();

			preparedStatement = connection.prepareStatement("SELECT * FROM Users WHERE company_id= ?");
			preparedStatement.setLong(1, companyId);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
					usersByCompany.add(extractUserFromResltSet(resultSet));
			}
			
			return usersByCompany;

		} catch (SQLException e) {
			throw new MyException(ErrorType.GENERAL_ERROR, e,"Faild. " + e.getMessage());
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}
	}
	
	public List<User> getAllUsers() throws MyException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList<User>users=new ArrayList<User>();
		
		try {
			connection = JdbcUtils.getConnection();

			preparedStatement = connection.prepareStatement("SELECT * FROM Users");
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
					users.add(extractUserFromResltSet(resultSet));
			}
			
			return users;

		} catch (SQLException e) {
			throw new MyException(ErrorType.GENERAL_ERROR, e,"Faild. " + e.getMessage());
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}
	}
	
	
	public void deleteUser(long userID) throws MyException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = JdbcUtils.getConnection();

			preparedStatement = connection.prepareStatement("DELETE FROM users WHERE user_id=?");
			preparedStatement.setLong(1, userID);

			preparedStatement.executeUpdate();
			System.out.println("User deleted successfully");

		} catch (SQLException e) {
			throw new MyException(ErrorType.GENERAL_ERROR, e, "Faild. " + e.getMessage());
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement);

		}

	}

	public void updateUser(User user) throws MyException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = JdbcUtils.getConnection();
			String sql = null;
			if (user.getCompanyId() == null) {
				sql = "UPDATE Users SET user_name = ?, client_type = ?, user_password = ?" + " WHERE user_id = ?";
				preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setString(1, user.getUserName());
				preparedStatement.setString(2, user.getClientType().name());
				preparedStatement.setString(3, user.getUserPassword());
				preparedStatement.setLong(4, user.getUserId());
			} else {
				sql = "UPDATE Users SET user_name = ?, client_type = ?, user_password = ?, company_id = ?"
						+ " WHERE user_id = ?";
				preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setString(1, user.getUserName());
				preparedStatement.setString(2, user.getClientType().name());
				preparedStatement.setString(3, user.getUserPassword());
				preparedStatement.setLong(4, user.getCompanyId());
				preparedStatement.setLong(5, user.getUserId());
			}

			System.out.println("Customer updated successfully!");

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			throw new MyException(ErrorType.GENERAL_ERROR, e, "Faild. " + e.getMessage());
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement);

		}

	}


	private User extractUserFromResltSet(ResultSet result) throws MyException {
		User user = new User();
		try {
			user.setUserId(result.getLong("user_id"));
			user.setCompanyId(result.getLong("company_id"));
			user.setUserName(result.getString("user_name"));
			user.setUserPassword(result.getString("user_password"));
			user.setClientType(ClientType.valueOf(result.getString("client_type")));
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return user;
	}
}
