package com.weatherapi.weatherforecast.location;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.weatherapi.weatherforecast.common.Location;
import com.weatherapi.weatherforecast.controller.LocationApiController;
import com.weatherapi.weatherforecast.service.ILocationService;

@WebMvcTest(LocationApiController.class)
public class LocationApiControllerTest {
	
	private static final String END_POINT_PATH = "/v1/locations";
	
	@Autowired MockMvc mockMvc;
	@Autowired ObjectMapper objectMapper;
	@MockBean ILocationService locationService;
	
	@Test
	public void testAddShouldReturn400BadRequest() throws Exception {
		Location location = new Location();
		String bodyContent = objectMapper.writeValueAsString(location); // chuyển đối tượng `location` thành một chuỗi json
		
		mockMvc.perform(post(END_POINT_PATH).contentType("application/json").content(bodyContent))
		       .andExpect(status().isBadRequest())
		       .andDo(print());
		// thực hiện một request HTTP POST tới đường dẫn `END_POINT_PATH` với các thông tin như trên
	}
	
	
	@Test
	public void testAddShouldReturn201Created() throws Exception {
		Location location = new Location();
        location.setCode("NYC_USA");
        location.setCityName("New York City");
        location.setRegionName("New York");
        location.setCountryCode("US");
        location.setCountryName("United States of America");
        location.setEnabled(true);
        
        // Đoạn code này sử dụng thư viện Mockito để tạo ra một đối tượng giả lập và trả về vị trí đó
	    Mockito.when(locationService.addLocation(location)).thenReturn(location);
		
	    String bodyContent = objectMapper.writeValueAsString(location); // chuyển đối tượng `location` thành một chuỗi json
		
	    mockMvc.perform(post(END_POINT_PATH).contentType("application/json").content(bodyContent))
	       .andExpect(status().isCreated())
	       .andExpect(content().contentType("application/json"))
	       .andDo(print());
	}
	
	@Test
	public void testListShouldReturn204NoContent() throws Exception {
		
		Mockito.when(locationService.listLocation()).thenReturn(Collections.EMPTY_LIST);
		
		mockMvc.perform(get(END_POINT_PATH))
		      .andExpect(status().isNoContent())
		      .andDo(print());
	}
	
	
	@Test
	public void testListShouldReturn200OK() throws Exception {
		
		List<Location> result = new ArrayList<>();
		
        Location location1 = new Location();
        location1.setCode("NYC_USA");
        location1.setCityName("New York City");
        location1.setRegionName("New York");
        location1.setCountryCode("US");
        location1.setCountryName("United States of America");
        location1.setEnabled(true);
        result.add(location1);
        
        Location location2 = new Location();
        location2.setCode("Test code");
        location2.setCityName("Test city name");
        location2.setRegionName("Test region");
        location2.setCountryCode("Test country code");
        location2.setCountryName("Test country name");
        location2.setEnabled(true);
        result.add(location2);

		Mockito.when(locationService.listLocation()).thenReturn(result);
		
		mockMvc.perform(get(END_POINT_PATH))
		      .andExpect(status().isOk())
		      .andDo(print());
	}

}
