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

import main.java.edu.gatech.cs2340.risk.controller.helper.AttackController;
import main.java.edu.gatech.cs2340.risk.controller.helper.FortifyController;
import main.java.edu.gatech.cs2340.risk.controller.helper.InitializeController;
import main.java.edu.gatech.cs2340.risk.controller.helper.RotateController;
import main.java.edu.gatech.cs2340.risk.model.Player;
import main.java.edu.gatech.cs2340.risk.model.Risk;
import main.java.edu.gatech.cs2340.risk.service.impl.PlayerServiceImpl;
import main.java.edu.gatech.cs2340.risk.service.impl.TerritoryServiceImpl;
import main.java.edu.gatech.cs2340.risk.util.ArmyUtil;
import main.java.edu.gatech.cs2340.risk.util.PlayerUtil;
import main.java.edu.gatech.cs2340.risk.util.RiskConstants;

/** 
 * 
 * This class receives and handles user input for the Risk game UI
 */
@SuppressWarnings("serial")
@WebServlet("/app")
public class AppController extends HttpServlet {

	private static Logger log = Logger.getLogger(AppController.class);
	
	private Risk risk;

	private PlayerServiceImpl playerService = new PlayerServiceImpl();
	private TerritoryServiceImpl territoryService = new TerritoryServiceImpl();
	
	private InitializeController initializeController = new InitializeController();
	private RotateController rotateController = new RotateController();
	private AttackController attackController = new AttackController();
	private FortifyController fortifyController = new FortifyController();


	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response)
					throws IOException, ServletException {

		log.debug("In doGet()");

		// get players from jsons
		ArrayList<Player> players = playerService.getPlayers();
		// put players in a random order
		players = PlayerUtil.setPlayerOrder(players);
		log.debug("players after setPlayerOrder: " + players);

		// distribute armies to players
		log.debug("Adding armies to players");
		players = ArmyUtil.addArmies(players);
		log.debug("players after addArmies: " + players);

		// distribute territories to players
		log.debug("Adding territories to players");
		players = territoryService.addTerritories(players);
		log.debug("players after addTerritories: " + players);

		risk = new Risk(this, players);
		risk.setStage(RiskConstants.INITIALIZE);
		risk.setStep(RiskConstants.NO_STEP);
		risk.setDirectionsNum(RiskConstants.INITIAL_DIRECTION);
		
		forwardUpdatedVariables(request, response, risk);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response)
					throws IOException, ServletException {

		log.debug("In doPost()");
		risk.setDirectionsNum(RiskConstants.NO_DIRECTION);
		switch (risk.getStage()) {
			case RiskConstants.INITIALIZE: 
				initializeController.doPost(request, response, risk);
				break;
			case RiskConstants.ROTATE_PLAYERS: 
				rotateController.doPost(request, response, risk);
				break;
			case RiskConstants.ATTACK: 
				attackController.doPost(request, response, risk);
				break;
			case RiskConstants.FORTIFY: 
				fortifyController.doPost(request, response, risk);
				break;
		}
	}
	
	
	public void forwardUpdatedVariables(HttpServletRequest request,
			HttpServletResponse response, Risk risk) throws IOException, ServletException {

		request.setAttribute("currentPlayer", risk.getCurrentPlayer());
		request.setAttribute("players", risk.getPlayers());
		request.setAttribute("risk", risk);

		RequestDispatcher dispatcher = 
				getServletContext().getRequestDispatcher("/app.jsp");
		dispatcher.forward(request,response);
	}

}