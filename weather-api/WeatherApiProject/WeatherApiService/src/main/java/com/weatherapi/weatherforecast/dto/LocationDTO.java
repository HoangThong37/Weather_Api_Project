package com.weatherapi.weatherforecast.dto;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.weatherapi.weatherforecast.common.Location;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonPropertyOrder({"code", "city_name", "region_name", "country_name", "enabled"})
public class LocationDTO {
	
	@NotBlank(message = "Location code can not blank")
	@Length(min = 3, max = 12, message = "Location code must have 3-12 characters")
	private String code;
	
	@JsonProperty("city_name")
	@NotBlank(message = "City name can not blank")
	@Length(min = 3, max = 128, message = "City name must have 3-128 characters")
	private String cityName;     // tên thành phố
	
	@JsonProperty("region_name")
	@Length(min = 3, max = 128, message = "Region name must have 3-128 characters")
	@JsonInclude(JsonInclude.Include.NON_NULL) // đối tượng được gắn với annotation này sẽ không được đưa vào đầu ra JSON nếu chúng nul
	private String regionName;   // tên vùng
	
	@JsonProperty("country_name")
	@NotBlank(message = "Country name can not blank")
	@Length(min = 3, max = 64, message = "Country name must have 3-64 characters")
	private String countryName;
	
	@JsonProperty("country_code")
	@NotBlank(message = "Country code can not blank")
	@Length(min = 2, max = 2, message = "Country code must have 2 characters")
	private String countryCode;
	
	private boolean enabled;
	

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
	
	// builder
	public LocationDTO code(String code) {
		setCode(code);
		return this;
	}

	// cityName
	public LocationDTO cityName(String cityName) {
		setCityName(cityName);
		return this;
	}

	// regionName
	public LocationDTO regionName(String regionName) {
		setRegionName(regionName);
		return this;
	}

	// countryCode
	public LocationDTO countryCode(String countryCode) {
		setCountryCode(countryCode);
		return this;
	}
	
	// countryName
	public LocationDTO countryName(String countryName) {
		setCountryName(countryName);
		return this;
	} 

	// enabled
	public LocationDTO enabled(Boolean enabled) {
		setEnabled(enabled);
		return this;
	}

}
