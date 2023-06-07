package com.weatherapi.weatherforecast.service;

import com.weatherapi.weatherforecast.common.Location;

public interface IGeoLocationService {
	
	Location getLocation(String ipAddress) throws Exception;

}
