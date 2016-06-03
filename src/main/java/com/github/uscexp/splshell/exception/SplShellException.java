/*
 * Copyright (C) 2014 - 2016 by haui - all rights reserved
 */
package com.github.uscexp.splshell.exception;

/**
 * @author haui
 *
 */
public class SplShellException extends Exception {

	private static final long serialVersionUID = -3620437155295087994L;

	public SplShellException(String message) {
		super(message);
	}

	public SplShellException(String message, Throwable cause) {
		super(message, cause);
	}
}
