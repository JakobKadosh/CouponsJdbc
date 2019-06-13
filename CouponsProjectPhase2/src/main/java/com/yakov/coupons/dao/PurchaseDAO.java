package com.yakov.coupons.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.yakov.coupons.beans.Purchase;
import com.yakov.coupons.enums.ErrorType;
import com.yakov.coupons.exceptions.MyException;
import com.yakov.coupons.utils.JdbcUtils;

@Repository
public class PurchaseDAO {

	CouponDAO couponDAO = new CouponDAO();

	public long purchaseCoupon(long customerId, long couponId, int amount) throws MyException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = JdbcUtils.getConnection();
			String sql = "INSERT INTO Purchases (customer_id, coupon_id,amount) VALUES(?,?,?)";
			preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			preparedStatement.setLong(1, customerId);
			preparedStatement.setLong(2, couponId);
			preparedStatement.setInt(3, amount);
			preparedStatement.executeUpdate();
			System.out.println("Coupon purchased succesfully");
			resultSet = preparedStatement.getGeneratedKeys();
			if (!resultSet.next()) {
				System.out.println("purchase faild");
				throw new MyException(ErrorType.GENERAL_ERROR, "Faild to purchase");
			}
			long id = resultSet.getLong(1);

//after a purchase has made we need to update the amount left of that  coupon

			String amountSet = "UPDATE Coupons SET amount = (amount-" + amount + ") WHERE coupon_id =? ";
			preparedStatement = connection.prepareStatement(amountSet, PreparedStatement.RETURN_GENERATED_KEYS);
			preparedStatement.setLong(1, couponId);
			preparedStatement.executeUpdate();
			return id;
		} catch (SQLException e) {
			throw new MyException(ErrorType.GENERAL_ERROR, e, "Faild. " + e.getMessage());
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}
	}

	public void deleteCouponPurchase(long customerId, long couponId) throws MyException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = JdbcUtils.getConnection();
			preparedStatement = connection
					.prepareStatement("DELETE FROM Purchases WHERE coupon_id =? AND customer_id=?");
			preparedStatement.setLong(1, couponId);
			preparedStatement.setLong(2, customerId);
			preparedStatement.executeUpdate();
			System.out.println("Purchase deleted successfully");
		} catch (SQLException e) {
			throw new MyException(ErrorType.GENERAL_ERROR, e, "Faild. " + e.getMessage());
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}

	public void deletePurchaseByCouponId(long couponId) throws MyException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = JdbcUtils.getConnection();
			preparedStatement = connection.prepareStatement("DELETE FROM Purchases WHERE coupon_id = ?");
			preparedStatement.setLong(1, couponId);
			preparedStatement.executeUpdate();
			System.out.println("Purchase deleted successfully");
		} catch (SQLException e) {
			throw new MyException(ErrorType.GENERAL_ERROR, e, "Faild. " + e.getMessage());
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}

	public void deletePurchaseByPurchaseId(long purchaseID) throws MyException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = JdbcUtils.getConnection();
			preparedStatement = connection.prepareStatement("DELETE FROM Purchases WHERE purchase_id = ?");
			preparedStatement.setLong(1, purchaseID);
			preparedStatement.executeUpdate();
			System.out.println("Purchase deleted successfully");
		} catch (SQLException e) {
			throw new MyException(ErrorType.GENERAL_ERROR, e, "Faild. " + e.getMessage());
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}

	public void deleteCustomerPurchases(long customerId) throws MyException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = JdbcUtils.getConnection();
			preparedStatement = connection.prepareStatement("DELETE FROM Purchases WHERE customer_id = ?");
			preparedStatement.setLong(1, customerId);
			preparedStatement.executeUpdate();
			System.out.println("Purchase deleted successfully");
		} catch (SQLException e) {
			throw new MyException(ErrorType.GENERAL_ERROR, e, "Faild. " + e.getMessage());
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}

	public void deleteExpiredCoupons() throws MyException, SQLException {
		PreparedStatement preparedStatement = null;
		Connection connection = null;
		try {
			connection = JdbcUtils.getConnection();
			preparedStatement = connection.prepareStatement(
					"DELETE FROM purchases WHERE coupon_id IN(SELECT coupon_id FROM coupons WHERE end_date < CURDATE())");
			preparedStatement.executeQuery();
		} catch (SQLException e) {
			throw new MyException(ErrorType.GENERAL_ERROR, e, " Faild to delete expired coupons");
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}

	public Purchase getPurchaseById(long id) throws MyException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = JdbcUtils.getConnection();
			preparedStatement = connection.prepareStatement("SELECT * FROM Purchases WHERE purchase_id=?");
			preparedStatement.setLong(1, id);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				return getPurchaseFromResult(resultSet);
			} else {
				throw new MyException(ErrorType.GENERAL_ERROR, "Faild to get purchase from the data base");
			}
		} catch (SQLException e) {
			throw new MyException(ErrorType.GENERAL_ERROR, e, "faild to get purchase from data base");
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);

		}
	}

	public List<Purchase> getAllPurchases() throws MyException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = JdbcUtils.getConnection();
			preparedStatement = connection.prepareStatement("SELECT * FROM Purchases");
			resultSet = preparedStatement.executeQuery();
			ArrayList<Purchase> allPurchases = new ArrayList<Purchase>();

			// get results and convert them into Coupon objects
			while (resultSet.next()) {
				allPurchases.add(getPurchaseFromResult(resultSet));
			}

			return allPurchases;
		} catch (SQLException e) {
			throw new MyException(ErrorType.GENERAL_ERROR, e, "Faild. " + e.getMessage());
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}
	}

	

	private Purchase getPurchaseFromResult(ResultSet resultSet) throws SQLException, MyException {
		Purchase purchase = new Purchase();
		try {
			purchase.setPurchase_id(resultSet.getLong("purchase_id"));
			purchase.setCustomer_id(resultSet.getLong("customer_id"));
			purchase.setCoupon_id(resultSet.getLong("coupon_ID"));
			purchase.setAmount(resultSet.getInt("amount"));
		} catch (SQLException e) {
			throw new MyException(ErrorType.GENERAL_ERROR, e, "Faild to get purchase from data base");
		}
		return purchase;
	}

}
