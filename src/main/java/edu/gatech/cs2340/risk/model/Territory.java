package main.java.edu.gatech.cs2340.risk.model;

/**
 * @author Caroline Paulus
 *
 */
public class Territory {
	
	private String territoryName;
	private Country country;
	private Player owner;
	
	public String getTerritoryName() {
		return territoryName;
	}
	
	public void setTerritoryName(String territoryName) {
		this.territoryName = territoryName;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public Player getOwner() {
		return owner;
	}

	public void setOwner(Player owner) {
		this.owner = owner;
	}

}
