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
import com.weatherapi.weatherforecast.converter.LocationConverter;
import com.weatherapi.weatherforecast.dto.LocationDTO;
import com.weatherapi.weatherforecast.exception.LocationNotFoundException;
import com.weatherapi.weatherforecast.service.ILocationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1/locations")
public class LocationApiController {

	@Autowired
	private ILocationService locationService;

	@Autowired
	private LocationConverter locationConverter;

	@PostMapping
	public ResponseEntity<LocationDTO> createLocation(@RequestBody @Valid LocationDTO location) {
		Location addLocation = locationService.addLocation(locationConverter.convertToEntity(location));
		URI uri = URI.create("/v1/locations/" + addLocation.getCode());
		return ResponseEntity.created(uri).body(locationConverter.convertToDTO(addLocation));
	}

	@GetMapping
	public ResponseEntity<?> listLocation() {
		List<Location> list = locationService.listLocation();

		if (list.isEmpty()) {
			return ResponseEntity.noContent().build(); // return 204: no content
		}
		return ResponseEntity.ok(locationConverter.listEntityToListDTO(list));
	}

	// get location
	@GetMapping("/{code}")
	public ResponseEntity<?> getLocation(@PathVariable(value = "code", required = false) String code)
			throws LocationNotFoundException {
		Location location = locationService.get(code);

		return ResponseEntity.ok(locationConverter.convertToDTO(location));
	}

	// update location
	@PutMapping
	public ResponseEntity<?> updateLocation(@RequestBody @Valid LocationDTO locationDTO) {
		Location updateLocation = locationService.updateLocation(locationConverter.convertToEntity(locationDTO));

		return ResponseEntity.ok(locationConverter.convertToDTO(updateLocation));

	}

	// delete location
	@DeleteMapping("/{code}")
	public ResponseEntity<?> deleteLocation(@PathVariable(value = "code", required = false) String code) {
		locationService.deletedLocation(code);

		return ResponseEntity.noContent().build(); // ko còn gtri

	}
}
