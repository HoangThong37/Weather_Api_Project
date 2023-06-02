package com.weatherapi.weatherforecast.service;

import java.util.List;

import com.weatherapi.weatherforecast.common.Location;

public interface ILocationService { 
	
	Location addLocation(Location location);
	
	List<Location> listLocation();
	
	Location get(String code);

}
