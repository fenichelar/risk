package main.java.edu.gatech.cs2340.risk.controller;

import java.io.IOException; 
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import main.java.edu.gatech.cs2340.risk.model.Player;
import main.java.edu.gatech.cs2340.risk.model.Territory;
import main.java.edu.gatech.cs2340.risk.service.impl.PlayerServiceImpl;
import main.java.edu.gatech.cs2340.risk.service.impl.TerritoryServiceImpl;
import main.java.edu.gatech.cs2340.risk.util.ArmyUtil;
import main.java.edu.gatech.cs2340.risk.util.DiceUtil;
import main.java.edu.gatech.cs2340.risk.util.PlayerUtil;
import main.java.edu.gatech.cs2340.risk.util.TerritoryUtil;

/** 
 * 
 * This class receives and handles user input for the Risk game UI
 */
@WebServlet("/app")
public class AppController extends HttpServlet {

	private static Logger log = Logger.getLogger(AppController.class);

	private PlayerServiceImpl playerService = new PlayerServiceImpl();
	private TerritoryServiceImpl territoryService = new TerritoryServiceImpl();

	private ArrayList<Player> players; 
	private Player currentPlayer;

	private Territory attackingTerritory = null, defendingTerritory = null;
	private int attackingArmyNum = 0;
	private int defendingArmyNum = 0;

