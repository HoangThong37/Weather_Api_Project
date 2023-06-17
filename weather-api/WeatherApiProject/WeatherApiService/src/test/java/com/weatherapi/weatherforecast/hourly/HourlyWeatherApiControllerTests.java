package com.weatherapi.weatherforecast.hourly;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.weatherapi.weatherforecast.common.HourlyWeather;
import com.weatherapi.weatherforecast.common.Location;
import com.weatherapi.weatherforecast.controller.HourlyWeatherApiController;
import com.weatherapi.weatherforecast.dto.HourlyWeatherDTO;
import com.weatherapi.weatherforecast.exception.GeoLocationException;
import com.weatherapi.weatherforecast.exception.LocationNotFoundException;
import com.weatherapi.weatherforecast.service.IGeoLocationService;
import com.weatherapi.weatherforecast.service.IHourlyWeatherService;

@WebMvcTest(HourlyWeatherApiController.class)
public class HourlyWeatherApiControllerTests {

	private static final String END_POINT_PATH = "/v1/hourly";

	@Autowired MockMvc mockMvc;
	
	@Autowired ObjectMapper objectMapper;
	
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
		// 400 : máy chủ ko hiểu request từ ng dùng do lỗi trong việc truyền dữ liệu.
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
	
	
	// testGetByCodeShouldReturn400BadRequest();
	// testGetByCodeShouldReturn404NotFound();
	// testGetByCodeShouldReturn204NoContent();
	// testGetByCodeShouldReturn200OK();
	
	@Test
	public void testGetByCodeShouldReturn400BadRequest() throws Exception {
		String locationCode = "NYC_USA";
		String url = END_POINT_PATH + "/" + locationCode;
		mockMvc.perform(get(url))
		       .andExpect(status().isBadRequest())
		       .andDo(print());
	}
	
	
	// testUpdateShouldReturn400BadRequestBecauseNoData()
	// testUpdateShouldReturn400BadRequestBecauseInvalidData()
	// testUpdateShouldReturn404NotFound()
	// testUpdateShouldReturn400BadRequestBecauseNoData()
	
	@Test
	public void testUpdateShouldReturn400BadRequestBecauseNoData() throws Exception {
		String locationCode = "NYC_USA";
		String url = END_POINT_PATH + "/" + locationCode;
		
		List<HourlyWeatherDTO> hourlyWeatherDTOs = Collections.EMPTY_LIST;
		
		String bodyContent = objectMapper.writeValueAsString(hourlyWeatherDTOs);
		mockMvc.perform(put(url).contentType(MediaType.APPLICATION_JSON)
			   .content(bodyContent))
		       .andExpect(status().isBadRequest())
		       .andExpect(jsonPath("$.errorDetails[0]", is("Hourly forecast data cannot be empty")))
		       .andDo(print());
	}
	
	@Test
	public void testUpdateShouldReturn400BadRequestBecauseInvalidData() throws Exception {
		
		String locationCode = "NYC_USA";
		String url = END_POINT_PATH + "/" + locationCode;
		
		HourlyWeatherDTO hourly1 = new HourlyWeatherDTO()
		        .hourOfDay(10)
		        .temperature(133)
		        .precipitation(70)
		        .status("Cloudy");
		 
		HourlyWeatherDTO hourly2 = new HourlyWeatherDTO()
		        .hourOfDay(11)
		        .temperature(15)
		        .precipitation(60)
		        .status("Sunny");
		
		List<HourlyWeatherDTO> result = List.of(hourly1, hourly2);
		
		String bodyContent = objectMapper.writeValueAsString(result);
		
		mockMvc.perform(put(url).contentType(MediaType.APPLICATION_JSON)
			   .content(bodyContent))
		       .andExpect(status().isBadRequest())
		       .andExpect(jsonPath("$.errors[0]", containsString("Temperator must be in the range")))
		       .andDo(print());
	}
	
	
	@Test
	public void testUpdateShouldReturn404NotFound() throws Exception {
		
		String locationCode = "NYC_USA";
		String url = END_POINT_PATH + "/" + locationCode;
		
		HourlyWeatherDTO hourly1 = new HourlyWeatherDTO()
		        .hourOfDay(10)
		        .temperature(133)
		        .precipitation(70)
		        .status("Cloudy");
		 
		List<HourlyWeatherDTO> result = List.of(hourly1);
		
		String bodyContent = objectMapper.writeValueAsString(result);
		
		when(hourlyWeatherService.updateByLocationCode(Mockito.eq(locationCode), Mockito.anyList()))
		                         .thenThrow(LocationNotFoundException.class);
		
		mockMvc.perform(put(url).contentType(MediaType.APPLICATION_JSON)
			   .content(bodyContent))
		       .andExpect(status().isNotFound())
		       .andDo(print());
	}
	
	// 
	@Test
	public void testUpdateShouldReturn200OK() throws Exception {
		
		String locationCode = "NYC_USA";
		String url = END_POINT_PATH + "/" + locationCode;
		
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
		
		
		HourlyWeatherDTO hourly1 = new HourlyWeatherDTO()
		        .hourOfDay(10)
		        .temperature(33)
		        .precipitation(70)
		        .status("Cloudy");
		
		HourlyWeatherDTO hourly2 = new HourlyWeatherDTO()
		        .hourOfDay(11)
		        .temperature(15)
		        .precipitation(60)
		        .status("Sunny");
		 
		List<HourlyWeatherDTO> result = List.of(hourly1, hourly2);
		var hourlyWeather = List.of(hourlyWeather1, hourlyWeather2);
		 
		String bodyContent = objectMapper.writeValueAsString(result);
		
		when(hourlyWeatherService.updateByLocationCode(Mockito.eq(locationCode), Mockito.anyList()))
		                         .thenReturn(hourlyWeather);
		
		mockMvc.perform(put(url).contentType(MediaType.APPLICATION_JSON)
			   .content(bodyContent))
		       .andExpect(status().isOk())
		       .andExpect(jsonPath("$.location", is(location.toString())))
		       .andDo(print());
	}
}