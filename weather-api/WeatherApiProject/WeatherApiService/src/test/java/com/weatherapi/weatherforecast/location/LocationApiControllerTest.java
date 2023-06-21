package com.weatherapi.weatherforecast.location;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.weatherapi.weatherforecast.common.Location;
import com.weatherapi.weatherforecast.controller.LocationApiController;
import com.weatherapi.weatherforecast.dto.LocationDTO;
import com.weatherapi.weatherforecast.exception.LocationNotFoundException;
import com.weatherapi.weatherforecast.service.ILocationService;

@WebMvcTest(LocationApiController.class)
public class LocationApiControllerTest {

	 private static final String END_POINT_PATH = "/v1/locations";

	    @Autowired
	    MockMvc mockMvc;

	    @Autowired
	    ObjectMapper mapper;

	    @MockBean
	    ILocationService service;

	    @Test
	    public void testAddShouldReturn400BadRequest() throws Exception {

	        // given
	        LocationDTO locationDTO = new LocationDTO();

	        // when

	        String bodyContent = mapper.writeValueAsString(locationDTO);

	        // then
	        mockMvc.perform(post(END_POINT_PATH).contentType("application/json").content(bodyContent))
	                .andExpect(status().isBadRequest())
	                .andDo(print());
	    }

	    @Test
	    public void testAddShouldReturn201Created() throws Exception {

	        // given
	        Location location = new Location()
	                .code("NYC_USA")
	                .cityName("New York City")
	                .regionName("New York")
	                .countryCode("US")
	                .countryName("United States of America")
	                .enabled(true);
	             
	        LocationDTO locationDTO = new LocationDTO()
	                .code(location.getCode())
	                .cityName(location.getCityName())
	                .regionName(location.getRegionName())
	                .countryCode(location.getCountryCode())
	                .countryName(location.getCountryName())
	                .enabled(location.isEnabled());

	        // when
	        when(service.addLocation(location)).thenReturn(location);

	        String bodyContent = mapper.writeValueAsString(locationDTO);

	        // then
	        mockMvc.perform(post(END_POINT_PATH).contentType("application/json").content(bodyContent))
	                .andExpect(status().isCreated())
	                .andExpect(content().contentType("application/json"))
	                .andExpect(jsonPath("$.code", is("NYC_USA")))
	                .andExpect(jsonPath("$.city_name", is("New York City")))
	                .andExpect(header().string("Location", "/v1/locations/NYC_USA"))
	                .andDo(print());
	    }

	    @Test
	    public void testListShouldReturn204NoContent() throws Exception {

	        // given

	        // when
	        when(service.listLocation()).thenReturn(Collections.emptyList());

	        // then
	        mockMvc.perform(get(END_POINT_PATH))
	                .andExpect(status().isNoContent())
	                .andDo(print());
	    }

	    @Test
	    public void testListShouldReturnLocationList() throws Exception {

	        // given
	        Location location1 = new Location()
	                .code("NYC_USA")
	                .cityName("New York City")
	                .regionName("New York")
	                .countryCode("US")
	                .countryName("United States of America")
	                .enabled(true)
	                ;

	        Location location2 = new Location()
	                .code("LACA_USA")
	                .cityName("Los Angeles")
	                .regionName("California")
	                .countryCode("US")
	                .countryName("United States of America")
	                .enabled(true)
	                ;

	        // when
	        when(service.listLocation()).thenReturn(List.of(location1, location2));

	        // then
	        mockMvc.perform(get(END_POINT_PATH))
	                .andExpect(status().isOk())
	                .andExpect(content().contentType("application/json"))
	                .andExpect(jsonPath("$[0].code", is("NYC_USA")))
	                .andExpect(jsonPath("$[0].city_name", is("New York City")))
	                .andExpect(jsonPath("$[0].region_name", is("New York")))
	                .andExpect(jsonPath("$[0].country_name", is("United States of America")))
	                .andExpect(jsonPath("$[0].country_code", is("US")))
	                .andExpect(jsonPath("$[0].enabled", is(true)))
	                .andExpect(jsonPath("$[1].code", is("LACA_USA")))
	                .andExpect(jsonPath("$[1].city_name", is("Los Angeles")))
	                .andExpect(jsonPath("$[1].region_name", is("California")))
	                .andExpect(jsonPath("$[1].country_name", is("United States of America")))
	                .andExpect(jsonPath("$[1].country_code", is("US")))
	                .andExpect(jsonPath("$[1].enabled", is(true)))
	                .andDo(print());
	    }

