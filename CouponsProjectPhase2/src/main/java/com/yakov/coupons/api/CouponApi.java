package com.yakov.coupons.api;

import java.sql.SQLException;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yakov.coupons.beans.Coupon;
import com.yakov.coupons.enums.CategoriesEnum;
import com.yakov.coupons.exceptions.MyException;
import com.yakov.coupons.logic.CouponController;

@RestController
@RequestMapping("/coupons")
public class CouponApi {
	@Autowired
	private CouponController couponController;

	@PostMapping
	public void addCoupon(@RequestBody Coupon coupon) throws MyException {
		couponController.addCoupon(coupon);
	}

	@PutMapping
	public void updateCoupon(@RequestBody Coupon coupon) throws MyException {
		couponController.updateCoupon(coupon);
	}
	
	@GetMapping
	public List<Coupon> getAllCoupons() throws MyException{
		return couponController.getAllCoupons();
	}
	
	@DeleteMapping("/{couponId}")
	public void deleteCoupon(@PathVariable("couponId") long id) throws MyException {
		couponController.deleteCoupon(id);
	}

	@GetMapping("/{couponId}")
	public Coupon getCouponById(@PathVariable("couponId") long id) throws MyException {
		return couponController.getCouponById(id);
	}
	
	@GetMapping("/company")
	public List<Coupon> getCompanyCoupons(@RequestParam("companyId") long companyId) throws MyException {
		return couponController.getCompanysCoupons(companyId);
	}

	@GetMapping("/company/category")
	public List<Coupon> getCouponsByCategory(@RequestParam("companyId") long companyId,
			@RequestParam("category") String category) throws MyException, SQLException {
		return couponController.getCompanysCouponsByCategory(companyId, category);
	}

	@GetMapping("/company/price")
	public List<Coupon> getCompanysCouponsByPrice(@RequestParam("companyId") long companyId,
			@RequestParam("price") double price) throws MyException {
		return couponController.getCompanysCouponsByPrice(companyId, price);
	}

	@GetMapping("/customer")
	public List<Coupon> getCouponsByCustomer(@RequestParam("customerId") long customerId) throws MyException {
		return couponController.getCustomersCoupons(customerId);
	}
	
	@GetMapping("/customer/price")
	public List<Coupon> getCustomersCouponsByPrice(@RequestParam("customerId") long customerId,
			@RequestParam("price") double price) throws MyException {
		return couponController.getCustomersCouponsByPrice(customerId, price);
	}


	@GetMapping("/customer/category")
	public List<Coupon> getCustomersCouponsByCategory(@RequestParam("category") CategoriesEnum category,
			@RequestParam("customerId") long customerId) throws MyException {
		return couponController.getCustomersCouponsByCategory(category, customerId);
	}



}
