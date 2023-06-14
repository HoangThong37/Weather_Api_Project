package com.weatherapi.weatherforecast.converter;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.weatherapi.weatherforecast.common.HourlyWeather;
import com.weatherapi.weatherforecast.dto.HourlyWeatherDTO;
import com.weatherapi.weatherforecast.dto.HourlyWeatherListDTO;

@Component
public class HourlyWeatherConverter {
	
    @Autowired
    private ModelMapper modelMapper;

    // entity -> dto
    public HourlyWeatherListDTO convertToDTO(List<HourlyWeather> hourlyForecast) {
    	HourlyWeatherListDTO result = new HourlyWeatherListDTO();
    	
    	for(HourlyWeather item : hourlyForecast) {
    		HourlyWeatherDTO dto = modelMapper.map(item, HourlyWeatherDTO.class);
    		result.addHourlyWeatherDTO(dto);
    	}
    	
        return result;
        
        
//        hourlyForecast.stream()
//        .map(item -> modelMapper.map(item, HourlyWeatherDTO.class))
//        .forEach(dto -> result.addHourlyWeatherDTO(dto));
    }
}