	    @Test
	    public void testGetLocationShouldReturn405MethodNotAllowed() throws Exception {

	        // given
	        // when

	        // then
	        String requestURI = END_POINT_PATH + "/ABCDEF";
	        mockMvc.perform(post(requestURI))
	                .andExpect(status().isMethodNotAllowed())
	                .andDo(print());
	    }

	    @Test
	    public void testGetLocationShouldReturn404NotFound() throws Exception {

	        // given
	        // when

	        // then
	        String requestURI = END_POINT_PATH + "/ABCDEF";
	        mockMvc.perform(get(requestURI))
	                .andExpect(status().isNotFound())
	                .andDo(print());
	    }

	    @Test
	    public void testGetLocationShouldReturn200OK() throws Exception {

	        // given
	        String code = "LACA_USA";
	        String requestURI = END_POINT_PATH + "/" + code;

	        Location location = new Location()
	                .code("LACA_USA")
	                .cityName("Los Angeles")
	                .regionName("California")
	                .countryCode("US")
	                .countryName("United States of America")
	                .enabled(true);
	                //.build();

	        // when
	        when(service.get(code)).thenReturn(location);

	        // then
	        mockMvc.perform(get(requestURI))
	                .andExpect(status().isOk())
	                .andExpect(content().contentType("application/json"))
	                .andExpect(jsonPath("$.code", is("LACA_USA")))
	                .andExpect(jsonPath("$.city_name", is("Los Angeles")))
	                .andExpect(jsonPath("$.region_name", is("California")))
	                .andExpect(jsonPath("$.country_name", is("United States of America")))
	                .andExpect(jsonPath("$.country_code", is("US")))
	                .andExpect(jsonPath("$.enabled", is(true)))
	                .andDo(print());
	    }

	    @Test
	    public void testUpdateShouldReturn404NotFound() throws Exception {

	        LocationDTO locationDTO = new LocationDTO()
	                .code("ABCDEF")
	                .cityName("Los Angeles")
	                .regionName("California")
	                .countryCode("US")
	                .countryName("United States of America")
	                .enabled(true)
	                ;

	        LocationNotFoundException ex = new LocationNotFoundException(locationDTO.getCityName());

	        when(service.updateLocation(any())).thenThrow(ex);

	        String bodyContent = mapper.writeValueAsString(locationDTO);

	        mockMvc.perform(put(END_POINT_PATH).contentType("application/json").content(bodyContent))
	                .andExpect(status().isNotFound())
	                .andExpect(jsonPath("$.error[0]", is(ex.getMessage())))
	                .andDo(print());
	    }

	    @Test
	    public void testUpdateShouldReturn400BadRequest() throws Exception {

	        Location location = new Location()
	                .cityName("Los Angeles")
	                .regionName("California")
	                .countryCode("US")
	                .countryName("United States of America")
	                .enabled(true);
	                

	        LocationDTO locationDTO = new LocationDTO()
	                .cityName(location.getCityName())
	                .regionName(location.getRegionName())
	                .countryCode(location.getCountryCode())
	                .countryName(location.getCountryName())
	                .enabled(location.isEnabled());
	                


	        String bodyContent = mapper.writeValueAsString(locationDTO);

	        mockMvc.perform(put(END_POINT_PATH).contentType("application/json").content(bodyContent))
	                .andExpect(status().isBadRequest())
	                .andDo(print());
	    }

