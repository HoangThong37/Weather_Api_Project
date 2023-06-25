package com.weatherapi.weatherforecast.converter;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.weatherapi.weatherforecast.common.Location;
import com.weatherapi.weatherforecast.dto.LocationDTO;

@Component
public class LocationConverter {
	
    @Autowired
    private ModelMapper modelMapper;

    // convert entity -> dto
    public LocationDTO convertToDTO(Location entity) {
    	LocationDTO dto = modelMapper.map(entity, LocationDTO.class);
        return dto;
    }

    // convert dto -> entity
    public Location convertToEntity(LocationDTO dto) {
    	Location entity = modelMapper.map(dto, Location.class);
        return entity;
    }
    
    // convert List<dto> -> List<entity> 
    public List<LocationDTO> listEntityToListDTO(List<Location> listEntity) {
    	List<LocationDTO> listLocation = new ArrayList<>();
    	for(Location item : listEntity) {
    		 LocationDTO dto = modelMapper.map(item, LocationDTO.class);
    		 listLocation.add(dto);
    	}
        return listLocation;
    }
}
