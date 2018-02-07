package com.born.fcs.face.web.controller.fund.payment;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class DeptBalanceCheckLock {
	
	private final static DeptBalanceCheckLock lock = new DeptBalanceCheckLock();
	
	private final Semaphore semaphore = new Semaphore(1);
	
	private DeptBalanceCheckLock() {
	};
	
	public static DeptBalanceCheckLock getInstance() {
		return lock;
	}
	
	public boolean acquire() {
		boolean isAcquire = false;
		try {
			isAcquire = semaphore.tryAcquire(10L, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return isAcquire;
	}
	
	public void release() {
		semaphore.release();
	}
}
