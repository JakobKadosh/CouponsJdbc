package com.yakov.coupons.logic;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.yakov.coupons.beans.Coupon;
import com.yakov.coupons.beans.Purchase;
import com.yakov.coupons.dao.CouponDAO;
import com.yakov.coupons.dao.PurchaseDAO;
import com.yakov.coupons.enums.ErrorType;
import com.yakov.coupons.exceptions.MyException;
import com.yakov.coupons.utils.InputChecker;

@Controller
public class PurchaseController {
	// getting variables of all the dao in class level to be used in the controller
	// functionality
	@Autowired
	private PurchaseDAO purchasesDAO;
	@Autowired
	private CouponDAO couponDAO;

	public long purchaseCoupon(long customerID, long couponID, int amount) throws MyException {
		if (!InputChecker.isValidId(customerID)) {
			throw new MyException(ErrorType.INVALID_ID, "invalid customer id");
		} else if (!InputChecker.isValidId(couponID)) {
			throw new MyException(ErrorType.INVALID_ID, "invalid coupon id");
		} else if (!couponDAO.isCouponAvailble(couponID)) {
			throw new MyException(ErrorType.COUPON_IS_OUT_OF_ORDER, "Coupon is not available");
		} else if (amount < 1) {
			throw new MyException(ErrorType.GENERAL_ERROR, "invalid amount ");
		}
		return purchasesDAO.purchaseCoupon(customerID, couponID, amount);
	}

	public List<Purchase> getAllPurchases() throws MyException {
		return purchasesDAO.getAllPurchases();
	}

	//get all the purchased coupons as coupons and set the coupon amount as the amount in the purchase table
	
	public List<Coupon> getAllPurchasedCoupons() throws MyException {
		List<Purchase> purchases = new ArrayList<Purchase>();
		purchases.addAll(getAllPurchases());
		List<Coupon> purchasedCoupons = new ArrayList<Coupon>();

		for (int i = 0; i < purchases.size(); i++) {
			Coupon coupon = couponDAO.getCouponById(purchases.get(i).getCoupon_id());
			coupon.setAmount(purchases.get(i).getAmount());
			purchasedCoupons.add(coupon);
		}

		return purchasedCoupons;
	}

	public Purchase getPurchaseById(long id) throws MyException {
		if (!InputChecker.isValidId(id)) {
			throw new MyException(ErrorType.INVALID_ID, "Invalid purchase id");
		}
		return purchasesDAO.getPurchaseById(id);
	}

	public void deleteCouponPurchase(long customerID, long couponID) throws MyException {
		if (!InputChecker.isValidId(customerID)) {
			throw new MyException(ErrorType.INVALID_ID, "Invalid customer id");
		} else if (!InputChecker.isValidId(couponID)) {
			throw new MyException(ErrorType.INVALID_ID, "Invalid coupon  id");
		}
		purchasesDAO.deleteCouponPurchase(customerID, couponID);
	}

	public void deletePurchaseByCouponID(long couponID) throws MyException {
		if (!InputChecker.isValidId(couponID)) {
			throw new MyException(ErrorType.INVALID_ID, "Invalid coupon id");
		}

		purchasesDAO.deletePurchaseByCouponId(couponID);
	}

	public void deleteCustomerPurchases(long customerID) throws MyException {
		if (!InputChecker.isValidId(customerID)) {
			throw new MyException(ErrorType.INVALID_ID, "Invalid customer id");
		}

		purchasesDAO.deleteCustomerPurchases(customerID);
	}

}
