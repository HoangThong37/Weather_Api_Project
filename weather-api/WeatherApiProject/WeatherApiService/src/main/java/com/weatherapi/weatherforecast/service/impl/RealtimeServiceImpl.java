package com.weatherapi.weatherforecast.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.weatherapi.weatherforecast.common.Location;
import com.weatherapi.weatherforecast.common.RealtimeWeather;
import com.weatherapi.weatherforecast.exception.LocationNotFoundException;
import com.weatherapi.weatherforecast.repository.LocationRepository;
import com.weatherapi.weatherforecast.repository.RealtimeRepository;
import com.weatherapi.weatherforecast.service.IRealtimeService;

@Service
@Transactional
public class RealtimeServiceImpl implements IRealtimeService {

	@Autowired
	private RealtimeRepository repository;

	@Autowired
	private LocationRepository locationRepo;

	@Override
	public RealtimeWeather getByLocation(Location location) {
		String countryCode = location.getCountryCode();
		String cityName = location.getCityName();

		RealtimeWeather realtimeWeather = repository.findByCountryCodeAndCity(countryCode, cityName);
		if (realtimeWeather == null) {
			throw new LocationNotFoundException(countryCode, cityName);
		}
		return realtimeWeather;
	}

	@Override
	public RealtimeWeather getByLocationCode(String locationCode) {

		RealtimeWeather realtimeWeather = repository.findByLocationCode(locationCode);
		if (realtimeWeather == null) {
			throw new LocationNotFoundException(locationCode);
		}
		return realtimeWeather;
	}

	@Override
	public RealtimeWeather update(String locationCode, RealtimeWeather realtimeWeather)
			throws LocationNotFoundException {
		Location location = locationRepo.findByCode(locationCode);

		if (location == null) {
			throw new LocationNotFoundException("No location found with the given location code");
		}
		realtimeWeather.setLocation(location);
		realtimeWeather.setLastUpdated(new Date());

		if (location.getRealtimeWeather() == null) {
			location.setRealtimeWeather(realtimeWeather);
			Location locationUpdate = locationRepo.save(location);
			return locationUpdate.getRealtimeWeather();
		}
		return repository.save(realtimeWeather);
	}
}
