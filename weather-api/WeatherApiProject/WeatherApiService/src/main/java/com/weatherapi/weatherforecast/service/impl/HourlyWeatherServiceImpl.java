package com.weatherapi.weatherforecast.service.impl;

import java.util.ArrayList;
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
	public List<HourlyWeather> updateByLocationCode(String locationCode, List<HourlyWeather> hourlyWeatherInRequest) throws LocationNotFoundException {
        Location location = locationRepository.findByCode(locationCode);

        if (location == null) {
            throw new LocationNotFoundException(locationCode);
        }

        for (HourlyWeather item : hourlyWeatherInRequest) {
            item.getId().setLocation(location);
        }

        List<HourlyWeather> hourlyWeatherInDB = location.getListHourlyWeathers();
        List<HourlyWeather> hourlyWeatherToBeRemoved = new ArrayList<>();

        for (HourlyWeather item : hourlyWeatherInDB) {
            if (!hourlyWeatherInRequest.contains(item)) {
                hourlyWeatherToBeRemoved.add(item.getShallowCopy());
            }
        }

        for (HourlyWeather item : hourlyWeatherToBeRemoved) {
            hourlyWeatherInDB.remove(item);
        }

        return (List<HourlyWeather>) hourlyWeatherRepository.saveAll(hourlyWeatherInRequest);
    }
	
}
