package com.weatherapi.weatherforecast.repository;

import org.springframework.data.repository.CrudRepository;

import com.weatherapi.weatherforecast.common.RealtimeWeather;

public interface RealtimeRepository extends CrudRepository<RealtimeWeather, String> {
	

}
