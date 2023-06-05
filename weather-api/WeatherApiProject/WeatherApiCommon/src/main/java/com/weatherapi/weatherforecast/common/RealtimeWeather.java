package com.weatherapi.weatherforecast.common;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "realtime_weather")
public class RealtimeWeather {
	
	@Id
	@Column(name = "location_code")
	private String locationCode;
	
	@Column
	private int temperature;
	
	@Column
	private int humidity;
	
	@Column
	private int precipitation;

	@Column
	private int windSpeed;
	
	@Column
	private String status;
	
	@Column
	private Date lastUpdated;
	
	@OneToOne
	@JoinColumn(name = "location_code") 
	@MapsId
	private Location location;
	
	
	public String getLocationCode() {
		return locationCode;
	}

	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}

	public int getTemperature() {
		return temperature;
	}

	public void setTemperature(int temperature) {
		this.temperature = temperature;
	}

	public int getHumidity() {
		return humidity;
	}

	public void setHumidity(int humidity) {
		this.humidity = humidity;
	}

	public int getPrecipitation() {
		return precipitation;
	}

	public void setPrecipitation(int precipitation) {
		this.precipitation = precipitation;
	}

	public int getWindSpeed() {
		return windSpeed;
	}

	public void setWindSpeed(int windSpeed) {
		this.windSpeed = windSpeed;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.locationCode = location.getCode();
		this.location = location;
	}
	
	
}
