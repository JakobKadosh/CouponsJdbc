package com.yakov.coupons.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
@RestControllerAdvice
public class ExceptionsHandler implements ExceptionMapper<Throwable> {

	@ExceptionHandler(MyException.class)
	public Response toResponse(Throwable exception) {
		
		if (exception instanceof MyException) {
			MyException e = (MyException) exception;
			int internalErrorCode = e.getErrorType().getInternalErrorCode();
			String internalMessage = e.getMessage();
			String externalMessage =  e.getErrorType().getInternalMessage();
			ErrorBean errorBean = new ErrorBean(internalErrorCode, internalMessage, externalMessage);
			if(e.getErrorType().isCritical()) {
				e.printStackTrace();
			}
			return Response.status(internalErrorCode).entity(errorBean).build();
			
		} else if (exception instanceof Exception) {
			String internalMessage = exception.getMessage();
			ErrorBean errorBean = new ErrorBean(601, internalMessage, "General error");
			exception.printStackTrace();
			return Response.status(601).entity(errorBean).build();
		}
		exception.printStackTrace();
		return Response.status(500).entity(null).build();
	}
}
