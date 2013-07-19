package main.java.edu.gatech.cs2340.risk.controller.helper;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import main.java.edu.gatech.cs2340.risk.dao.mock.TerritoryDAOMock;
import main.java.edu.gatech.cs2340.risk.model.Attack;
import main.java.edu.gatech.cs2340.risk.model.Move;
import main.java.edu.gatech.cs2340.risk.model.Risk;
import main.java.edu.gatech.cs2340.risk.model.Territory;
import main.java.edu.gatech.cs2340.risk.util.RiskConstants;
import main.java.edu.gatech.cs2340.risk.util.TerritoryUtil;

/**
 * Stage 3 (RiskConstants.ATTACK)
 *
 */
public class AttackController extends HttpServlet {

	private static Logger log = Logger.getLogger(AttackController.class);

	private MoveController moveController = new MoveController();
	private TurnController turnController = new TurnController();

	public void doPost(HttpServletRequest request,
			HttpServletResponse response, Risk risk) throws ServletException, IOException {

		switch (risk.getStep()) {
		case RiskConstants.SELECT_ATTACKING_TERRITORY: 
			selectAttackingTerritory(request, response, risk);
			break;
		case RiskConstants.SELECT_DEFENDING_TERRITORY: 
			selectDefendingTerritory(request, response, risk);
			break;
		case RiskConstants.SELECT_DEFENDING_ARMIES: 
			selectDefendingNumberOfArmies(request, response, risk);
			break;
		case RiskConstants.DO_ATTACK: 
			processAttackRequest(request, response, risk);
			break;
		case RiskConstants.PROCESS_ATTACK: 
			processAttackRequest(request, response, risk);
			break;
		}
	}

	/**
	 * Called when a player has selected a territory to attack from
	 * Corresponds to Stage ATTACK, Step SELECT_ATTACKING_TERRITORY
	 * 
	 * @param request
	 * @param response
	 * @param risk  Risk object containing variables for the current game session
	 * @throws IOException
	 * @throws ServletException
	 */
	protected void selectAttackingTerritory(HttpServletRequest request,
			HttpServletResponse response, Risk risk) throws IOException, ServletException {

		log.debug("In selectAttackingTerritory()");

		risk.setCurrentPlayer(Integer.parseInt(request.getParameter("currentPlayerId")));

		TerritoryDAOMock territoryDAO = new TerritoryDAOMock();
		Territory attackingTerritory = territoryDAO.getTerritory(risk.getCurrentPlayer(), 
				Integer.parseInt(request.getParameter("territoryId")));

		if (TerritoryUtil.validAttackTerritory(attackingTerritory)) {

			log.debug("Current territory: " + attackingTerritory);
			risk.setAttack(new Attack(attackingTerritory));

			log.debug("Attacking territory: " + attackingTerritory);
			request.setAttribute("attackingTerritory", attackingTerritory);

			log.debug("Changing step to SELECT_DEFENDING_TERRITORY");
			risk.setStep(RiskConstants.SELECT_DEFENDING_TERRITORY);

		} else {
			log.debug("Territory not satisfactory");
		}
		risk.getAppController().forwardUpdatedVariables(request, response, risk);
	}

