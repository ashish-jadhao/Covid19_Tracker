package com.arj.covidtracker.model;

public class LocationStats {
	
	private String country;
	private Integer latestTotalDeaths;
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public Integer getLatestTotalDeaths() {
		return latestTotalDeaths;
	}
	public void setLatestTotalDeaths(Integer latestTotalDeaths) {
		this.latestTotalDeaths = latestTotalDeaths;
	}
	@Override
	public String toString() {
		return "country=" + country + ", latestTotalDeaths=" + latestTotalDeaths ;
		
		 
	} 
	

}
