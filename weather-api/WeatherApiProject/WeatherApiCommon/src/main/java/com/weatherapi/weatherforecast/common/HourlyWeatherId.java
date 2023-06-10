package com.weatherapi.weatherforecast.common;

 import java.io.Serializable;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Embeddable
public class HourlyWeatherId implements Serializable {
	
	private int hourOfDay;
	
	@ManyToOne
	@JoinColumn(name = "location_code")
	private Location location;  // relationship many-to-one
	
	public int getHourOfDay() {
		return hourOfDay;
	}
	public void setHourOfDay(int hourOfDay) {
		this.hourOfDay = hourOfDay;
	}
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
}
