package com.yakov.coupons.job;

import java.util.Timer;
import java.util.TimerTask;

import com.yakov.coupons.enums.ErrorType;
import com.yakov.coupons.exceptions.MyException;

public class CouponExpritionDailyJob {

	public static void deleteExpCoupon() throws MyException {

		// Creating a task
		TimerTask timerTask = new MyTimerTask();

		// Creating a timer
		Timer timer = new Timer();

		// Tell the timer to run the task every 10 seconds, starting of now
		timer.scheduleAtFixedRate(timerTask, 0, 1000);

		System.out.println("TimerTask started");

		try {
			// 10 seconds delay before canceling the task
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			throw new MyException(ErrorType.GENERAL_ERROR, e, "faild to delete expired coupons");
		}

		
	}
}
