package com.weatherapi.weatherforecast.repository;

import org.springframework.data.repository.CrudRepository;

import com.weatherapi.weatherforecast.common.Location;

public interface LocationRepository extends CrudRepository<Location, String> {

}
