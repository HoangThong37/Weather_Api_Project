package com.weatherapi.weatherforecast.controller;


import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.weatherapi.weatherforecast.common.Location;
import com.weatherapi.weatherforecast.service.ILocationService;

@Controller
@RequestMapping("/v1/locations")
public class LocationApiController {
	
	@Autowired
	private ILocationService locationService;
	
	@GetMapping
	public ResponseEntity<Location> createLocation(@RequestBody Location location) {
	    Location addLocation = locationService.addLocation(location);
		URI uri = URI.create("/v1/locations/" + location.getCode());
		return ResponseEntity.created(uri).body(addLocation);
	}

}
