package com.born.fcs.crm.integration.bpm.service;

import java.net.SocketTimeoutException;

public interface CallExternalInterface<T> {
	public T call() throws SocketTimeoutException;
}
