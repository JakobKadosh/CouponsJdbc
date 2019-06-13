package com.yakov.coupons.api;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yakov.coupons.beans.Coupon;
import com.yakov.coupons.beans.Purchase;
import com.yakov.coupons.exceptions.MyException;
import com.yakov.coupons.logic.PurchaseController;

@RestController
@RequestMapping("/purchases")
public class PurchasesApi {
	@Autowired
	private PurchaseController purchaseController;
	
	@PostMapping
	public void addPurchase(@RequestBody Purchase purchase)throws MyException{
		purchaseController.purchaseCoupon(purchase.getCustomer_id(),purchase.getCoupon_id(),purchase.getAmount());
	}
	
	@GetMapping("/{purchaseId}")
	public Purchase getPurchaseById(@PathVariable("purchaseId")long id)throws MyException{
		return purchaseController.getPurchaseById(id);
	}
	
	@GetMapping("/allPurchasedCoupons")
	public List<Coupon> getAllPurchasedCoupons() throws MyException{
		return purchaseController.getAllPurchasedCoupons();
	}
	
	
	@DeleteMapping("/{couponId}")
	public void deletePurchaseByCouponId(@PathVariable("couponId")long id)throws MyException{
		purchaseController.deletePurchaseByCouponID(id);
	}
	
	@DeleteMapping("/{customerId}")
	public void deleteCustomerPurchases(@PathVariable("customerId")long id)throws MyException{
		purchaseController.deleteCustomerPurchases(id);
	}
	
	
}
