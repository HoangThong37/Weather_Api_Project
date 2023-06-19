package com.weatherapi.weatherforecast.common;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;

@Entity
@Table(name = "locations")
@Builder
@Data
public class Location {

	@Column(length = 12, nullable = false, unique = true)
	@Id
	private String code;

	@Column(length = 128, nullable = false)
	private String cityName; // tên thành phố

	@Column(length = 128)
	private String regionName; // tên vùng

	@Column(length = 64, nullable = false)
	private String countryName;

	@Column(length = 2, nullable = false)
	private String countryCode; // tên quốc gia

	private boolean enabled;

	@JsonIgnore
	private boolean trashed; // bỏ thùng rác

	@OneToOne(mappedBy = "location", cascade = CascadeType.ALL)
	@PrimaryKeyJoinColumn
	private RealtimeWeather realtimeWeather;

	@OneToMany(mappedBy = "id.location", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<HourlyWeather> listHourlyWeathers = new ArrayList<>();

	public Location() {

	}

	public Location(String cityName, String regionName, String countryName, String countryCode) {
		super();
		this.cityName = cityName;
		this.regionName = regionName;
		this.countryName = countryName;
		this.countryCode = countryCode;
	}

	public RealtimeWeather getRealtimeWeather() {
		return realtimeWeather;
	}

	public void setRealtimeWeather(RealtimeWeather realtimeWeather) {
		this.realtimeWeather = realtimeWeather;
	}

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

	public List<HourlyWeather> getListHourlyWeathers() {
		return listHourlyWeathers;
	}

	public void setListHourlyWeathers(List<HourlyWeather> listHourlyWeathers) {
		this.listHourlyWeathers = listHourlyWeathers;
	}

	// builder
	public Location code(String code) {
		setCode(code);
		return this;
	}

	// cityName
	public Location cityName(String cityName) {
		setCityName(cityName);
		return this;
	}

	// regionName
	public Location regionName(String regionName) {
		setRegionName(regionName);
		return this;
	}

	// countryName
	public Location countryName(String countryName) {
		setCountryName(countryName);
		return this;
	}

	// countryCode
	public Location countryCode(String countryCode) {
		setCountryCode(countryCode);
		return this;
	}

	// enabled
	public Location enabled(Boolean enabled) {
		setEnabled(enabled);
		return this;
	}

	@Override
	public String toString() {
		return cityName + " , " + (regionName != null ? regionName : "") + " , " + countryName;
	}

//	public static Location builder() {
//		
//		return null;
//	}

}
