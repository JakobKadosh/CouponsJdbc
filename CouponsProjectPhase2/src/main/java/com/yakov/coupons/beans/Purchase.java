package com.yakov.coupons.beans;


public class Purchase {
	/**
	 * customer vs coupon bean file for the db table, props setters getters
	 * constructor and a toString method
	 */
	// ---------------------prop----------------------------------
	
	private long purchaseId, customerId, couponId;
	
	private int amount;

	// ---------------------constructor--------------------------------
	/**
	 * 
	 * @param customerId on this table of all the purchased coupons this prop
	 *                    indicates the coupon's customer's id
	 * @param couponId   indicates the coupon's id
	 * @throws Exception general exception
	 */

	public Purchase(long customerId, long couponId,int amount)  {
		setCoupon_id(couponId);
		setCustomer_id(customerId);
		setAmount(amount);
	}

	public Purchase() {
	}
	
	public Purchase(long purchaseId, long customerId, long couponId, int amount) {
		this(customerId,couponId,amount);
		setPurchase_id(purchaseId);
	}

	// ---------------------getters + setters-------------------------

	
	public long getPurchase_id() {
		return purchaseId;
	}

	public void setPurchase_id(long purchaseId) {
		this.purchaseId = purchaseId;
	}

	public int getAmount() {
		return amount;
	}

	public long getCustomer_id() {
		return customerId;
	}

	public void setCustomer_id(long customerId) {
		this.customerId = customerId;
	}

	public long getCoupon_id() {
		return couponId;
	}

	public void setCoupon_id(long couponId) {
		this.couponId = couponId;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	// ---------------------methods-------------------------
	@Override
	public String toString() {
		return "Customer ID: " + customerId + ",\tCoupon ID: " + couponId + ".";
	}

}
