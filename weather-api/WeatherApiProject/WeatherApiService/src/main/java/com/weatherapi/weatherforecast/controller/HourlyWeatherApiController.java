package com.weatherapi.weatherforecast.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.weatherapi.weatherforecast.common.HourlyWeather;
import com.weatherapi.weatherforecast.common.Location;
import com.weatherapi.weatherforecast.exception.GeoLocationException;
import com.weatherapi.weatherforecast.exception.LocationNotFoundException;
import com.weatherapi.weatherforecast.service.IGeoLocationService;
import com.weatherapi.weatherforecast.service.IHourlyWeatherService;
import com.weatherapi.weatherforecast.utils.CommonUtility;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/v1/hourly")
public class HourlyWeatherApiController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(HourlyWeatherApiController.class);

	@Autowired
	private IGeoLocationService geoLocationService;

	@Autowired
	private IHourlyWeatherService hourlyWeatherService;
	
	@GetMapping
	public ResponseEntity<?> listHourlyForecastByIPAddress(HttpServletRequest request) throws Exception {
		// get Location
		String ipAddress = CommonUtility.getIPAddress(request);
		int currentHour = Integer.parseInt(request.getHeader("X-Current-Hour"));
				
		try {
			Location location = geoLocationService.getLocation(ipAddress);
			List<HourlyWeather> listHourlyWeathers = hourlyWeatherService.getByLocation(location, currentHour);
			if (listHourlyWeathers.isEmpty()) {
				return ResponseEntity.noContent().build();
			}
			return ResponseEntity.ok(listHourlyWeathers);
			
		} catch (GeoLocationException e) {
			LOGGER.error(e.getMessage(), e);
			return ResponseEntity.badRequest().build();
			
		} catch (LocationNotFoundException e) {
			LOGGER.error(e.getMessage(), e);
			return ResponseEntity.notFound().build();
		}
	}
}
