package com.weatherapi.weatherforecast.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.weatherapi.weatherforecast.common.HourlyWeather;
import com.weatherapi.weatherforecast.common.Location;
import com.weatherapi.weatherforecast.exception.LocationNotFoundException;
import com.weatherapi.weatherforecast.repository.HourlyWeatherRepository;
import com.weatherapi.weatherforecast.repository.LocationRepository;
import com.weatherapi.weatherforecast.service.IHourlyWeatherService;

@Service
@Transactional
public class HourlyWeatherServiceImpl implements IHourlyWeatherService {

	@Autowired
	private HourlyWeatherRepository hourlyWeatherRepository;

	@Autowired
	private LocationRepository locationRepository;

	@Override
	public List<HourlyWeather> getByLocation(Location location, int currentHour) throws LocationNotFoundException {
		
		String countryCode = location.getCountryCode();
		String cityName = location.getCityName();
		
		Location locationInDB = locationRepository.findByCountryCodeAndCityName(countryCode, cityName);
		
		if (locationInDB == null) {
			throw new LocationNotFoundException("No Location found with the given country code and city name");
		}
		
		return hourlyWeatherRepository.findByLocationCode(locationInDB.getCode(), currentHour);
	}
	
	
}
