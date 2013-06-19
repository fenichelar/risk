package main.java.edu.gatech.cs2340.risk.service.impl;

import java.util.ArrayList;

import main.java.edu.gatech.cs2340.risk.dao.mock.TerritoryDAOMock;
import main.java.edu.gatech.cs2340.risk.model.Territory;
import main.java.edu.gatech.cs2340.risk.service.TerritoryService;

/**
 * @author Caroline Paulus
 *
 */
public class TerritoryServiceImpl implements TerritoryService {
	
	private TerritoryDAOMock territoryDAO = new TerritoryDAOMock();

	@Override
	public ArrayList<Territory> getTerritories() {
		return territoryDAO.getTerritories();
	}

	@Override
	public ArrayList<Territory> getTerritories(int countryId) {
		return territoryDAO.getTerritories(countryId);
	}

	@Override
	public Territory getTerritory(int territoryId) {
		return territoryDAO.getTerritory(territoryId);
	}

}