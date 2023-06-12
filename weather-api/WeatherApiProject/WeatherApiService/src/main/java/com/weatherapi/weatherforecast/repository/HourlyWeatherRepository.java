package com.weatherapi.weatherforecast.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.weatherapi.weatherforecast.common.HourlyWeather;
import com.weatherapi.weatherforecast.common.HourlyWeatherId;

public interface HourlyWeatherRepository extends CrudRepository<HourlyWeather, HourlyWeatherId> {
	
	@Query("SELECT h FROM HourlyWeather h WHERE h.id.location.code  = ?1 AND h.id.hourOfDay > ?2 AND h.id.location.trashed = false")
	List<HourlyWeather> findByLocationCode(String locationCode, int currentHour);
	
}
