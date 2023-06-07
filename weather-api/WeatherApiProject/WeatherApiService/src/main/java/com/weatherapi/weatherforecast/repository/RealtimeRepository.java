package com.weatherapi.weatherforecast.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.weatherapi.weatherforecast.common.RealtimeWeather;

public interface RealtimeRepository extends CrudRepository<RealtimeWeather, String> {
	
	@Query("SELECT r FROM RealtimeWeather r WHERE r.location.countryCode = ?1 AND r.location.cityName = ?2")
	public RealtimeWeather findByCountryCodeAndCity(String countryCode, String city);
	

}
