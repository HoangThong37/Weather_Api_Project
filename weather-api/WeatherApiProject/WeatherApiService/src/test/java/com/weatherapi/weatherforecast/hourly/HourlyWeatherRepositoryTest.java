package com.weatherapi.weatherforecast.hourly;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.weatherapi.weatherforecast.common.HourlyWeather;
import com.weatherapi.weatherforecast.common.HourlyWeatherId;
import com.weatherapi.weatherforecast.common.Location;
import com.weatherapi.weatherforecast.repository.HourlyWeatherRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class HourlyWeatherRepositoryTest {

	@Autowired
    private HourlyWeatherRepository weatherRepository;
	
	@Test
	public void testAdd() {
		String locationCode = "AMI";
		
		Location location = new Location().code(locationCode);
		
		HourlyWeather hourlyWeather = new HourlyWeather().location(location)
										                 .hourOfDay(8)
											             .precipitation(32) // lượng mưa
											             .temperature(22)
											             .status("Hot");
		
	    HourlyWeather hWeather	= weatherRepository.save(hourlyWeather);
	    assertThat(hWeather).isNotNull();
	    assertThat(hWeather.getId().getLocation().getCode()).isEqualTo(locationCode);
	}
	
	@Test
	public void testDeleted() {
		Location location = new Location().code("AMI");
		
		HourlyWeatherId hourlyWeatherId = new HourlyWeatherId(8, location);
		weatherRepository.deleteById(hourlyWeatherId);
		
		Optional<HourlyWeather> hourlyWeather = weatherRepository.findById(hourlyWeatherId);
		
		assertThat(hourlyWeather).isNotEmpty();
	}
	
	// test findbylocationcode not found() - ko có
	@Test
	public void testFindByLocationCodeNotFound() {
		String locationCode = "MBTest";
		int curentHour = 13;

		List<HourlyWeather> list = weatherRepository.findByLocationCode(locationCode, curentHour);
		assertThat(list).isEmpty();
	}
	
	// test findbylocationcode found
	@Test
	public void testFindByLocationCodeFound() { 
		String locationCode = "MBTest";
		int curentHour = 5;

		List<HourlyWeather> list = weatherRepository.findByLocationCode(locationCode, curentHour);
		assertThat(list).isNotEmpty();
	}
 
}
 