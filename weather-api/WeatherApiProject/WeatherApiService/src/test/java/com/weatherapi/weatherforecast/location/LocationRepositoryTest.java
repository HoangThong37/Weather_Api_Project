package com.weatherapi.weatherforecast.location;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.weatherapi.weatherforecast.common.HourlyWeather;
import com.weatherapi.weatherforecast.common.Location;
import com.weatherapi.weatherforecast.common.RealtimeWeather;
import com.weatherapi.weatherforecast.repository.LocationRepository;
import com.weatherapi.weatherforecast.repository.RealtimeRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class LocationRepositoryTest {

	@Autowired
	private RealtimeRepository realtimeRepository;
	
	@Autowired
	private LocationRepository locationRepository;

	@Test
	public void testUpdated() {
		String locationCode = "VIETNAM";

		RealtimeWeather realtimeWeather = realtimeRepository.findById(locationCode).get();

		realtimeWeather.setHumidity(35);
		realtimeWeather.setStatus("Snowy");

		realtimeRepository.save(realtimeWeather);
	}

	@Test
	public void testFindByCountryCodeAndCityFound() {
		String countryCode = "JP";
		String city = "Japan";

		RealtimeWeather realtimeWeather = realtimeRepository.findByCountryCodeAndCity(countryCode, city);
		assertThat(realtimeWeather).isNull();
	}

	@Test
	public void testFindByCountryCodeAndCitySuccess() {
		String countryCode = "US";
		String city = "New York City";

		RealtimeWeather realtimeWeather = realtimeRepository.findByCountryCodeAndCity(countryCode, city);

		assertThat(realtimeWeather).isNotNull();
		assertThat(realtimeWeather.getLocation().getCountryCode()).isEqualTo(countryCode);
	}

	// test findByLocationCode
	@Test
	public void testFindByLocationNotFound() {
		String location = "VIETNAMs";

		RealtimeWeather realtimeWeather = realtimeRepository.findByLocationCode(location);

		assertThat(realtimeWeather).isNull();
		//assertThat(realtimeWeather.getLocation().getCountryCode()).isEqualTo(countryCode);
	}
	
	@Test
	public void testFindByLocationSuccess() {
		String location = "VIETNAM";

		RealtimeWeather realtimeWeather = realtimeRepository.findByLocationCode(location);

		assertThat(realtimeWeather).isNotNull();
		assertThat(realtimeWeather.getLocationCode()).isEqualTo(location);
	}
	
	@Test
	public void testAddsuccess() {
		Location location = new Location();
		location.setCode("MBTest");
		location.setCityName("Mumbai");
		location.setRegionName("Maharashtra");
		location.setCountryCode("In");
		location.setCountryName("India");
		location.setEnabled(false);
		
	    Location savedLocation = locationRepository.save(location);
		
		assertThat(savedLocation).isNotNull();
		assertThat(savedLocation.getCode()).isEqualTo("MBTest");
	}
	
	
	// test add hour weather
	@Test
	public void testAddHourWeatherData() {
		Location location = locationRepository.findById("VIETNAM").get();
		// get hour weather
		List<HourlyWeather> listHourlyWeathers = location.getListHourlyWeathers();
		
		HourlyWeather hourlyWeather1 = new HourlyWeather().id(location, 15)
				                                         .precipitation(50)
				                                         .temperature(35)
				                                         .status("Sunny");
		
		HourlyWeather hourlyWeather2 = new HourlyWeather().location(location)
				                                          .hourOfDay(17)
											              .precipitation(54) // lượng mưa
											              .temperature(32)
											              .status("Sunny");
		
		listHourlyWeathers.add(hourlyWeather1);
		listHourlyWeathers.add(hourlyWeather2);
		
		Location updateLocation = locationRepository.save(location);
		
		assertThat(updateLocation.getListHourlyWeathers()).isNotEmpty();
	}
	
	// test findByCountryCodeAndCityName Not Found()
	@Test
	public void testFindByCountryCodeAndCityNameNotFound() {
		String countryCode = "IndiaA";
		String cityName = "city test";

		Location location = locationRepository.findByCountryCodeAndCityName(countryCode, cityName);

		assertThat(location).isNull();
	}
	
	// test FindByCountryCodeAndCityName Found
	@Test
	public void testFindByCountryCodeAndCityNameFound() { // ko 
		String countryCode = "In";
		String cityName = "city test";

		Location location = locationRepository.findByCountryCodeAndCityName(countryCode, cityName);

		assertThat(location).isNotNull();
		assertThat(location.getCountryCode()).isEqualTo(countryCode);
		assertThat(location.getCityName()).isEqualTo(cityName);
	}
}
 