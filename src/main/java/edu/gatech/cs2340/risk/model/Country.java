package main.java.edu.gatech.cs2340.risk.model;

import java.util.ArrayList;

public class Country {
	
	private String countryName;
	private ArrayList<Territory> territories;
	
	public Country(String countryName, ArrayList<Territory> territories) {
		this.setCountryName(countryName);
		this.setTerritories(territories);
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public ArrayList<Territory> getTerritories() {
		return territories;
	}

	public void setTerritories(ArrayList<Territory> territories) {
		this.territories = territories;
	}

}
