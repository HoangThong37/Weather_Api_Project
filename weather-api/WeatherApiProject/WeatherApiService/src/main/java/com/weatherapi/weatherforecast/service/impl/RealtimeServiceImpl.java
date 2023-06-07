package com.weatherapi.weatherforecast.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.weatherapi.weatherforecast.common.Location;
import com.weatherapi.weatherforecast.common.RealtimeWeather;
import com.weatherapi.weatherforecast.exception.LocationNotFoundException;
import com.weatherapi.weatherforecast.repository.RealtimeRepository;
import com.weatherapi.weatherforecast.service.IRealtimeService;

@Service
@Transactional
public class RealtimeServiceImpl implements IRealtimeService {
	
	@Autowired
	private RealtimeRepository repository;

	@Override 
	public RealtimeWeather getByLocation(Location location) throws LocationNotFoundException {
		String countryCode = location.getCountryCode();
		String cityName = location.getCityName();
		
		RealtimeWeather realtimeWeather = repository.findByCountryCodeAndCity(countryCode, cityName);
		if (realtimeWeather == null) {
			throw new LocationNotFoundException("No location found with the given country code and city name");
		}
		return realtimeWeather;
	}
}
