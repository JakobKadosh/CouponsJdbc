package com.yakov.coupons.logic;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.yakov.coupons.beans.Coupon;
import com.yakov.coupons.dao.CouponDAO;
import com.yakov.coupons.dao.PurchaseDAO;
import com.yakov.coupons.enums.CategoriesEnum;
import com.yakov.coupons.enums.ErrorType;
import com.yakov.coupons.exceptions.MyException;
import com.yakov.coupons.utils.InputChecker;

@Repository
public class CouponController {
	
	@Autowired
	private CouponDAO couponDAO;

	public void updateCoupon(Coupon coupon) throws MyException {
		if (coupon != null) {

			couponDAO.updateCoupon(coupon);
		} else {
			throw new MyException(ErrorType.GENERAL_ERROR, "coupon is null!!");
		}
	}

	public long addCoupon(Coupon coupon) throws MyException {
		// input validation
//		System.out.println(coupon.toString()+"couponController35");

		if (isCouponValidToAdd(coupon)) {
			return couponDAO.addCoupon(coupon);
		} else {
			throw new MyException(ErrorType.GENERAL_ERROR, "Faild to add Coupon");
		}
	}
	
	public Coupon getCouponById(long id) throws MyException {
		if (InputChecker.isValidId(id)) {
			return couponDAO.getCouponById(id);
		} else {
			throw new MyException(ErrorType.INVALID_ID, "invlid id");
		}
	}
	//SQL is responsible to delete all dependencies "ON DELETE CASCADE"

	public void deleteCoupon(long couponId) throws MyException {
		if (InputChecker.isValidId(couponId)) {
			couponDAO.deleteCoupon(couponId);
		} else {
			throw new MyException(ErrorType.INVALID_ID, "invalid id");
		}
	}
	

	public List<Coupon> getAllCoupons() throws MyException{
		
		return couponDAO.getAllCoupons();
	}
	
	public List<Coupon> getCompanysCoupons(long companyId) throws MyException {
		if (InputChecker.isValidId(companyId)) {
			return couponDAO.getCompanysCoupons(companyId);
		} else
			throw new MyException(ErrorType.INVALID_ID, "invalid id");
	}

	public List<Coupon> getCompanysCouponsByCategory(long companyId, String category) throws MyException {
		if (!InputChecker.isValidId(companyId)) {
			throw new MyException(ErrorType.INVALID_ID, "compnayId is not valid");
		} else {
			return couponDAO.getCompanysCouponsByCategory(category, companyId);
		}
	}

	public List<Coupon> getCompanysCouponsByPrice(long companyId, double price) throws MyException {
		if (InputChecker.isValidId(companyId)) {
			return couponDAO.getCompanysCouponsByPrice(companyId, price);
		} else {
			throw new MyException(ErrorType.INVALID_ID, "invalid id");
		}
	}

	public List<Coupon> getCustomersCouponsByPrice(long customerId, double price) throws MyException {
		if (!InputChecker.isValidId(customerId)) {
			throw new MyException(ErrorType.INVALID_ID, "invalid Id");
		} else if (!InputChecker.isValidPrice(price)) {
			throw new MyException(ErrorType.INVALID_PRICE, "invalid price");
		} else {
			return couponDAO.getCustomersCouponsByPrice(customerId, price);
		}
	}

	public List<Coupon> getCustomersCoupons(long customerId) throws MyException {
		if (InputChecker.isValidId(customerId)) {
			return couponDAO.getCouponsByCustomer(customerId);
		} else {
			throw new MyException(ErrorType.INVALID_ID, "customer ID is invalid");
		}
	}

	public List<Coupon> getCustomersCouponsByCategory(CategoriesEnum category, long customerId) throws MyException {
		if (!InputChecker.isValidId(customerId)) {
			throw new MyException(ErrorType.INVALID_ID, "customer ID is invalid");
		} else if (!InputChecker.isValidCategory(category)) {
			throw new MyException(ErrorType.INVALID_ID, "category is invalid");
		} else {
			return couponDAO.getCustomersCouponsByCategory(category, customerId);
		}
	}

	public void deleteExpCoupons() throws MyException, SQLException {
		PurchaseDAO purchaseDAO=new PurchaseDAO();
		couponDAO.deleteExpiredCoupons();
		purchaseDAO.deleteExpiredCoupons();
	}

	private boolean isCouponValidToAdd(Coupon coupon) throws MyException {
		if (coupon == null) {
			throw new MyException(ErrorType.GENERAL_ERROR, "coupon is null");
		} else if (coupon.getStartDate().after(coupon.getEndDate()) || coupon.getEndDate().before(new Date())) {
			throw new MyException(ErrorType.INVALID_DATES, "coupon dates are invalid");
		} else if (coupon.getAmount() < 1) {
			throw new MyException(ErrorType.COUPON_IS_OUT_OF_ORDER, "coupon is out of order");
		} else if (coupon.getPrice() < 0) {
			throw new MyException(ErrorType.INVALID_PRICE, "coupon price is invalid");
		} else if (isCouponExsist(coupon.getTitle(), coupon.getCompanyId())) {
			throw new MyException(ErrorType.GENERAL_ERROR, "coupon exsits on the data base");
		}else if(!InputChecker.isValidUrl(coupon.getImage())) {
			throw new MyException(ErrorType.INVALID_URL, "Error. Staus: "+ErrorType.INVALID_URL.getInternalErrorCode()+"\nMessege: "+ErrorType.INVALID_URL.getInternalMessage());
		}
		return true;
	
	}

	private boolean isCouponExsist(String title, long companyId) throws MyException {
		return couponDAO.isCouponExist(title, companyId);
	}
}
