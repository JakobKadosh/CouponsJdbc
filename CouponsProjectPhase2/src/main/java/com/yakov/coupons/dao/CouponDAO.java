package com.yakov.coupons.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.yakov.coupons.beans.Coupon;
import com.yakov.coupons.enums.CategoriesEnum;
import com.yakov.coupons.enums.ErrorType;
import com.yakov.coupons.exceptions.MyException;
import com.yakov.coupons.utils.JdbcUtils;

@Repository
public class CouponDAO {

	/**
	 * 
	 * @param CouponDBDAO allows to manipulate data in the Coupons table in the
	 *                    database
	 * @throws MyException , SQLException
	 */

	public boolean isCouponExist(String title, long companyId) throws MyException {
		// init Connection
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			// get connection
			connection = JdbcUtils.getConnection();

			// prepare query
			preparedStatement = connection
					.prepareStatement("SELECT * FROM Coupons WHERE title = ? AND company_id = ? ");
			preparedStatement.setString(1, title);
			preparedStatement.setLong(2, companyId);
			resultSet = preparedStatement.executeQuery();
			if (!resultSet.next()) {
				return false;
			}
			return true;

		} catch (SQLException e) {
			throw new MyException(ErrorType.GENERAL_ERROR, e,  "Faild. " + e.getMessage());
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}

	}

	public long addCoupon(Coupon coupon) throws MyException {

		// init
		Connection connection = null;
		ResultSet resultSet = null;
		PreparedStatement preparedStatement = null;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String startDate = formatter.format(coupon.getStartDate());
		String endDate = formatter.format(coupon.getEndDate());

		try {
//			System.out.println(coupon.toString()+"couponDao70"+"Start date: "+startDate+" End date: "+endDate);

			// get connection
			connection = JdbcUtils.getConnection();

			// prepare query
			String sql = "INSERT INTO Coupons (company_id ,category ,title ,description ,"
					+ " start_date , end_date, amount, price, image)VALUES (?,?,?,?,?,?,?,?,?) ";

			preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			preparedStatement.setLong(1, coupon.getCompanyId());
			preparedStatement.setString(2, coupon.getCategory().name());
			preparedStatement.setString(3, coupon.getTitle());
			preparedStatement.setString(4, coupon.getDescription());
			preparedStatement.setString(5, startDate);
			preparedStatement.setString(6, endDate);
			preparedStatement.setInt(7, coupon.getAmount());
			preparedStatement.setDouble(8, coupon.getPrice());
			preparedStatement.setString(9, coupon.getImage());
			// execute
			preparedStatement.executeUpdate();

			// report success
			resultSet = preparedStatement.getGeneratedKeys();
			if (!resultSet.next()) {
				throw new MyException(ErrorType.GENERAL_ERROR, "Faild to add coupon");
			}
			long id = resultSet.getLong(1);
			coupon.setId(id);
			System.out.println("Coupon Added succesfully!");
			return id;
		} catch (SQLException e) {
			throw new MyException(ErrorType.GENERAL_ERROR, e,  "Faild. " + e.getMessage());
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}
	}

	public void deleteCoupon(long couponId) throws MyException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = JdbcUtils.getConnection();
			preparedStatement = connection.prepareStatement("DELETE FROM Coupons WHERE coupon_id = ?");
			preparedStatement.setLong(1, couponId);
			preparedStatement.executeUpdate();
			System.out.println("Deleted succesfully");
		} catch (SQLException e) {
			throw new MyException(ErrorType.GENERAL_ERROR, e,  "Faild. " + e.getMessage());
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}

	public void deleteCompanyCoupons(long companyId) throws MyException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = JdbcUtils.getConnection();
			preparedStatement = connection.prepareStatement("DELETE FROM Coupons WHERE company_id = ?");
			preparedStatement.setLong(1, companyId);
			preparedStatement.executeUpdate();
			System.out.println("coupons Deleted succesfully");
		} catch (SQLException e) {
			throw new MyException(ErrorType.GENERAL_ERROR, e,  "Faild. " + e.getMessage());
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}
	
	public void updateCoupon(Coupon coupon) throws MyException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String startDate = formatter.format(coupon.getStartDate());
		String endDate = formatter.format(coupon.getEndDate());
		try {
			// get connection
			connection = JdbcUtils.getConnection();

			// prepare query
			String sql = " UPDATE Coupons SET category =?, title = ?, description = ?, start_date = ?,"
					+ "end_date = ?, amount = ?, price = ?, image = ? WHERE coupon_id =?";

			// execute and report success
			preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setString(1, coupon.getCategory().name());
			preparedStatement.setString(2, coupon.getTitle());
			preparedStatement.setString(3, coupon.getDescription());
			preparedStatement.setString(4, startDate);
			preparedStatement.setString(5, endDate);
			preparedStatement.setInt(6, coupon.getAmount());
			preparedStatement.setDouble(7, coupon.getPrice());
			preparedStatement.setString(8, coupon.getImage());
			preparedStatement.setLong(9, coupon.getId());

			preparedStatement.executeUpdate();

			System.out.println("Coupon Updated Succesfully");

		} catch (SQLException e) {
			throw new MyException(ErrorType.GENERAL_ERROR, e,  "Faild. " + e.getMessage());
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}

	public List<Coupon> getCompanysCoupons(long companyId) throws MyException {

		// init Connection
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = JdbcUtils.getConnection();
			preparedStatement = connection.prepareStatement("SELECT * FROM Coupons WHERE company_id = ?");
			preparedStatement.setLong(1, companyId);
			resultSet = preparedStatement.executeQuery();
			ArrayList<Coupon> allCoupons = new ArrayList<Coupon>();
			// get results and convert them into Coupon objects
			while (resultSet.next()) {
				allCoupons.add(extractCouponFromResltSet(resultSet));
			}
			return allCoupons;
		} catch (SQLException e) {
			throw new MyException(ErrorType.GENERAL_ERROR, e,  "Faild. " + e.getMessage());
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}
	}

	public List<Coupon> getCompanysCouponsByCategory(String category, long companyId) throws MyException {
		ArrayList<Coupon> catgoryCouponList = new ArrayList<Coupon>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = JdbcUtils.getConnection();
			preparedStatement = connection
					.prepareStatement("SELECT * FROM Coupons WHERE category = ? AND company_id =?");
			preparedStatement.setString(1, category);
			preparedStatement.setLong(2, companyId);
			resultSet = preparedStatement.executeQuery();
			// get results and convert them into Coupon objects
			while (resultSet.next()) {
				catgoryCouponList.add(extractCouponFromResltSet(resultSet));
			}
			return catgoryCouponList;
		} catch (SQLException e) {
			throw new MyException(ErrorType.GENERAL_ERROR, e,  "Faild. " + e.getMessage());
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}
	}

	public List<Coupon> getCouponsByCompany(long companyId) throws MyException {
		ArrayList<Coupon> companyCouponsList = new ArrayList<Coupon>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = JdbcUtils.getConnection();
			preparedStatement = connection.prepareStatement("SELECT * FROM Coupons WHERE company_id = ?");
			preparedStatement.setLong(1, companyId);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				companyCouponsList.add(extractCouponFromResltSet(resultSet));
			}
			return companyCouponsList;
		} catch (SQLException e) {
			throw new MyException(ErrorType.GENERAL_ERROR, e,  "Faild. " + e.getMessage());
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}
	}

	public List<Coupon> getAllCoupons() throws MyException {
		ArrayList<Coupon> couponsList = new ArrayList<Coupon>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = JdbcUtils.getConnection();
			preparedStatement = connection.prepareStatement("SELECT * FROM Coupons ");
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				couponsList.add(extractCouponFromResltSet(resultSet));
			}
			return couponsList;
		} catch (SQLException e) {
			throw new MyException(ErrorType.GENERAL_ERROR, e,  "Faild. " + e.getMessage());
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}
	}

	public Coupon getCouponById(long couponId) throws MyException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = JdbcUtils.getConnection();
			preparedStatement = connection.prepareStatement("SELECT * FROM Coupons WHERE coupon_id =?");
			preparedStatement.setLong(1, couponId);
			resultSet = preparedStatement.executeQuery();
			if (!resultSet.next()) {
				return null;
			}
			return extractCouponFromResltSet(resultSet);
		} catch (SQLException e) {
			throw new MyException(ErrorType.GENERAL_ERROR, e,  "Faild. " + e.getMessage());
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}
	}

	public List<Coupon> getCompanysCouponsByPrice(long companyId, double couponPrice) throws MyException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = JdbcUtils.getConnection();
			preparedStatement = connection.prepareStatement("SELECT * FROM Coupons WHERE company_id =? AND price <= ?");
			preparedStatement.setLong(1, companyId);
			preparedStatement.setDouble(2, couponPrice);
			resultSet = preparedStatement.executeQuery();
			ArrayList<Coupon> couponPriceList = new ArrayList<Coupon>();
			while (resultSet.next()) {
				couponPriceList.add(extractCouponFromResltSet(resultSet));
			}
			return couponPriceList;
		} catch (SQLException e) {
			throw new MyException(ErrorType.GENERAL_ERROR, e,  "Faild. " + e.getMessage());
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}
	}

	public boolean isCouponAvailble(long couponId) throws MyException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		int upAmount = 0;
		Date dateValidate = new Date();
		Date current = new Date();

		try {
			connection = JdbcUtils.getConnection();
			preparedStatement = connection.prepareStatement("SELECT amount FROM Coupons WHERE coupon_id=?");
			preparedStatement.setLong(1, couponId);
			resultSet = preparedStatement.executeQuery();
			if (!resultSet.next()) {
				return false;
			}
			upAmount = resultSet.getInt("amount");
//check if that coupon isn't expired 
			preparedStatement = connection.prepareStatement("SELECT end_date FROM Coupons WHERE coupon_id =?");
			preparedStatement.setLong(1, couponId);
			resultSet = preparedStatement.executeQuery();
			if (!resultSet.next()) {
				return false;
			}
			dateValidate = resultSet.getDate("end_date");
			// Test validity
			if (upAmount > 0 && dateValidate.after(current)) {
				return true;
			} else {
				return false;
			}

		} catch (SQLException e) {
			throw new MyException(ErrorType.GENERAL_ERROR, e,  "Faild. " + e.getMessage());
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}
	}

	public List<Coupon> getCouponsByCustomer(long customerId) throws MyException {
		ArrayList<Coupon> customerCouponList = new ArrayList<Coupon>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = JdbcUtils.getConnection();
			// prepare and execute query
			// here i need to get the coupon id variable from the purchases table in the
			// data base.
			// im doing that by putting a sql statement that will retrieve that data as a
			// variable
			preparedStatement = connection.prepareStatement(
					"SELECT * FROM Coupons LEFT JOIN purchases ON "
							+ "coupons.coupon_id = purchases.coupon_id WHERE purchases.customer_id=?");
			preparedStatement.setLong(1, customerId);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				customerCouponList.add(extractCouponFromResltSet(resultSet));
			}
			return customerCouponList;

		} catch (SQLException e) {
			throw new MyException(ErrorType.GENERAL_ERROR, e,  "Faild. " + e.getMessage());
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}
	}

	public List<Coupon> getCustomersCouponsByCategory(CategoriesEnum category, long customerId) throws MyException {
		ArrayList<Coupon> customerCouponList = new ArrayList<Coupon>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = JdbcUtils.getConnection();
			// here i need to get the coupon id variable from the purchases table in the
			// data base.
			// im doing that by putting a sql statement that will retrieve that data as a
			// variable
			preparedStatement = connection.prepareStatement("SELECT * FROM Coupons LEFT JOIN purchases ON "
					+ "coupons.coupon_id = purchases.coupon_id WHERE purchases.customer_id=? AND coupons.category=?");
			preparedStatement.setLong(1, customerId);
			preparedStatement.setString(2,category.name());
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				customerCouponList.add(extractCouponFromResltSet(resultSet));
			}
			return customerCouponList;
		} catch (SQLException e) {
			throw new MyException(ErrorType.GENERAL_ERROR, e,  "Faild. " + e.getMessage());
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}
	}

	public List<Coupon> getCustomersCouponsByPrice(long customerId, double couponPrice) throws MyException {
		ArrayList<Coupon> customerCouponList = new ArrayList<Coupon>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = JdbcUtils.getConnection();
			// here i need to get the coupon id variable from the purchases table in the
			// data base.
			// im doing that by putting a sql statement that will retrieve that data as a
			// variable
			preparedStatement = connection.prepareStatement("SELECT * FROM Coupons WHERE coupon_id ="
					+ "(SELECT coupon_id FROM Purchases WHERE customer_id = ?) AND price = ?");
			preparedStatement.setLong(1, customerId);
			preparedStatement.setDouble(2, couponPrice);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				customerCouponList.add(extractCouponFromResltSet(resultSet));
			}
			return customerCouponList;
		} catch (SQLException e) {
			throw new MyException(ErrorType.GENERAL_ERROR, e,  "Faild. " + e.getMessage());
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}
	}

	public void deleteExpiredCoupons() throws MyException, SQLException {
		System.out.println("Checking for expiared coupons...");
		PreparedStatement preparedStatement = null;
		Connection connection = null;
		try {
			connection = JdbcUtils.getConnection();
			// deleting expired coupons from Coupons table
			// CURDATE() is a sql function that gets the current date
			preparedStatement = connection.prepareStatement("DELETE FROM Coupons WHERE end_date < CURDATE()");
			preparedStatement.executeUpdate();
			System.out.println("Expired coupons deleted successfully");
		} catch (SQLException e) {
			throw new MyException(ErrorType.GENERAL_ERROR, e,  "Faild to delete expired coupons");
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement);

		}
	}

//this method extracts the coupon object from the result set of the data base
	private Coupon extractCouponFromResltSet(ResultSet result) throws SQLException, MyException {
		Coupon coupon = new Coupon();

		// instead of writing this code in every function that retrieves coupon/s from
		// the DB,
		coupon.setId(result.getLong("coupon_id"));
		coupon.setCompanyId(result.getLong("company_id"));
		coupon.setCategory(CategoriesEnum.valueOf(result.getString("category")));
		coupon.setTitle(result.getString("title"));
		coupon.setDescription(result.getString("description"));
		coupon.setStartDate(result.getDate("start_date"));
		coupon.setEndDate(result.getDate("end_date"));
		coupon.setAmount(result.getInt("amount"));
		coupon.setImage(result.getString("image"));
		coupon.setPrice(result.getDouble("price"));

		return coupon;
	}

}
