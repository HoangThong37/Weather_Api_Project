package com.weatherapi.weatherforecast.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.weatherapi.weatherforecast.common.Location;
import com.weatherapi.weatherforecast.dto.LocationDTO;
import com.weatherapi.weatherforecast.exception.LocationNotFoundException;
import com.weatherapi.weatherforecast.repository.LocationRepository;
import com.weatherapi.weatherforecast.service.ILocationService;

@Service
@Transactional
public class LocationServiceImpl implements ILocationService {

	@Autowired
	private LocationRepository repository;

	@Override
	public Location addLocation(Location location) {
		return repository.save(location);
	}

	@Override
	public List<Location> listLocation() {
		return repository.findUntrashed();
	}

	@Override
	public Location get(String code) {
		Location location = repository.findByCode(code);
		
		if (location == null) {
			throw new LocationNotFoundException(code);
		}
		return location;
	}


	@Override
	@Transactional
	public Location updateLocation(Location locationRequest) {
		
		Location locationInDB = repository.findByCode(locationRequest.getCode());
		
		if (locationInDB == null) {
           throw new LocationNotFoundException(locationRequest.getCode());
		}
		
		locationInDB.setCode(locationRequest.getCode());
		locationInDB.setCityName(locationRequest.getCityName());
		locationInDB.setCountryCode(locationRequest.getCountryCode());
		locationInDB.setCountryName(locationRequest.getCountryName());
		locationInDB.setRegionName(locationRequest.getRegionName());
		locationInDB.setEnabled(locationRequest.isEnabled());
	
		return repository.save(locationInDB);
	}

	
	@Override
	public void deletedLocation(String code) {
		try {
			if (!repository.existsById(code)) {
				throw new LocationNotFoundException(code);
			}
			repository.trashByCode(code);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error delete location service");
		}
	}
}
