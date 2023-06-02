package com.weatherapi.weatherforecast.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

}
