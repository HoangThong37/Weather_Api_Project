package com.weatherapi.weatherforecast.realtime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.weatherapi.weatherforecast.common.Location;
import com.weatherapi.weatherforecast.common.RealtimeWeather;
import com.weatherapi.weatherforecast.controller.RealtimeWeatherApiController;
import com.weatherapi.weatherforecast.exception.GeoLocationException;
import com.weatherapi.weatherforecast.exception.LocationNotFoundException;
import com.weatherapi.weatherforecast.service.IGeoLocationService;
import com.weatherapi.weatherforecast.service.IRealtimeService;

@WebMvcTest(RealtimeWeatherApiController.class)
public class RealtimeWeatherApiControllerTest {

	private static final String END_POINT_PATH = "/v1/realtime";

	@Autowired MockMvc mockMvc;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@MockBean
	IRealtimeService realtimeService;
	
	@MockBean
	IGeoLocationService geoLocationService;

	@Test
	public void testGetShouldReturnStatus400BadRequest() throws Exception { // là một lỗi HTTP status code, chỉ ra rằng máy chủ không thể hiểu hoặc xử lý yêu cầu của trình duyệt
		// `Mockito.anyString()` -> phương thức này có thể nhận bất kỳ chuỗi String nào làm đối số
		Mockito.when(geoLocationService.getLocation(Mockito.anyString())).thenThrow(GeoLocationException.class);
		
		mockMvc.perform(get(END_POINT_PATH))
		       .andExpect(status().isBadRequest())
		       .andDo(print());
	}
	
	
	@Test
	public void testGetShouldReturnStatus404NotFound() throws Exception { // server không tìm thấy tài nguyên được yêu cầu
		Location location = new Location();
		
		Mockito.when(geoLocationService.getLocation(Mockito.anyString())).thenReturn(location);
		Mockito.when(realtimeService.getByLocation(location)).thenThrow(LocationNotFoundException.class);
		
		mockMvc.perform(get(END_POINT_PATH))
		       .andExpect(status().isNotFound())
		       .andDo(print());
	}
	
	
	@Test
	public void testGetShouldReturnStatus200Success() throws Exception { // server không tìm thấy tài nguyên được yêu cầu
		Location location = new Location();
		location.setCode("USA");
		location.setCityName("San Franciso");
		location.setRegionName("Califonia");
		location.setCountryName("United states of America");
		location.setCountryCode("US");
		
		RealtimeWeather realtimeWeather = new RealtimeWeather();
		realtimeWeather.setHumidity(32);
		realtimeWeather.setTemperature(12);
		realtimeWeather.setLastUpdated(new Date());
		realtimeWeather.setPrecipitation(88);
		realtimeWeather.setStatus("Cloudy");
		realtimeWeather.setWindSpeed(5);
		
		realtimeWeather.setLocation(location);
		location.setRealtimeWeather(realtimeWeather);
		
		
		Mockito.when(geoLocationService.getLocation(Mockito.anyString())).thenReturn(location);
		Mockito.when(realtimeService.getByLocation(location)).thenReturn(realtimeWeather);
		
		mockMvc.perform(get(END_POINT_PATH))
		       .andExpect(status().isOk())
		       .andExpect(content().contentType("application/json"))
		       .andDo(print());
	}
}