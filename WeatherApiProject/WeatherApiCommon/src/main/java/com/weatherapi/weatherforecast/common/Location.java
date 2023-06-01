package com.weatherapi.weatherforecast.common;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "locations")
public class Location {
   
	@Column(length = 12, nullable = false, unique = true)
	@Id
	private String code;
	
	@Column(length = 128, nullable = false)
	private String cityName;     // tên thành phố
	
	@Column(length = 128)
	private String regionName;   // tên vùng
	
	@Column(length = 64, nullable = false)
	private String countryName;
	
	@Column(length = 2, nullable = false)
	private String countryCode;  // tên quốc gia 

	private boolean enabled;
	private boolean trashed;  // bỏ thùng rác
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getRegionName() {
		return regionName;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public boolean isTrashed() {
		return trashed;
	}
	public void setTrashed(boolean trashed) {
		this.trashed = trashed;
	}

	
	
	
}
