package com.weatherapi.weatherforecast.service.impl;

import java.util.Collections;
import java.util.List;

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

//	@Autowired
//	private HourlyWeatherRepository hourlyWeatherRepository;
//
//	@Autowired
//	private LocationRepository locationRepository;
	
    private final HourlyWeatherRepository hourlyWeatherRepository;
    private final LocationRepository locationRepository;
    
	public HourlyWeatherServiceImpl(HourlyWeatherRepository hourlyWeatherRepo, LocationRepository locationRepo) {
		super();
		this.hourlyWeatherRepository = hourlyWeatherRepo;
		this.locationRepository = locationRepo;
	}

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

	@Override
	public List<HourlyWeather> getByLocationCode(String locationCode, int currentHour) throws LocationNotFoundException {
          
        Location location = locationRepository.findByCode(locationCode); 
        if (location == null) {
        	throw new LocationNotFoundException("No Location found with the given location code");
		}
		return hourlyWeatherRepository.findByLocationCode(location.getCode(), currentHour);
	}

	// update location code 
	@Override
	public List<HourlyWeather> updateByLocationCode(String code, List<HourlyWeather> listHourlyWeathers) throws LocationNotFoundException {
		Location location = locationRepository.findByCode(code);
		
		if (location == null) {
			throw new LocationNotFoundException("No location found with the given code : " + code);
		}
		return (List<HourlyWeather>) hourlyWeatherRepository.saveAll(listHourlyWeathers);
	}
	
	
}
