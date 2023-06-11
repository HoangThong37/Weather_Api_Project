package com.weatherapi.weatherforecast.common;

 import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "weather_hourly")
public class HourlyWeather {
	
	@EmbeddedId
	private HourlyWeatherId id = new HourlyWeatherId();
	
	private int temperature;   // nđộ
	private int precipitation; // lượng mưa
	
	@Column(length = 50)
	private String status;

	public HourlyWeatherId getId() {
		return id;
	}

	public void setId(HourlyWeatherId id) {
		this.id = id;
	}

	public int getTemperature() {
		return temperature;
	}

	public void setTemperature(int temperature) {
		this.temperature = temperature;
	}

	public int getPrecipitation() {
		return precipitation;
	}

	public void setPrecipitation(int precipitation) {
		this.precipitation = precipitation;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	// builder temperature;
	public HourlyWeather temperature(int temp) {
		setTemperature(temp);
		return this;
	}
	
	// id
	public HourlyWeather id(Location location, int hour) {
		this.id.setHourOfDay(hour);
		this.id.setLocation(location);
		return this;
	}
	
	// precipitation   
	public HourlyWeather precipitation(int precipitation) {
		setPrecipitation(precipitation);
		return this;
	}
	
	// status
	public HourlyWeather status(String status) {
		setStatus(status);
		return this;
	}
	
	public HourlyWeather location(Location location) {
		this.id.setLocation(location);
		return this;
	}
	
	public HourlyWeather hourOfDay(int hour) {
		this.id.setHourOfDay(hour);
		return this;
	}
	
	
}
