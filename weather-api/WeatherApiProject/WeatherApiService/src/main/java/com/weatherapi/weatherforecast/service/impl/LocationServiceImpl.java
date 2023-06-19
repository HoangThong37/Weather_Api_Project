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
		try {
			if (code != null) {
				Location location = repository.findByCode(code);
				return location;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// update
	@Override
	@Transactional
	public Location updateLocation(Location locationRequest) throws LocationNotFoundException {
		
		Location locationInDB = repository.findByCode(locationRequest.getCode());
		
		if (locationInDB == null) {
           throw new LocationNotFoundException("No location found with the given code" + locationRequest.getCode());
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
	public void deletedLocation(String code) throws LocationNotFoundException {
		try {
			if (!repository.existsById(code)) {
				throw new LocationNotFoundException("No Location not found with the given code : " + code);
			}
			repository.trashByCode(code);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error delete location service");
		}
	}
}
