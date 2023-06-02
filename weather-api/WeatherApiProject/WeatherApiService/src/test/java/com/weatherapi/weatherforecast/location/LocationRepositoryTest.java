package com.weatherapi.weatherforecast.location;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.weatherapi.weatherforecast.common.Location;
import com.weatherapi.weatherforecast.repository.LocationRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class LocationRepositoryTest {
	
	@Autowired
	private LocationRepository repository;

	@Test
	public void testAddLocationSuccess() {

		Location location = new Location();
		location.setCode("VIETNAM");
		location.setCityName("HO CHI MINH City");
		location.setRegionName("SÀI GÒN");
		location.setCountryCode("SG");
		location.setCountryName("THÀNH PHỐ HCM");
		location.setEnabled(true);
		location.setTrashed(false);

		Location savedLocation = repository.save(location);

		assertThat(savedLocation).isNotNull();
		// assertEquals(savedLocation.getCode(), "NYC_USA");
	}

	@Test
	public void testListSuccess() {
		List<Location> locations = repository.findUntrashed();

		assertThat(locations).isNotEmpty();
		locations.forEach(System.out::println);
	}
	
	@Test
	public void testGetNotFound() {
		String code = "ABC";
		Location location = repository.findByCode(code);
		
		assertThat(location).isNull();
	}
	
	@Test
	public void testGetFound() {
		String code = "AMI";
		Location location = repository.findByCode(code);
		
		assertThat(location).isNotNull();
		assertThat(location.getCode()).isEqualTo(code);
	}
}
