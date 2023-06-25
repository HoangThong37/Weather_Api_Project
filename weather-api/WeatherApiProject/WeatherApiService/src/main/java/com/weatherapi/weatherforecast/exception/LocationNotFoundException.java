package com.weatherapi.weatherforecast.exception;

public class LocationNotFoundException extends RuntimeException {

	public LocationNotFoundException(String locationCode) {
		super("No location found with the given code" + locationCode);
	}
	
	// countryCode, cityName
	public LocationNotFoundException(String countryCode, String cityName) {
		super("No location found with the given country code and city name");
	}
}
