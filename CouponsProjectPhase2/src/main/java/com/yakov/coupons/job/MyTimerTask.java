package com.yakov.coupons.job;

import java.util.TimerTask;

import com.yakov.coupons.logic.CouponController;
import com.yakov.coupons.logic.PurchaseController;

public class MyTimerTask extends TimerTask {
	CouponController couponController=new CouponController();
	PurchaseController purchaseController=new PurchaseController();
	@Override
	public void run() {
		try {
			couponController.deleteExpCoupons();
		} catch (Exception e) {
			// Temporarily swallowing exception. 
			e.printStackTrace();
		}
	}
}
