package main.java.edu.gatech.cs2340.risk.controller.helper;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import main.java.edu.gatech.cs2340.risk.dao.mock.TerritoryDAOMock;
import main.java.edu.gatech.cs2340.risk.model.Move;
import main.java.edu.gatech.cs2340.risk.model.Risk;
import main.java.edu.gatech.cs2340.risk.model.Territory;
import main.java.edu.gatech.cs2340.risk.util.RiskConstants;

/**
 * Stage 4
 *
 */
public class MoveController extends HttpServlet {
	
	private static Logger log = Logger.getLogger(MoveController.class);
	
	private TurnController turnController = new TurnController();
	
	public void doPost(HttpServletRequest request,
			HttpServletResponse response, Risk risk) throws ServletException, IOException {
		
		switch (risk.getStep()) {
			case RiskConstants.SELECT_SOURCE_TERRITORY: 
				selectSourceTerritory(request, response, risk);
				break;
			case RiskConstants.SELECT_DESTINATION_TERRITORY: 
				selectDestinationTerritory(request, response, risk);
				break;
			case RiskConstants.SELECT_ARMIES_TRANSFERRED:
				selectArmiesTransferred(request, response, risk);
			case RiskConstants.DO_MOVE: 
				doMove(request, response, risk);
				break;
		}
	}
	

	protected void selectSourceTerritory(HttpServletRequest request,
			HttpServletResponse response, Risk risk) throws IOException, ServletException {

		log.debug("In selectSourceTerritory()");

		risk.setCurrentPlayer(Integer.parseInt(request.getParameter("currentPlayerId")));

		TerritoryDAOMock territoryDAO = new TerritoryDAOMock();
		Territory currentTerritory = territoryDAO.getTerritory(risk.getCurrentPlayer(), 
				Integer.parseInt(request.getParameter("territoryId")));

		if (currentTerritory != null && currentTerritory.getNumberOfArmies() > 1) {

			log.debug("Current territory: " + currentTerritory);

			risk.setMove(new Move(currentTerritory));
			log.debug("Changing step to SELECT_FORTIFIED_TERRITORY");
			risk.setStep(RiskConstants.SELECT_DESTINATION_TERRITORY);

		} else {
			log.debug("Territory cannot be used as source territory");
		}
		risk.getAppController().forwardUpdatedVariables(request, response, risk);
	}


	protected void selectDestinationTerritory(HttpServletRequest request,
			HttpServletResponse response, Risk risk) throws ServletException, IOException {

		log.debug("In selectDestinationTerritory()");
	}
	
	protected void selectArmiesTransferred(HttpServletRequest request,
			HttpServletResponse response, Risk risk) throws ServletException, IOException {

		risk.getMove().setNumArmies(Integer.parseInt(request.getParameter("numArmies")));
		log.debug("Changing stage to ATTACK and step to DO ATTACK");
		risk.setStage(RiskConstants.ATTACK);
		risk.setStep(RiskConstants.DO_ATTACK);
		doMove(request, response, risk);
	}


	protected void doMove(HttpServletRequest request,
			HttpServletResponse response, Risk risk) throws ServletException, IOException {

		log.debug("In doMove()");
		risk.getMove().doMove();
		risk.setDirections(RiskConstants.NO_DIRECTIONS);
		risk.setStage(RiskConstants.SETUP_TURN);
		risk.setStep(RiskConstants.SHOW_OPTIONS);
		log.debug("Changing stage to SETUP_TURN and step to SHOW_OPTIONS");
		turnController.determineNextMove(request, response, risk);
	}
}
