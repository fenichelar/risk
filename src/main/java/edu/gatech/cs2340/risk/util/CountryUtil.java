package main.java.edu.gatech.cs2340.risk.util;

import java.util.ArrayList;

import main.java.edu.gatech.cs2340.risk.model.*;

/**
 * @author Brittany Wood
 *
 */
public class CountryUtil {

	public static Player getOwner(Country country){
		ArrayList<Territory> territories = country.getTerritories();
		Player owner = territories[0].getOwner();
		for (Territory territory : territories) {
			if (owner.equals(territory.getOwner())){
				owner = territory.getOwner();
			} else {
				owner = null;
				break;
			}
		}
		return owner;  
	} 
}
