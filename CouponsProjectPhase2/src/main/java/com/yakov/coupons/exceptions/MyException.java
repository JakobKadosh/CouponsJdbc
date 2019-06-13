package com.yakov.coupons.exceptions;


import com.yakov.coupons.enums.ErrorType;


public class MyException extends RuntimeException{
	/**
	 * this is an exception that will be thrown when trying to add a too-short
	 * String to the data base
	 */
	// exception variables
	private static final long serialVersionUID = 1L;
	private String messege;
	private ErrorType errorType;
	private Exception innerException;
	

	

	//getters & setters
	

	
	public Exception getInnerException() {
		return innerException;
	}

	public void setInnerException(Exception innerException) {
		this.innerException = innerException;
	}

	public ErrorType getErrorType() {
		return errorType;
	}

	public void setErrorType(ErrorType errorType) {
		this.errorType = errorType;
	}

	public String getMessege() {
		return messege;
	}

	public void setMessege(String messege) {
		this.messege = messege;
	}

	// constructors --------------------------------- 
	public MyException( ErrorType errorType,String messege) {
		super();
		this.messege = messege;
		this.errorType = errorType;
	}

	public MyException( ErrorType errorType, Exception innerException, String messege) {
		super();
		this.messege = messege;
		this.errorType = errorType;
		this.innerException = innerException;
	}

}
