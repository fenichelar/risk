package main.java.edu.gatech.cs2340.risk.controller.helper;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import main.java.edu.gatech.cs2340.risk.dao.mock.TerritoryDAOMock;
import main.java.edu.gatech.cs2340.risk.model.Fortify;
import main.java.edu.gatech.cs2340.risk.model.Risk;
import main.java.edu.gatech.cs2340.risk.model.Territory;
import main.java.edu.gatech.cs2340.risk.util.RiskConstants;

/**
 * Stage 4
 *
 */
public class FortifyController extends HttpServlet {
	
	private static Logger log = Logger.getLogger(FortifyController.class);
	
	public void doPost(HttpServletRequest request,
			HttpServletResponse response, Risk risk) throws ServletException, IOException {
		
		switch (risk.getStep()) {
			case RiskConstants.SELECT_FORTIFYING_TERRITORY: 
				selectFortifyingTerritory(request, response, risk);
				break;
			case RiskConstants.SELECT_FORTIFIED_TERRITORY: 
				selectFortifiedTerritory(request, response, risk);
				break;
			case RiskConstants.DO_FORTIFY: 
				doFortify(request, response, risk);
				break;
		}
	}
	

	protected void selectFortifyingTerritory(HttpServletRequest request,
			HttpServletResponse response, Risk risk) throws IOException, ServletException {

		log.debug("In selectFortifyingTerritory()");

		risk.setCurrentPlayer(Integer.parseInt(request.getParameter("currentPlayerId")));

		TerritoryDAOMock territoryDAO = new TerritoryDAOMock();
		Territory currentTerritory = territoryDAO.getTerritory(risk.getCurrentPlayer(), 
				Integer.parseInt(request.getParameter("territoryId")));

		if (currentTerritory != null && currentTerritory.getNumberOfArmies() > 1) {

			log.debug("Current territory: " + currentTerritory);

			risk.setFortify(new Fortify());
			risk.getFortify().setFortifyingTerritory(currentTerritory);
			log.debug("Fortifying territory: " + risk.getFortify().getFortifyingTerritory());
			request.setAttribute("fortifyingTerritory", risk.getFortify().getFortifyingTerritory());
			log.debug("Changing step to SELECT_FORTIFIED_TERRITORY");
			risk.setStep(RiskConstants.SELECT_FORTIFIED_TERRITORY);

		} else {
			log.debug("Territory not satisfactory");
		}
		risk.getAppController().forwardUpdatedVariables(request, response, risk);
	}


	protected void selectFortifiedTerritory(HttpServletRequest request,
			HttpServletResponse response, Risk risk) throws ServletException, IOException {

		log.debug("In selectFortifiedTerritory()");
	}


	protected void doFortify(HttpServletRequest request,
			HttpServletResponse response, Risk risk) throws ServletException, IOException {

		log.debug("In doFortify()");

	}
}
