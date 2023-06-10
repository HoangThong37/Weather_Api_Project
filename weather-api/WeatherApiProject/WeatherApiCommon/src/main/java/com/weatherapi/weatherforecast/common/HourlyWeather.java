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
	
	private int temperature;
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
	
	
}
