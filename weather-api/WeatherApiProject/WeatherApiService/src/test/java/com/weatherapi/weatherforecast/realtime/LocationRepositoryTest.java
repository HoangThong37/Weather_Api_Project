package com.weatherapi.weatherforecast.realtime;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.weatherapi.weatherforecast.common.RealtimeWeather;
import com.weatherapi.weatherforecast.repository.RealtimeRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class LocationRepositoryTest {
	
	@Autowired
	private RealtimeRepository realtimeRepository;

    @Test
    public void testUpdated() {
    	String locationCode = "VIETNAM";
   
        RealtimeWeather realtimeWeather	= realtimeRepository.findById(locationCode).get();
        
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
    	String countryCode = "SG";
    	String city = "HO CHI MINH City";
    	
      RealtimeWeather realtimeWeather = realtimeRepository.findByCountryCodeAndCity(countryCode, city);
      
      assertThat(realtimeWeather).isNotNull();
      assertThat(realtimeWeather.getLocation().getCountryCode()).isEqualTo(countryCode);
    }

}
