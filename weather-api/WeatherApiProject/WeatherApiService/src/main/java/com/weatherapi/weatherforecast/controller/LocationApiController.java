package com.weatherapi.weatherforecast.controller;


import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.weatherapi.weatherforecast.common.Location;
import com.weatherapi.weatherforecast.service.ILocationService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/v1/locations")
public class LocationApiController {
	
	@Autowired
	private ILocationService locationService;
	
	@PostMapping
	public ResponseEntity<Location> createLocation(@RequestBody @Valid Location location) {
	    Location addLocation = locationService.addLocation(location);
		URI uri = URI.create("/v1/locations/" + addLocation.getCode());
		return ResponseEntity.created(uri).body(addLocation);
	}

}
