package com.weatherapi.weatherforecast.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.weatherapi.weatherforecast.common.Location;
import com.weatherapi.weatherforecast.repository.LocationRepository;
import com.weatherapi.weatherforecast.service.ILocationService;

@Service
public class LocationServiceImpl implements ILocationService {
	
	@Autowired
	private LocationRepository repository;

	@Override
	public Location addLocation(Location location) {
		
		return repository.save(location);
	}

}
