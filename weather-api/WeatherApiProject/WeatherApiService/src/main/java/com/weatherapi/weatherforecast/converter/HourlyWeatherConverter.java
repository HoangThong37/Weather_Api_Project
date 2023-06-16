package com.weatherapi.weatherforecast.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;import org.springframework.data.convert.DtoInstantiatingConverter;
import org.springframework.stereotype.Component;

import com.weatherapi.weatherforecast.common.HourlyWeather;
import com.weatherapi.weatherforecast.common.Location;
import com.weatherapi.weatherforecast.dto.HourlyWeatherDTO;
import com.weatherapi.weatherforecast.dto.HourlyWeatherListDTO;

@Component
public class HourlyWeatherConverter {
	
    @Autowired
    private ModelMapper modelMapper;

    // entity -> dto
    public HourlyWeatherListDTO convertToDTO(List<HourlyWeather> hourlyForecast) {
    	Location location = hourlyForecast.get(0).getId().getLocation();
    	
    	HourlyWeatherListDTO result = new HourlyWeatherListDTO();
    	
    	for(HourlyWeather item : hourlyForecast) {
    		HourlyWeatherDTO dto = modelMapper.map(item, HourlyWeatherDTO.class);
    		result.addHourlyWeatherDTO(dto);
    	}
    	result.setLocation(location.toString());
        return result;
    }
    
    // DTO -> ENTITY
    public List<HourlyWeather> convertToEntity(List<HourlyWeatherDTO> listHourlyWeatherDTO) {
        return listHourlyWeatherDTO.stream().map(dto -> modelMapper.map(dto, HourlyWeather.class))
        		                   .collect(Collectors.toList());
    }
}
