package com.weatherapi.weatherforecast.service;

import java.util.List;

import com.weatherapi.weatherforecast.common.Location;
import com.weatherapi.weatherforecast.dto.LocationDTO;
import com.weatherapi.weatherforecast.exception.LocationNotFoundException;

public interface ILocationService { 
	
	Location addLocation(Location location);
	
	List<Location> listLocation();
	
	Location get(String code);
	
	Location updateLocation(Location location);
	
	void deletedLocation(String code);

}
