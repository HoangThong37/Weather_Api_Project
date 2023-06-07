package com.weatherapi.weatherforecast.service;

import com.weatherapi.weatherforecast.common.Location;
import com.weatherapi.weatherforecast.common.RealtimeWeather;
import com.weatherapi.weatherforecast.exception.LocationNotFoundException;

public interface IRealtimeService { 
	
    RealtimeWeather getByLocation(Location location) throws LocationNotFoundException;

}
