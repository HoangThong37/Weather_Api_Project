package com.weatherapi.weatherforecast.location;

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
}
