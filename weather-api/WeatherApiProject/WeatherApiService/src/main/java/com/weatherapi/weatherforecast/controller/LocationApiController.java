package com.weatherapi.weatherforecast.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.weatherapi.weatherforecast.common.Location;
import com.weatherapi.weatherforecast.exception.LocationNotFoundException;
import com.weatherapi.weatherforecast.service.ILocationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1/locations")
public class LocationApiController {

    @Autowired
	private  ILocationService locationService;
	
	@PostMapping
	public ResponseEntity<Location> createLocation(@RequestBody @Valid Location location) {
		Location addLocation = locationService.addLocation(location);
		URI uri = URI.create("/v1/locations/" + addLocation.getCode());
		return ResponseEntity.created(uri).body(addLocation);
	}

	@GetMapping
	public ResponseEntity<?> listLocation() {
		List<Location> list = locationService.listLocation();

		if (list.isEmpty()) {
			return ResponseEntity.noContent().build(); // return 204: no content
		}
		return ResponseEntity.ok(list);
	}

	@GetMapping("/{code}")
	public ResponseEntity<?> getLocation(@PathVariable(value = "code", required = false) String code) {
		Location location = locationService.get(code);
		if (location == null) {
			return ResponseEntity.notFound().build(); // return 204: no content
		}
		return ResponseEntity.ok(location);
	}
	
	// update location
	@PutMapping
	public ResponseEntity<?> updateLocation(@RequestBody @Valid Location location) throws LocationNotFoundException {
		try {
			Location updateLocation = locationService.updateLocation(location);
			return ResponseEntity.ok(updateLocation);
		} catch (Exception e) {
			return ResponseEntity.notFound().build(); // return 204: no content
		}
	}
	
	// delete location
	@DeleteMapping("/{code}")
	public ResponseEntity<?> deleteLocation(@PathVariable(value = "code", required = false) String code) {
		try {
			locationService.deletedLocation(code);
			return ResponseEntity.noContent().build(); // ko còn gtri
		} catch (Exception e) {
			return ResponseEntity.notFound().build(); // return 204: no content
		}
	}
}
