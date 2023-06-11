package com.weatherapi.weatherforecast.repository;

import org.springframework.data.repository.CrudRepository;

import com.weatherapi.weatherforecast.common.HourlyWeather;
import com.weatherapi.weatherforecast.common.HourlyWeatherId;

public interface HourlyWeatherRepository extends CrudRepository<HourlyWeather, HourlyWeatherId> {
	
}
