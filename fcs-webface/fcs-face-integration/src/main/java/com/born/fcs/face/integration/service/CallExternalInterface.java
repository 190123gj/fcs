package com.born.fcs.face.integration.service;

import java.net.SocketTimeoutException;

public interface CallExternalInterface<T> {
	public T call() throws SocketTimeoutException;
}
