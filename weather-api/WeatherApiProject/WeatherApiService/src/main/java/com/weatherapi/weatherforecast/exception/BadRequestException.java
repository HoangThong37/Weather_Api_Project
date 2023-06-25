package com.weatherapi.weatherforecast.exception;

public class BadRequestException extends Exception {

	public BadRequestException(String message, Throwable cause) {
		super(message, cause);
	}

	public BadRequestException(String message) {
		super(message);
	}
}
