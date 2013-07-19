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
import main.java.edu.gatech.cs2340.risk.util.PlayerUtil;
import main.java.edu.gatech.cs2340.risk.util.RiskConstants;

/**
 * Stage 2 (RiskConstants.SETUP_TURN)
 *
 */
public class TurnController extends HttpServlet {
	
	private static Logger log = Logger.getLogger(TurnController.class);
	
	public void doPost(HttpServletRequest request,
			HttpServletResponse response, Risk risk) throws IOException, ServletException {
		
		log.debug("In doPost()");
		switch (risk.getStep()) {
			case RiskConstants.BEFORE_TURN: 
				assignAdditionalArmies(request, response, risk);
				break;
			case RiskConstants.BEGINNING_OF_TURN: 
				distributeAdditionalArmies(request, response, risk);
				break;
			case RiskConstants.SHOW_OPTIONS: 
				determineNextMove(request, response, risk);
				break;
		}
	}
	
	/**
	 * Determines how many armies a player should receive before their turn begins
	 * Corresponds to Stage SETUP_TURN, Step BEFORE_TURN
	 * 
	 * @param request
	 * @param response
	 * @param risk  Risk object containing variables for the current game session
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void assignAdditionalArmies(HttpServletRequest request, 
			HttpServletResponse response, Risk risk) throws ServletException, IOException {

		log.debug("in assignAdditionalArmies()");
		// determine the number of armies the player should receive
		int armiesToAssign = ArmyUtil.getArmiesToAssign(risk.getCurrentPlayer());
		log.debug("Player " + risk.getCurrentPlayer() + " is receiving " 
				+ armiesToAssign + " additional armies");

		risk.getCurrentPlayer().setAvailableArmies(armiesToAssign);
		risk.setStep(RiskConstants.BEGINNING_OF_TURN);
		risk.setDirections(RiskConstants.ADDITIONAL_ARMIES_DIRECTIONS);

		risk.getAppController().forwardUpdatedVariables(request, response, risk);
	}
	
	/**
	 * Called when player is distributing their allotted armies at the beginning of their turn
	 * Corresponds to Stage SETUP_TURN, Step BEGINNING_OF_TURN
	 * 
	 * @param request
	 * @param response
	 * @param risk  Risk object containing variables for the current game session
	 * @throws IOException
	 * @throws ServletException
	 */
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
				risk.setStage(RiskConstants.SETUP_TURN);
				risk.setStep(RiskConstants.SHOW_OPTIONS);
			}

		} else {
			log.debug("Territory does not belong to player");
		}
		risk.getAppController().forwardUpdatedVariables(request, response, risk);
	}
	
	/**
	 * Called after player selects an option from the Option Panel
	 * Launches Attack, Fortify, or End Turn methods based on player's input
	 * Corresponds to Stage SETUP_TURN, Step SHOW_OPTIONS
	 * 
	 * @param request
	 * @param response
	 * @param risk  Risk object containing variables for the current game session
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void determineNextMove(HttpServletRequest request,
			HttpServletResponse response, Risk risk) throws ServletException, IOException {

		log.debug("In determineNextMove");
		String option = request.getParameter("option");
		log.debug("Option: " + option);

		if (option != null) {
			switch (option) {
				case "attack":		risk.setStage(RiskConstants.ATTACK);
									risk.setStep(RiskConstants.SELECT_ATTACKING_TERRITORY);
									risk.setDirections(RiskConstants.SELECT_TERRITORY_DIRECTIONS);	
									break;

				case "fortify":		risk.setStage(RiskConstants.MOVE_ARMIES);
									risk.setStep(RiskConstants.SELECT_SOURCE_TERRITORY);
									break;

				case "end turn":	risk.setDirections(RiskConstants.NO_DIRECTIONS);
									risk.setStage(RiskConstants.SETUP_TURN);
									risk.setStep(RiskConstants.BEFORE_TURN);
									risk.moveToNextPlayer();
									log.debug("New Current Player: " + risk.getCurrentPlayer());
									assignAdditionalArmies(request, response, risk);
									return;
			}
		}
		risk.getAppController().forwardUpdatedVariables(request, response, risk);

	}


}
