package main.java.edu.gatech.cs2340.risk.controller.helper;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import main.java.edu.gatech.cs2340.risk.dao.mock.TerritoryDAOMock;
import main.java.edu.gatech.cs2340.risk.model.Risk;
import main.java.edu.gatech.cs2340.risk.model.Territory;
import main.java.edu.gatech.cs2340.risk.util.ArmyUtil;
import main.java.edu.gatech.cs2340.risk.util.RiskConstants;

/**
 * Stage 2
 *
 */
public class RotateController extends HttpServlet {
	
	private static Logger log = Logger.getLogger(RotateController.class);
	
	public void doPost(HttpServletRequest request,
			HttpServletResponse response, Risk risk) throws IOException, ServletException {
		
		log.debug("In doPost()");
		switch (risk.getStep()) {
			case RiskConstants.BEGINNING_OF_TURN: 
				assignAdditionalArmies(request, response, risk);
				break;
			case RiskConstants.SELECT_DEFENDING_TERRITORY: 
				distributeAdditionalArmies(request, response, risk);
				break;
		}
	}
	
	protected void assignAdditionalArmies(HttpServletRequest request, 
			HttpServletResponse response, Risk risk) throws ServletException, IOException {

		log.debug("in assignAdditionalArmies()");
		// determine the number of armies the player should receive
		int armiesToAssign = ArmyUtil.getArmiesToAssign(risk.getCurrentPlayer());
		log.debug("Player " + risk.getCurrentPlayer() + " is receiving " 
				+ armiesToAssign + " additional armies");

		risk.getCurrentPlayer().setAvailableArmies(armiesToAssign);
		risk.setStep(RiskConstants.DURING_TURN);
		risk.setDirectionsNum(RiskConstants.ADDITIONAL_ARMIES_DIRECTIONS);

		risk.getAppController().forwardUpdatedVariables(request, response, risk);
	}
	

	protected void distributeAdditionalArmies(HttpServletRequest request,
			HttpServletResponse response, Risk risk) throws IOException, ServletException {

		log.debug("In distributeAdditionalArmies()");

		risk.setCurrentPlayer(Integer.parseInt(request.getParameter("currentPlayerId")));

		TerritoryDAOMock territoryDAO = new TerritoryDAOMock();
		Territory currentTerritory = territoryDAO.getTerritory(risk.getCurrentPlayer(), 
				Integer.parseInt(request.getParameter("territoryId")));

		if (currentTerritory != null && risk.getCurrentPlayer().getAvailableArmies() > 0) {

			log.debug("Current territory: " + currentTerritory);

			currentTerritory.addArmy();
			risk.getCurrentPlayer().removeArmy();

			if (risk.getCurrentPlayer().getAvailableArmies() == 0) {
				risk.setStage(RiskConstants.SELECT_OPTIONS);
			}

		} else {
			log.debug("Territory does not belong to player");
		}
		risk.getAppController().forwardUpdatedVariables(request, response, risk);
	}


}
