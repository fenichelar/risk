package main.java.edu.gatech.cs2340.risk.model;

public class Move {
	
	private Territory fortifyingTerritory, fortifiedTerritory;

	public Territory getFortifyingTerritory() {
		return fortifyingTerritory;
	}

	public void setFortifyingTerritory(Territory fortifyingTerritory) {
		this.fortifyingTerritory = fortifyingTerritory;
	}

	public Territory getFortifiedTerritory() {
		return fortifiedTerritory;
	}

	public void setFortifiedTerritory(Territory fortifiedTerritory) {
		this.fortifiedTerritory = fortifiedTerritory;
	}
	
}
