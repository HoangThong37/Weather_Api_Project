package com.weatherapi.weatherforecast.location;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.ip2location.IP2Location;
import com.ip2location.IPResult;

public class IP2LocationTest {
	
	private String dbPath = "ip2locationdb/IP2LOCATION-LITE-DB3.BIN";
	private IP2Location ip2Location = new IP2Location();
	
	@Test
	public void testInvalidIP() throws IOException {
		IP2Location ip2Location = new IP2Location();
		ip2Location.Open(dbPath);
		
		String abc = "abc";
		IPResult ipResult = ip2Location.IPQuery(abc);
		assertThat(ipResult.getStatus()).isEqualTo("INVALID_IP_ADDRESS");
	}
	
	@Test
	public void testValidIP1() throws IOException {
		
		IP2Location ip2Location = new IP2Location();
		ip2Location.Open(dbPath);
		
		String ipAddress = "108.30.178.78"; // New york
		IPResult ipResult = ip2Location.IPQuery(ipAddress);
		
		assertThat(ipResult.getStatus()).isEqualTo("OK");
		System.out.println(ipResult);
	}

}
