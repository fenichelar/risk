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
import main.java.edu.gatech.cs2340.risk.util.RiskConstants;

/**
 * Stage 1
 *
 */
public class InitializeController extends HttpServlet {
	
	private static Logger log = Logger.getLogger(InitializeController.class);
	
	private TurnController rotateController = new TurnController();
	
	
	public void doPost(HttpServletRequest request,
			HttpServletResponse response, Risk risk) throws IOException, ServletException {
		
		distributeInitialArmies(request, response, risk);
		return;
	}
	

	protected void distributeInitialArmies(HttpServletRequest request,
			HttpServletResponse response, Risk risk) throws IOException, ServletException {

		log.debug("In distributeInitialArmies()");

		risk.setCurrentPlayer(Integer.parseInt(request.getParameter("currentPlayerId")));
		log.debug("Current player: " + risk.getCurrentPlayer());

		TerritoryDAOMock territoryDAO = new TerritoryDAOMock();
		Territory currentTerritory = territoryDAO.getTerritory(risk.getCurrentPlayer(), 
				Integer.parseInt(request.getParameter("territoryId")));

		if (currentTerritory != null && risk.getCurrentPlayer().getAvailableArmies() > 0) {

			log.debug("Current territory: " + currentTerritory);

			currentTerritory.addArmy();
			risk.getCurrentPlayer().removeArmy();

			risk.moveToNextPlayer();

			if (risk.getCurrentPlayer().getAvailableArmies() < 1) {
				log.debug("Entering secondary stage!");
				risk.setStage(RiskConstants.SETUP_TURN);
				risk.setStep(RiskConstants.BEGINNING_OF_TURN);
				rotateController.doPost(request, response, risk);
				return;
			}
		} else {
			log.debug("Territory does not belong to player");
		}

		log.debug("New current player: " + risk.getCurrentPlayer());

		risk.getAppController().forwardUpdatedVariables(request, response, risk);
	}



}
