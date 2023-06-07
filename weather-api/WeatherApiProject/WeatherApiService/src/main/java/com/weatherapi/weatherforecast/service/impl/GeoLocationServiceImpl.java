package com.weatherapi.weatherforecast.service.impl;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ip2location.IP2Location;
import com.ip2location.IPResult;
import com.weatherapi.weatherforecast.common.Location;
import com.weatherapi.weatherforecast.exception.GeoLocationException;
import com.weatherapi.weatherforecast.service.IGeoLocationService;

@Service
public class GeoLocationServiceImpl implements IGeoLocationService {
	private static final Logger LOGGER = LoggerFactory.getLogger(IGeoLocationService.class);
	
	private String dbPath = "ip2locationdb/IP2LOCATION-LITE-DB3.BIN";
	private IP2Location ip2Location = new IP2Location();

	public GeoLocationServiceImpl() {
		try {
			ip2Location.Open(dbPath);  // Initialize the component and preload the BIN file.
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	@Override
	public Location getLocation(String ipAddress) throws Exception  { // GeoLocation : định vị
		try {
			IPResult ipResult = ip2Location.IPQuery(ipAddress); // Query IP address. This method returns results in com.ip2location.IPResult object.
		
		    if (!"OK".equals(ipResult.getStatus())) {
				throw new GeoLocationException("Geolocation failed with status: " + ipResult.getStatus());
			} 
		    return new Location(ipResult.getCity(), ipResult.getRegion(), ipResult.getCountryLong(), ipResult.getCountryShort());
		} catch (IOException e) {
			throw new GeoLocationException("Error querying IP database", e);
		}
	}

}