	    @Test
	    public void testUpdateShouldReturn200OK() throws Exception {

	        Location location = new Location()
	                .code("NYC_USA")
	                .cityName("New York City")
	                .regionName("New York")
	                .countryCode("US")
	                .countryName("United States of America")
	                .enabled(true);

	        LocationDTO locationDTO = new LocationDTO()
	                .code(location.getCode())
	                .cityName(location.getCityName())
	                .regionName(location.getRegionName())
	                .countryCode(location.getCountryCode())
	                .countryName(location.getCountryName())
	                .enabled(location.isEnabled());

	        when(service.updateLocation(location)).thenReturn(location);

	        String bodyContent = mapper.writeValueAsString(locationDTO);

	        mockMvc.perform(put(END_POINT_PATH).contentType("application/json").content(bodyContent))
	                .andExpect(status().isOk())
	                .andExpect(content().contentType("application/json"))
	                .andExpect(jsonPath("$.code", is("NYC_USA")))
	                .andExpect(jsonPath("$.city_name", is("New York City")))
	                .andExpect(jsonPath("$.region_name", is("New York")))
	                .andExpect(jsonPath("$.country_code", is("US")))
	                .andExpect(jsonPath("$.country_name", is("United States of America")))
	                .andExpect(jsonPath("$.enabled", is(true)))
	                .andDo(print());
	    }

	    @Test
	    public void testDeleteLocationShouldReturn404NotFound() throws Exception {

	        String code = "NYC_USA";
	        String requestURI = END_POINT_PATH + "/" + code;

	        LocationNotFoundException ex = new LocationNotFoundException(code);

	        doThrow(ex).when(service).deletedLocation(code);

	        mockMvc.perform(delete(requestURI))
	                .andExpect(status().isNotFound())
	                .andExpect(jsonPath("$.error[0]", is(ex.getMessage())))
	                .andDo(print());
	    }

	    @Test
	    public void testDeleteLocationShouldReturn204NoContent() throws Exception {

	        String code = "NYC_USA";
	        String requestURI = END_POINT_PATH + "/" + code;

	        Mockito.doNothing().when(service).deletedLocation(code);

	        mockMvc.perform(delete(requestURI))
	                .andExpect(status().isNoContent())
	                .andDo(print());
	    }

	    @Test
	    public void testValidateRequestBodyLocationCodeNotNull() throws Exception {

	        LocationDTO locationDTO = new LocationDTO()
	                .cityName("New York City")
	                .regionName("New York")
	                .countryCode("US")
	                .countryName("United States of America")
	                .enabled(true);

	        String bodyContent = mapper.writeValueAsString(locationDTO);

	        mockMvc.perform(post(END_POINT_PATH).contentType("application/json").content(bodyContent))
	                .andExpect(status().isBadRequest())
	                .andExpect(content().contentType("application/json"))
	                .andExpect(jsonPath("$.error[0]", is("Location code cannot be null")))
	                .andDo(print());
	    }

	    @Test
	    public void testValidateRequestBodyLocationCodeLength() throws Exception {

	        LocationDTO locationDTO = new LocationDTO()
	                .code("")
	                .cityName("New York City")
	                .regionName("New York")
	                .countryCode("US")
	                .countryName("United States of America")
	                .enabled(true);

	        String bodyContent = mapper.writeValueAsString(locationDTO);

	        mockMvc.perform(post(END_POINT_PATH).contentType("application/json").content(bodyContent))
	                .andExpect(status().isBadRequest())
	                .andExpect(content().contentType("application/json"))
	                .andExpect(jsonPath("$.error[0]", is("Location code must have 3-12 characters")))
	                .andDo(print());
	    }

	    @Test
	    public void testValidateRequestBodyAllFieldsInvalid() throws Exception {
	        LocationDTO locationDTO = new LocationDTO()
	                .regionName("");

	        String bodyContent = mapper.writeValueAsString(locationDTO);

	        MvcResult mvcResult = mockMvc.perform(post(END_POINT_PATH)
	                .contentType("application/json").content(bodyContent))
	                .andExpect(status().isBadRequest())
	                .andExpect(content().contentType("application/json"))
	                .andDo(print())
	                .andReturn();

	        String responseBody = mvcResult.getResponse().getContentAsString();

	        assertThat(responseBody).contains("Location code cannot be null");
	        assertThat(responseBody).contains("City name cannot be null");
	        assertThat(responseBody).contains("Region name must have 3-128 characters");
	        assertThat(responseBody).contains("Country name cannot be null");
	        assertThat(responseBody).contains("Country code cannot be null");
	    }
}
