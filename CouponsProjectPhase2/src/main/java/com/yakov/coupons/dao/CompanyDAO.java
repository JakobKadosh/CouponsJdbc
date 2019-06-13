package com.yakov.coupons.dao;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.yakov.coupons.beans.Company;
import com.yakov.coupons.enums.ErrorType;
import com.yakov.coupons.exceptions.MyException;
import com.yakov.coupons.utils.JdbcUtils;

@Repository
public class CompanyDAO  {

	/**
	 * @author Vision
	 * @param CompanyDBDAO allows to manipulate data in the Companies table in the
	 *                     DB
	 * @throws SQLMyException, MyMyException
	 * 
	 */
	public boolean isCompanyExsitsByMail(String email) throws MyException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = JdbcUtils.getConnection();
			preparedStatement = connection.prepareStatement("SELECT *  FROM Companies WHERE email =? ");
			preparedStatement.setString(1, email);
			resultSet = preparedStatement.executeQuery();
			if (!resultSet.next()) {
				return false;
			}
			return true;
		} catch (SQLException e) {
			throw new MyException(ErrorType.GENERAL_ERROR, e, "Faild to check for company validation");
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}
	}

	
	public boolean isCompanyExistsByName(String name) throws MyException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = JdbcUtils.getConnection();
			preparedStatement = connection.prepareStatement("SELECT *  FROM Companies WHERE company_name =? ");
			preparedStatement.setString(1, name);
			resultSet = preparedStatement.executeQuery();
			if (!resultSet.next()) {
				return false;
			}
			return true;
		} catch (SQLException e) {
			throw new MyException(ErrorType.GENERAL_ERROR, e,  "Faild to check for company validation");
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}
	}

	public boolean isCompanyExists(String email, String name) throws MyException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = JdbcUtils.getConnection();
			preparedStatement = connection.prepareStatement("SELECT * FROM Companies WHERE email =? AND company_name = ?");
			preparedStatement.setString(1, email);
			preparedStatement.setString(2, name);
			resultSet = preparedStatement.executeQuery();
			if (!resultSet.next()) {
				return false;
			}
			return true;

		} catch (SQLException e) {
			throw new MyException(ErrorType.GENERAL_ERROR, e,  "Faild to check for company validation");
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}
	}

	public long addCompany(Company company) throws MyException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = JdbcUtils.getConnection();
			String sql = "INSERT INTO Companies(company_name, email, address)VALUES(?, ?, ?)";
			preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, company.getName());
			preparedStatement.setString(2, company.getEmail());
			preparedStatement.setString(3, company.getAddress());
			preparedStatement.executeUpdate();
			System.out.println("Company "+company.getName()+" added Successfully");
			resultSet = preparedStatement.getGeneratedKeys();
			if (!resultSet.next()) {
				throw new MyException(ErrorType.GENERAL_ERROR, "Faild to add company");
			}
			long id = resultSet.getLong(1);
			company.setId(id);
			return id;
		} catch (SQLException e) {
			throw new MyException(ErrorType.GENERAL_ERROR, e,  "Faild. " + e.getMessage());
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
			
		}
	}

	public void updateCompany(Company company) throws MyException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = JdbcUtils.getConnection();
			String sql = "UPDATE Companies SET email = ?, address = ? WHERE company_id = ?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, company.getEmail());
			preparedStatement.setString(2, company.getAddress());
			preparedStatement.setLong(3, company.getId());
			preparedStatement.executeUpdate();
			System.out.println("Company updated Successfully !");
		} catch (SQLException e) {
			throw new MyException(ErrorType.GENERAL_ERROR, e,  "Faild.  " + e.getMessage());
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}

	public void deleteCompany(long companyID) throws MyException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = JdbcUtils.getConnection();
			preparedStatement = connection.prepareStatement("DELETE FROM Companies WHERE company_id =?");
			preparedStatement.setLong(1, companyID);
			preparedStatement.executeUpdate();
			System.out.println("Delete company success!");
		} catch (SQLException e) {
			throw new MyException(ErrorType.GENERAL_ERROR, e,  "Faild.  " + e.getMessage());
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}

	public List<Company> getAllCompanies() throws MyException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = JdbcUtils.getConnection();
			String sql = "SELECT * FROM Companies";
			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			ArrayList<Company> allCompanies = new ArrayList<Company>();
			while (resultSet.next()) {
				allCompanies.add(extractCompanyFromResultSet(resultSet));
			}
			return allCompanies;
		} catch (SQLException e) {
			throw new MyException(ErrorType.GENERAL_ERROR, e,  "Faild. " + e.getMessage());
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}
	}

	public Company getCompanyByID(long companyID) throws MyException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Company company=new Company();
		try {
			connection = JdbcUtils.getConnection();
			String sql = "SELECT * FROM Companies WHERE company_id = ?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, companyID);
			resultSet = preparedStatement.executeQuery();
			if (!resultSet.next()) {
				return null;
			}
			company=extractCompanyFromResultSet(resultSet);
			return company;
		} catch (SQLException e) {
			throw new MyException(ErrorType.GENERAL_ERROR, e, "Faild.  " + e.getMessage());
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);

		}
	}
	public Company getCompanyByName(String companyName) throws MyException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Company company=new Company();
		try {
			connection = JdbcUtils.getConnection();
			preparedStatement = connection.prepareStatement("SELECT * FROM Companies WHERE company_name = ?");
			preparedStatement.setString(1, companyName);
			resultSet = preparedStatement.executeQuery();
			if (!resultSet.next()) {
				return null;
			}
			company=extractCompanyFromResultSet(resultSet);
			return company;
		} catch (SQLException e) {
			throw new MyException(ErrorType.GENERAL_ERROR, e, "Faild.  " + e.getMessage());
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);

		}
	}
	//this method extracts the Company object from the result set of the data base

	private Company extractCompanyFromResultSet(ResultSet result) throws SQLException, MyException {
		Company company = new Company();
		company.setId(result.getLong("company_id"));
		company.setName(result.getString("company_name"));
		company.setEmail(result.getString("email"));
		company.setAddress(result.getString("address"));

		return company;
	}
	
	
}
