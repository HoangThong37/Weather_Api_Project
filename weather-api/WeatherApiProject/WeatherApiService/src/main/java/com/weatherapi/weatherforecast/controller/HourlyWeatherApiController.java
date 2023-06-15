package com.weatherapi.weatherforecast.controller;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.weatherapi.weatherforecast.common.HourlyWeather;
import com.weatherapi.weatherforecast.common.Location;
import com.weatherapi.weatherforecast.converter.HourlyWeatherConverter;
import com.weatherapi.weatherforecast.dto.HourlyWeatherDTO;
import com.weatherapi.weatherforecast.dto.HourlyWeatherListDTO;
import com.weatherapi.weatherforecast.exception.BadRequestException;
import com.weatherapi.weatherforecast.exception.GeoLocationException;
import com.weatherapi.weatherforecast.exception.LocationNotFoundException;
import com.weatherapi.weatherforecast.service.IGeoLocationService;
import com.weatherapi.weatherforecast.service.IHourlyWeatherService;
import com.weatherapi.weatherforecast.utils.CommonUtility;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/v1/hourly")
//@Validated
public class HourlyWeatherApiController {

	private static final Logger LOGGER = LoggerFactory.getLogger(HourlyWeatherApiController.class);


	@Autowired
	public IHourlyWeatherService hourlyWeatherService;

	@Autowired
	public IGeoLocationService geoLocationService;

	@Autowired
	public HourlyWeatherConverter hourlyWeatherConverter;

	@GetMapping
	public ResponseEntity<?> listHourlyForecastByIPAddress(HttpServletRequest request) throws Exception {
		// get Location
		String ipAddress = CommonUtility.getIPAddress(request);

		try {
			int currentHour = Integer.parseInt(request.getHeader("X-Current-Hour"));

			Location location = geoLocationService.getLocation(ipAddress);
			List<HourlyWeather> listHourlyWeathers = hourlyWeatherService.getByLocation(location, currentHour);
			if (listHourlyWeathers.isEmpty()) {
				return ResponseEntity.noContent().build();
			}
			return ResponseEntity.ok(listEntity2DTO(listHourlyWeathers));

		} catch (NumberFormatException | GeoLocationException e) {
			LOGGER.error(e.getMessage(), e);
			return ResponseEntity.badRequest().build();

		} catch (LocationNotFoundException e) {
			LOGGER.error(e.getMessage(), e);
			return ResponseEntity.notFound().build();
		}
	}

	public HourlyWeatherListDTO listEntity2DTO(List<HourlyWeather> hourlyForecast) throws Exception {
		Location location = hourlyForecast.get(0).getId().getLocation();

		HourlyWeatherListDTO listDTO = hourlyWeatherConverter.convertToDTO(hourlyForecast);
		listDTO.setLocation(location.toString());

		return listDTO;
	}

	@GetMapping("/{locationCode}")
	public ResponseEntity<?> listHourlyForecastByLocationCode(@PathVariable("locationCode") String locationCode,
			HttpServletRequest request) throws Exception {

		try {
			int currentHour = Integer.parseInt(request.getHeader("X-Current-Hour"));
			List<HourlyWeather> listHourlyWeathers = hourlyWeatherService.getByLocationCode(locationCode, currentHour);

			if (listHourlyWeathers.isEmpty()) {
				return ResponseEntity.noContent().build();
			}
			return ResponseEntity.ok(listEntity2DTO(listHourlyWeathers));

		} catch (NumberFormatException e) {
			return ResponseEntity.badRequest().build();
		} catch (LocationNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}

	// update hourly forecast
	@PutMapping("/{locationCode}")
	public ResponseEntity<?> updateHourlyForecast(@PathVariable("locationCode") String locationCode,
			@RequestBody List<HourlyWeatherDTO> listDTOs) throws BadRequestException {

		if (listDTOs.isEmpty()) {
			throw new BadRequestException("Hourly forecast data cannot be empty");
		}

		return ResponseEntity.accepted().build(); // mã trạng thái `202 Accepted` và không có nội dung thân của phản hồi
	}

	
	
	
	// test
//	private HourlyWeatherListDTO listEntityToDTO(List<HourlyWeather> hourlyForecast) {
//		Location location = hourlyForecast.get(0).getId().getLocation();
//
//		HourlyWeatherListDTO listDTO = new HourlyWeatherListDTO();
//		listDTO.setLocation(location.toString());
//
//		hourlyForecast.forEach(hourlyWeather -> {
//			HourlyWeatherDTO dto = modelMapper.map(hourlyWeather, HourlyWeatherDTO.class);
//			listDTO.addHourlyWeatherDTO(dto);
//		});
//
//		return listDTO;
//
//	}
//
//	private List<HourlyWeather> listDTO2ListEntity(List<HourlyWeatherDTO> listDTO) {
//		List<HourlyWeather> listEntity = new ArrayList<>();
//
//		listDTO.forEach(dto -> {
//			listEntity.add(modelMapper.map(dto, HourlyWeather.class));
//		});
//
//		return listEntity;
//	}

}
