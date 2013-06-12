package main.java.edu.gatech.cs2340.risk.controller;

import java.io.IOException;
import java.util.TreeMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import main.java.edu.gatech.cs2340.risk.model.Player;
import main.java.edu.gatech.cs2340.risk.service.PlayerService;

//import org.apache.log4j.Logger; TODO figure out how to get this to work


@WebServlet("/app")
public class RiskServlet extends HttpServlet {
	
	//private static Logger log = Logger.getLogger(RiskServlet.class);
	TreeMap<Integer, Player> players = new TreeMap<Integer, Player>(); // GET FROM DATABASE

	protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            		throws IOException, ServletException {
		
		// get players from database
		//players = PlayerService.assignArmiesToPlayers(players);
		request.setAttribute("players", players);
        RequestDispatcher dispatcher = 
            getServletContext().getRequestDispatcher("/app.jsp");
        dispatcher.forward(request,response);
	}
	
	public void setPlayers(TreeMap<Integer, Player> players) {
		this.players = players;
	}

}