package com.weatherapi.weatherforecast.hourly;

import static org.mockito.ArgumentMatchers.intThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.weatherapi.weatherforecast.common.HourlyWeather;
import com.weatherapi.weatherforecast.common.Location;
import com.weatherapi.weatherforecast.controller.HourlyWeatherApiController;
import com.weatherapi.weatherforecast.exception.GeoLocationException;
import com.weatherapi.weatherforecast.service.IGeoLocationService;
import com.weatherapi.weatherforecast.service.IHourlyWeatherService;

@WebMvcTest(HourlyWeatherApiController.class)

public class HourlyWeatherApiControllerTests {

	private static final String END_POINT_PATH = "/v1/hourly";

	@Autowired MockMvc mockMvc;
	
//	@Autowired
//	ObjectMapper objectMapper;
//	
	@MockBean
	IHourlyWeatherService hourlyWeatherService;
	
	@MockBean
	IGeoLocationService locationService;
	
	// testGetByIPShouldReturn400BadRequestBecauseNoHeaderXCurrentHour()
	// testGetByIPShouldReturn400BadRequestBecauseGeolocationException()
	// testGetByIPShouldReturn200OK
	// testGetByIPShouldReturn204NoContent. // nhận đc request nhưng ko có response

	@Test
	public void testGetByIPShouldReturn400BadRequestBecauseNoHeaderXCurrentHour() throws Exception { // là một lỗi HTTP status code, chỉ ra rằng máy chủ không thể hiểu hoặc xử lý yêu cầu của trình duyệt
		
		mockMvc.perform(get(END_POINT_PATH))
		       .andExpect(status().isBadRequest())
		       .andDo(print());
	}
	
	@Test
	public void testGetByIPShouldReturn400BadRequestBecauseGeolocationException() throws Exception { // là một lỗi HTTP status code, chỉ ra rằng máy chủ không thể hiểu hoặc xử lý yêu cầu của trình duyệt
		
		Mockito.when(locationService.getLocation(Mockito.anyString())).thenThrow(GeoLocationException.class);
		
		mockMvc.perform(get(END_POINT_PATH).header("X-Current-Hour", "9"))  
		       .andExpect(status().isBadRequest())
		       .andDo(print());
	}
	
	
	@Test
	public void testGetByIPShouldReturn204NoContent() throws Exception {
		 
		int currentHour = 9;
		Location location = new Location().code("VIETNAM");
		
		Mockito.when(locationService.getLocation(Mockito.anyString())).thenReturn(location);
		when(hourlyWeatherService.getByLocation(location, currentHour)).thenReturn(new ArrayList<>());
		
		mockMvc.perform(get(END_POINT_PATH).header("X-Current-Hour", String.valueOf(currentHour)))  
		       .andExpect(status().isNoContent())
		       .andDo(print());
	}
	
	@Test
	public void testGetByIPShouldReturn200OK() throws Exception {
		 
		int currentHour = 9;
		
		Location location = new Location();
		location.setCode("NYC_USA");
		location.setCityName("New York City");
		location.setRegionName("New York");
		location.setCountryCode("US");
		location.setCountryName("United States of America");
		
		HourlyWeather hourlyWeather1 = new HourlyWeather()
				.location(location)
		        .hourOfDay(10)
		        .temperature(13)
		        .precipitation(70)
		        .status("Cloudy");
		 
		HourlyWeather hourlyWeather2 = new HourlyWeather()
				.location(location)
		        .hourOfDay(11)
		        .temperature(15)
		        .precipitation(60)
		        .status("Sunny");
		
		Mockito.when(locationService.getLocation(Mockito.anyString())).thenReturn(location);
		when(hourlyWeatherService.getByLocation(location, currentHour)).thenReturn(List.of(hourlyWeather1, hourlyWeather2));
		
		mockMvc.perform(get(END_POINT_PATH).header("X-Current-Hour", String.valueOf(currentHour)))  
		       .andExpect(status().isOk())
		       .andDo(print());
	}

	
}