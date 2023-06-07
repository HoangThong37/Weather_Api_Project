package com.weatherapi.weatherforecast.converter;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.weatherapi.weatherforecast.common.RealtimeWeather;
import com.weatherapi.weatherforecast.dto.RealtimeWeatherDTO;

@Component
public class RealtimeWeatherConverter {
	
    @Autowired
    private ModelMapper modelMapper;

    // entity -> dto
    public RealtimeWeatherDTO convertToDTO(RealtimeWeather entity) {
    	RealtimeWeatherDTO dto = modelMapper.map(entity, RealtimeWeatherDTO.class);
        return dto;
    }

    // dto -> entity
    public RealtimeWeather convertToEntity(RealtimeWeatherDTO dto) {
    	RealtimeWeather entity = modelMapper.map(dto, RealtimeWeather.class);
        return entity;
    }

}
