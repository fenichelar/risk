package main.java.edu.gatech.cs2340.risk.model;

import java.util.ArrayList;

/**
 * @author Caroline Paulus
 *
 */
public class Country {
	
	private String countryName;
	private ArrayList<Territory> territories;
	private Player owner;
	
	public Country(String countryName, ArrayList<Territory> territories) {
		this.setCountryName(countryName);
		this.setTerritories(territories);
		setOwner(null);
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

	public Player getOwner() {
		return owner;
	}

	public void setOwner(Player owner) {
		this.owner = owner;
	}

}
