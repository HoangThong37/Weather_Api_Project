package com.weatherapi.weatherforecast.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.weatherapi.weatherforecast.common.Location;
import com.weatherapi.weatherforecast.common.RealtimeWeather;
import com.weatherapi.weatherforecast.converter.RealtimeWeatherConverter;
import com.weatherapi.weatherforecast.exception.GeoLocationException;
import com.weatherapi.weatherforecast.exception.LocationNotFoundException;
import com.weatherapi.weatherforecast.service.IGeoLocationService;
import com.weatherapi.weatherforecast.service.IRealtimeService;
import com.weatherapi.weatherforecast.utils.CommonUtility;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1/realtime")
public class RealtimeWeatherApiController {

	private static final Logger LOGGER = LoggerFactory.getLogger(RealtimeWeatherApiController.class);

	@Autowired
	private IRealtimeService realtimeService;

	@Autowired
	private IGeoLocationService locationService;

	@Autowired
	private RealtimeWeatherConverter realtimeWeatherConverter;

	@GetMapping
	public ResponseEntity<?> getRealtimeByIPAddress(HttpServletRequest request) throws Exception {
		// get Location
		String ipAddress = CommonUtility.getIPAddress(request);
		try {
			Location location = locationService.getLocation(ipAddress); //
			RealtimeWeather realtimeWeather = realtimeService.getByLocation(location);

			return ResponseEntity.ok(realtimeWeatherConverter.convertToDTO(realtimeWeather));

		} catch (GeoLocationException e) {
			LOGGER.error(e.getMessage(), e);
			return ResponseEntity.badRequest().build();
		}
	}

	@GetMapping("/{locationCode}")
	public ResponseEntity<?> getRealtimeWeatherByLocationCode(@PathVariable(name = "locationCode") String locationCode)
			throws Exception {

		RealtimeWeather realtime = realtimeService.getByLocationCode(locationCode);

		return ResponseEntity.ok(realtimeWeatherConverter.convertToDTO(realtime));
	}

	@PutMapping("/{locationCode}")
	public ResponseEntity<?> updateRealtimeWeather(@PathVariable(name = "locationCode") String locationCode,
			@RequestBody @Valid RealtimeWeather realtimeWeather) {
		realtimeWeather.setLocationCode(locationCode);
		RealtimeWeather updateRealtimeWeather = realtimeService.update(locationCode, realtimeWeather);

		return ResponseEntity.ok(realtimeWeatherConverter.convertToDTO(updateRealtimeWeather));

	}
}
