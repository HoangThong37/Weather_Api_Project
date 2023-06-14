package com.weatherapi.weatherforecast.service;

import java.util.List;

import com.weatherapi.weatherforecast.common.HourlyWeather;
import com.weatherapi.weatherforecast.common.Location;
import com.weatherapi.weatherforecast.exception.LocationNotFoundException;

public interface IHourlyWeatherService { 
	
	 // list location
     List<HourlyWeather> getByLocation(Location location, int currentHour) throws LocationNotFoundException;
     
     List<HourlyWeather> getByLocationCode(String locationCode, int currentHour) throws LocationNotFoundException;

}