	private int stage;
	private Integer directionsList;


	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response)
					throws IOException, ServletException {

		log.debug("In doGet()");

		// get players from jsons
		players = playerService.getPlayers();
		// put players in a random order
		players = PlayerUtil.setPlayerOrder(players);

		// determine the current player
		currentPlayer = players.get(0);
		log.debug("Current player: " + currentPlayer);

		// distribute armies to players
		log.debug("Adding armies to players");
		players = ArmyUtil.addArmies(players);

		// distribute territories to players
		log.debug("Adding territories to players");
		players = territoryService.addTerritories(players);


		// set attributes to be displayed in the game
		request.setAttribute("currentPlayer", currentPlayer);
		request.setAttribute("players", players);

		stage = 1;
		request.setAttribute("stage", stage);

		directionsList = 1;
		request.setAttribute("directionsList", directionsList);


		RequestDispatcher dispatcher = 
				getServletContext().getRequestDispatcher("/app.jsp");
		dispatcher.forward(request,response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response)
					throws IOException, ServletException {

		log.debug("In doPost()");
		switch (stage) {
		case 1: directionsList = 0;
				distributeInitialArmies(request, response);
				break;
		case 2: directionsList = 0;
				distributeAdditionalArmies(request, response);
				break;
		case 3: selectAttackingTerritory(request, response);
				break;
		case 4: directionsList = 0;
				selectDefendingTerritory(request, response);
				break;
		case 5: directionsList = 0;
				stage = 7;
				selectOptions(request, response);
				break;
		case 6: directionsList = 0;
				stage = 6;
				selectDefendingNumberOfArmies(request, response);
				break;
		case 7: directionsList = 0;
				stage = 7;
				selectOptions(request, response);
				break;
		}
	}

	/**
	 * Players assign all their starting armies to territories
	 * Stage 1
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void distributeInitialArmies(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		request.setAttribute("directionsList", directionsList);

		log.debug("In distributeInitialArmies()");

		int currentPlayerId = Integer.parseInt(request.getParameter("currentPlayerId"));
		currentPlayer = PlayerUtil.getPlayerById(players, currentPlayerId);

		log.debug("Current player ID: " + currentPlayerId);

		int territoryId = Integer.parseInt(request.getParameter("territoryId"));
		Territory territory = TerritoryUtil.getTerritoryById(currentPlayer, territoryId);


		// player's list of territories contains the territory AND player has armies left 
		if (territory != null && currentPlayer.getAvailableArmies() > 0) {

			log.debug("Current territory: " + territory);

			territory.addArmy();
			currentPlayer.removeArmy();

			currentPlayer = PlayerUtil.getNextPlayer(players, currentPlayerId);
			currentPlayerId = currentPlayer.getPlayerId();
			if (currentPlayer.getAvailableArmies() < 1) {
				log.debug("Entering secondary stage!");
				stage = 2;
				assignAdditionalArmies(request, response);
				return;
			}

		} else {
			log.debug("Territory does not belong to player");
		}

		log.debug("New current player: " + currentPlayer);
		request.setAttribute("currentPlayer", currentPlayer);

		request.setAttribute("players", players);
		request.setAttribute("stage", stage);

		RequestDispatcher dispatcher = 
				getServletContext().getRequestDispatcher("/app.jsp");
		dispatcher.forward(request,response);
	}

	/**
	 * Assign armies to player at the beginning of a turn
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void assignAdditionalArmies(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {

		// determine the number of armies the player should receive
		int armiesToAssign = ArmyUtil.getArmiesToAssign(currentPlayer);
		log.debug("Player " + currentPlayer + " is receiving " 
				+ armiesToAssign + " additional armies");

		currentPlayer.setAvailableArmies(armiesToAssign);

		directionsList = 2;
		request.setAttribute("directionsList", directionsList);
		request.setAttribute("currentPlayer", currentPlayer);
		request.setAttribute("players", players);
		request.setAttribute("stage", stage);

		RequestDispatcher dispatcher = 
				getServletContext().getRequestDispatcher("/app.jsp");
		dispatcher.forward(request,response);
	}

	/**
	 * Stage 2
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void distributeAdditionalArmies(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		log.debug("In distributeAdditionalArmies()");

		int currentPlayerId = Integer.parseInt(request.getParameter("currentPlayerId"));
		currentPlayer = PlayerUtil.getPlayerById(players, currentPlayerId);

		int territoryId = Integer.parseInt(request.getParameter("territoryId"));
		Territory territory = TerritoryUtil.getTerritoryById(currentPlayer, territoryId);

		// player's list of territories contains the territory AND player has armies left
		if (territory != null && currentPlayer.getAvailableArmies() > 0) {

			log.debug("Current territory: " + territory);

			territory.addArmy();
			currentPlayer.removeArmy();

			if (currentPlayer.getAvailableArmies() == 0) {
				stage = 7;
				directionsList = 0;
			}

		} else {
			log.debug("Territory does not belong to player");
		}

		request.setAttribute("directionsList", directionsList);
		request.setAttribute("currentPlayer", currentPlayer);
		request.setAttribute("players", players);
		request.setAttribute("stage", stage);

		RequestDispatcher dispatcher = 
				getServletContext().getRequestDispatcher("/app.jsp");
		dispatcher.forward(request,response);
	}


	/**
	 * Stage 3
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void selectAttackingTerritory(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		log.debug("In selectAttackingTerritory()");

		request.setAttribute("directionsList", directionsList);

		int currentPlayerId = Integer.parseInt(request.getParameter("currentPlayerId"));
		currentPlayer = PlayerUtil.getPlayerById(players, currentPlayerId);

		int territoryId = Integer.parseInt(request.getParameter("territoryId"));
		Territory territory = TerritoryUtil.getTerritoryById(currentPlayer, territoryId);

		if (territory != null && territory.getNumberOfArmies() > 1) {

			log.debug("Current territory: " + territory);

			attackingTerritory = territory;
			log.debug("Attacking territory: " + attackingTerritory);
			request.setAttribute("attackingTerritory", attackingTerritory);
			log.debug("Changing stage to 4");
			stage = 4;

		} else {
			log.debug("Territory not satisfactory");
		}

		request.setAttribute("currentPlayer", currentPlayer);
		request.setAttribute("players", players);
		request.setAttribute("stage", stage);

		RequestDispatcher dispatcher = 
				getServletContext().getRequestDispatcher("/app.jsp");
		dispatcher.forward(request,response);
	}


	/**
	 * Stage 4
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void selectDefendingTerritory(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		log.debug("In selectDefendingTerritory()");

		boolean cancelled = Boolean.parseBoolean(request.getParameter("cancelled"));

		if (cancelled) {
			stage = 3;
			directionsList = 3;
			request.setAttribute("directionsList", directionsList);
			request.setAttribute("currentPlayer", currentPlayer);
			request.setAttribute("players", players);
			request.setAttribute("stage", stage);

			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/app.jsp");
			dispatcher.forward(request,response);
		}

		int neighboringTerritoryId = Integer.parseInt(request.getParameter("neighboringTerritoryId"));
		defendingTerritory = TerritoryUtil.getTerritoryById(players, neighboringTerritoryId);
		log.debug("Defending territory: " + defendingTerritory);

		attackingArmyNum = Integer.parseInt(request.getParameter("attackingArmyNum"));
		//log.debug("Attacking Army Number: " + attackingArmyNum);

		if (defendingTerritory.getNumberOfArmies() > 1) {
			log.debug("Changing stage to 6");
			stage = 6;
		} else {
			defendingArmyNum = 1;

			log.debug("Changing stage to 5");
			stage = 5;
			doAttack(request, response);
			return;
		}
		request.setAttribute("defendingTerritory", defendingTerritory);
		request.setAttribute("directionsList", directionsList);
		request.setAttribute("currentPlayer", currentPlayer);
		request.setAttribute("players", players);
		request.setAttribute("stage", stage);

		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/app.jsp");
		dispatcher.forward(request,response);
	}


	/**
	 * Stage 5 TODO THIS IS NOT WORKING CORRECTLY
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void doAttack(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		log.debug("In doAttack()");

		// get sorted lists of dice for attacking and defending armies
		int[] attackingArmyDice = DiceUtil.rollDice(Math.min(attackingArmyNum, 3));
		int[] defendingArmyDice = DiceUtil.rollDice(Math.min(defendingTerritory.getNumberOfArmies(), 2));

		log.debug("Attacking Armies " + attackingTerritory.getNumberOfArmies());
		log.debug("Defending Armies " + defendingTerritory.getNumberOfArmies());


		int attackingDiceMax = attackingArmyDice[0];
		int defendingDiceMax = defendingArmyDice[0];

		boolean attackerWin = attackingDiceMax > defendingDiceMax;
		String attackResultsMessage = "";

		if (attackerWin) {
			attackResultsMessage = "Attacker wins! ";
			if (defendingArmyNum > 1) {
				defendingTerritory.removeNumberOfArmies(2);
				attackResultsMessage += "Two Armies Removed.";
			} else {
				defendingTerritory.removeNumberOfArmies(1);
				attackResultsMessage += "One Army Removed.";
			}
			if (defendingTerritory.getNumberOfArmies() < 1) {
				attackResultsMessage = "Attacker wins! Territory acquired.";
				defendingTerritory.getOwner().removeTerritory(defendingTerritory);
				attackingTerritory.getOwner().addTerritory(defendingTerritory);
				defendingTerritory.setNumberOfArmies(attackingArmyNum);
				attackingTerritory.removeNumberOfArmies(attackingArmyNum);
			} 
		} else {
			attackResultsMessage = "Attack unsuccessful. ";
			if (attackingArmyNum > 1) {
				attackingTerritory.removeNumberOfArmies(2);
				attackResultsMessage += "Two Armies Removed.";
			} else {
				attackingTerritory.removeNumberOfArmies(1);
				attackResultsMessage += "One Army Removed.";

			}
			log.debug("Setting attack results message as " + attackResultsMessage);
		}

		request.setAttribute("directionsList", directionsList);
		request.setAttribute("currentPlayer", currentPlayer);
		request.setAttribute("players", players);
		request.setAttribute("stage", stage);
		log.debug("*** STAGE: " + stage);
		request.setAttribute("attackingArmyDice", attackingArmyDice);
		request.setAttribute("defendingArmyDice", defendingArmyDice);
		request.setAttribute("attackResultsMessage", attackResultsMessage);

		RequestDispatcher dispatcher = 
				getServletContext().getRequestDispatcher("/app.jsp");
		dispatcher.forward(request,response);


	}

	/**
	 * Stage 6
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void selectDefendingNumberOfArmies(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		defendingArmyNum = Integer.parseInt(request.getParameter("defendingArmyNum"));

		log.debug("Changing stage to 5");
		stage = 5;
		doAttack(request, response);


		return;

	}

	/**
	 * Stage 7
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void selectOptions(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String option = request.getParameter("option");

		if (option != null) {
			switch (option) {
				case "attack":		stage = 3;
									directionsList = 3;	
									break;

				case "fortify":		//TODO MUST BE WRITTEN --> MOVING TO NEXT PLAYER

				case "end turn":	directionsList = 0;
									stage = 2;
									currentPlayer = PlayerUtil.getNextPlayer(players, currentPlayer.getPlayerId());
									log.debug("New Current Player: " + currentPlayer);
									assignAdditionalArmies(request, response);
									break;
			}
		}

		request.setAttribute("directionsList", directionsList);
		request.setAttribute("currentPlayer", currentPlayer);
		request.setAttribute("players", players);
		request.setAttribute("stage", stage);

		RequestDispatcher dispatcher = 
				getServletContext().getRequestDispatcher("/app.jsp");
		dispatcher.forward(request,response);

	}

}