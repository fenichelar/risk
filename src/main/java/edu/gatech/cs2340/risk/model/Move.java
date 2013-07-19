package main.java.edu.gatech.cs2340.risk.model;

public class Move {

	Territory source;
	Territory destination;
	int numArmies;

	public Move (Territory source) {
		this.source = source;
	}

	public Move (Territory source, Territory destination) {
		this(source);
		this.destination = destination;
	}

	public void setDestination (Territory destination) {
		this.destination = destination;
	}

	public void setNumArmies(int numArmies) {
		this.numArmies = numArmies;
	}

	public Territory getSource() {
		return source;
	}

	public void doMove() {
		destination.addNumberOfArmies(numArmies);
		source.removeNumberOfArmies(numArmies);
	}

	public boolean isValidMove() {
		return source.getNumberOfArmies() > 1;
	}

	public boolean oneArmyAtSourceTerritory() {
		return source.getNumberOfArmies() == 1;
	}

	@Override
	public String toString() {
		return "[Source: " + source + ", destination: " + destination + "]";
	}

}