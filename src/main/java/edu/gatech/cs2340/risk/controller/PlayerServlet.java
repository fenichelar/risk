package main.java.edu.gatech.cs2340.risk.controller;

import java.io.IOException;
import java.util.TreeMap;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import org.apache.log4j.Logger; TODO figure out how to get this to work

import main.java.edu.gatech.cs2340.risk.model.Player;
// TODO rename
@WebServlet("")
public class PlayerServlet extends HttpServlet {
	
	//private static Logger log = Logger.getLogger(RiskServlet.class); 

    TreeMap<Integer, Player> players = new TreeMap<Integer, Player>();
    RiskServlet riskServlet = new RiskServlet();

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws IOException, ServletException {

        // determines which operation has been requested
        String operation = (String) request.getParameter("operation");

        // if no operation has been requested, it indicates a name was added
        if (operation == null) {
        	// set equal to POST so we don't get a null pointer exception
        	operation = "POST";
        }
        // if a name has been changed, sends request to Put method
        if (operation.equalsIgnoreCase("PUT")) {
            doPut(request, response);
        // if the user pressed Delete, sends request to Delete method
        } else if (operation.equalsIgnoreCase("DELETE")) {
            doDelete(request, response);
        } else if (operation.equalsIgnoreCase("LAUNCH")) {
        	riskServlet.setPlayers(players);
        	riskServlet.doGet(request, response);
        } else {
            String name = request.getParameter("name");
            // create a new Player
            players.put(players.size(), new Player(name, players.size()));
            // send the updated list back to index.jsp
            request.setAttribute("players", players);
            RequestDispatcher dispatcher = 
                getServletContext().getRequestDispatcher("/index.jsp");
            dispatcher.forward(request,response);
        }
    }

    /**
     * Called when page is first loaded
     * Initializes "players" variable for index.jsp
     */
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws IOException, ServletException {
    
        System.out.println("In doGet()");
        request.setAttribute("players", players);
        RequestDispatcher dispatcher = 
            getServletContext().getRequestDispatcher("/index.jsp");
        dispatcher.forward(request,response);
    }

    /**
     * 
     */
    protected void doPut(HttpServletRequest request,
                         HttpServletResponse response)
            throws IOException, ServletException {
        System.out.println("In doPut()");
        String name = (String) request.getParameter("name");
        int id = getId(request);
        players.put(id, new Player(name, id));
        request.setAttribute("players", players);
        RequestDispatcher dispatcher = 
            getServletContext().getRequestDispatcher("/index.jsp");
        dispatcher.forward(request,response);
    }

    protected void doDelete(HttpServletRequest request,
                            HttpServletResponse response)
            throws IOException, ServletException {
        System.out.println("In doDelete()");
        int id = getId(request);
        players.remove(id);
        request.setAttribute("players", players);
        RequestDispatcher dispatcher = 
            getServletContext().getRequestDispatcher("/index.jsp");
        dispatcher.forward(request,response);
    }

    private int getId(HttpServletRequest request) {
        String uri = request.getPathInfo();
        // Strip off the leading slash, e.g. "/2" becomes "2"
        String idStr = uri.substring(1, uri.length()); 
        return Integer.parseInt(idStr);
    }

}