package com.weatherapi.weatherforecast.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.weatherapi.weatherforecast.common.Location;

public interface LocationRepository extends CrudRepository<Location, String> {
	
	@Query("SELECT l FROM Location l WHERE l.trashed = false ")
	public List<Location> findUntrashed();
	

}