	/**
	 * Called when a player has selected another player's territory to attack
	 * Corresponds to Stage ATTACK, Step SELECT_DEFENDING_TERRITORY
	 * 
	 * @param request
	 * @param response
	 * @param risk  Risk object containing variables for the current game session
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void selectDefendingTerritory(HttpServletRequest request,
			HttpServletResponse response, Risk risk) throws ServletException, IOException {

		log.debug("In selectDefendingTerritory()");

		boolean cancelled = Boolean.parseBoolean(request.getParameter("cancelled"));

		if (cancelled) {
			risk.setStage(RiskConstants.SETUP_TURN);
			risk.setStep(RiskConstants.SHOW_OPTIONS);
			risk.getAppController().forwardUpdatedVariables(request, response, risk);
			return;
		}

		risk.getAttack().setAttackingArmyNum(Integer.parseInt(request.getParameter("attackingArmyNum")));

		int neighboringTerritoryId = Integer.parseInt(request.getParameter("neighboringTerritoryId"));
		Territory defendingTerritory = TerritoryUtil.getTerritoryFromNeighborById(
				risk.getAttack().getAttackingTerritory(), neighboringTerritoryId);

		risk.getAttack().setDefendingTerritory(defendingTerritory);
		log.debug("Defending territory: " + defendingTerritory);

		if (defendingTerritory.getNumberOfArmies() > 1) {
			log.debug("Changing step to SELECT_DEFENDING_ARMIES");
			risk.setStep(RiskConstants.SELECT_DEFENDING_ARMIES);
			request.setAttribute("defendingTerritory", defendingTerritory);
		} else {
			risk.getAttack().setDefendingArmyNum(1);
			log.debug("Changing stage to 5");
			risk.setStep(RiskConstants.DO_ATTACK);
			doAttack(request, response, risk);
			return;
		}
		risk.getAppController().forwardUpdatedVariables(request, response, risk);
	}

	/**
	 * Called when the defending player has selected the number of armies to defend with
	 * Corresponds to Stage ATTACK, Step SELECT_DEFENDING_ARMIES
	 * 
	 * @param request
	 * @param response
	 * @param risk  Risk object containing variables for the current game session
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void selectDefendingNumberOfArmies(HttpServletRequest request,
			HttpServletResponse response, Risk risk) throws ServletException, IOException {

		risk.getAttack().setDefendingArmyNum(Integer.parseInt(request.getParameter("defendingArmyNum")));
		log.debug("Changing step to DO_ATTACK");
		risk.setStep(RiskConstants.DO_ATTACK);
		doAttack(request, response, risk);
		return;

	}

	/**
	 * Determines outcome of an attack by "rolling" dice and updating variables
	 * Corresponds to Stage ATTACK, Step DO_ATTACK
	 * 
	 * @param request
	 * @param response
	 * @param risk  Risk object containing variables for the current game session
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void doAttack(HttpServletRequest request,
			HttpServletResponse response, Risk risk) throws ServletException, IOException {

		log.debug("In doAttack()");

		String attackResultsMessage = risk.getAttack().doAttack();

		log.debug(attackResultsMessage);

		request.setAttribute("attackingArmyDice", risk.getAttack().getAttackingArmyDice());
		request.setAttribute("defendingArmyDice", risk.getAttack().getDefendingArmyDice());
		request.setAttribute("attackResultsMessage", attackResultsMessage);

		risk.getAppController().forwardUpdatedVariables(request, response, risk);
	}

	/**
	 * Determines player's options after 
	 * Corresponds to Stage ATTACK, Step PROCESS_ATTACK
	 * 
	 * @param request
	 * @param response
	 * @param risk  Risk object containing variables for the current game session
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void processAttackRequest(HttpServletRequest request,
			HttpServletResponse response, Risk risk) throws ServletException, IOException {

		if (risk.getAttack().defendingTerritoryIsConquered()) {

			risk.setMove(new Move(risk.getAttack().getAttackingTerritory(), 
					risk.getAttack().getDefendingTerritory()));
			
			// there are no armies available to be transferred
			if (risk.getMove().oneArmyAtSourceTerritory()) {
				log.debug("(oneArmyAtSourceTerritory) Move: " + risk.getMove());
				risk.getMove().setNumArmies(1); //TODO is this doing what we want it to?
				log.debug("Changing stage to ATTACK and step to DO_ATTACK");
				risk.setStage(RiskConstants.ATTACK);
				risk.setStep(RiskConstants.DO_ATTACK);

				moveController.doMove(request, response, risk);
				return;
			} 
			else { // there is one or more armies available to be transferred
				log.debug("Move: " + risk.getMove());
				risk.setDirections(RiskConstants.NO_DIRECTIONS);
				log.debug("Changing stage to MOVE and step to SELECT_ARMIES_TRANSFERRED");
				risk.setStage(RiskConstants.MOVE_ARMIES);
				risk.setStep(RiskConstants.SELECT_ARMIES_TRANSFERRED);
				risk.getAppController().forwardUpdatedVariables(request, response, risk);
			}
		} else { // attacking territory was conquered or cannot transfer armies
			risk.setDirections(RiskConstants.NO_DIRECTIONS);
			risk.setStage(RiskConstants.SETUP_TURN);
			risk.setStep(RiskConstants.SHOW_OPTIONS);
			log.debug("Changing stage to SETUP_TURN and stage to SHOW_OPTIONS");
			turnController.determineNextMove(request, response, risk);
			return;
		}

	}


}
