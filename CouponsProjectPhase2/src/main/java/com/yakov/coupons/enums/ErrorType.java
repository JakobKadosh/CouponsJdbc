package com.yakov.coupons.enums;


public enum ErrorType {
GENERAL_ERROR(600, "General error",true),
NAME_IS_ALREADY_EXISTS(601, "The name you chose is already exist. Please pick another name",false),
INVALID_ID(602, "The ID you've enter is invalid",false),
INVALID_AMOUNT(603,"The amount you've entered is invalid",false),
INVALID_PRICE(604,"The price you've entered is invalid",false),
INVALID_EMAIL(605,"The email you've entered is invalid. Please try again.",false),
INVALID_PASSWORD(606,"The password you've entered is invalid. Please try again.",false),
INVALID_DATES(607,"The dates you've entered is invalid. Please try again.",false),
INVALID_NAME(608,"Name must contain more then three ckarecters",false),
FIELD_IS_IRREPLACEABLE(609, "You can't change this field.",false),
NAME_IS_IRREPLACEABLE(610, "You can't change your name.",false),
COUPON_IS_OUT_OF_ORDER(611, "Coupon is out of order",false),
LOGIN_FAILED(612, "Login failed. credentials is incorrect, Please try again.",false),
COMPANY_DOES_NOT_EXSITS(613, "Company name does not apear to exsit in our data base",false),
INVALID_URL(614,"Image url is invalid. must be 200 chreckters or less",false);
	
	private ErrorType(int internalErrorCode, String internalMessage, boolean isCritical) {
	this.internalErrorCode = internalErrorCode;
	this.internalMessage = internalMessage;
	this.isCritical = isCritical;
}

	private int internalErrorCode;
	private String internalMessage;
	private boolean isCritical;
	
	public boolean isCritical() {
		return isCritical;
	}

	public void setCritical(boolean isCritical) {
		this.isCritical = isCritical;
	}

	public void setInternalErrorCode(int internalErrorCode) {
		this.internalErrorCode = internalErrorCode;
	}

	public void setInternalMessage(String internalMessage) {
		this.internalMessage = internalMessage;
	}

	private ErrorType(int internalErrorCode, String internalMessage) {
		this.internalErrorCode=internalErrorCode;
		this.internalMessage=internalMessage;
	}

	public int getInternalErrorCode() {
		return internalErrorCode;
	}

	public String getInternalMessage() {
		return internalMessage;
	}
	
} 
