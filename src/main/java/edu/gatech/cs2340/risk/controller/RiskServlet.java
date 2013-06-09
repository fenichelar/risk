package main.java.edu.gatech.cs2340.risk.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger; 

@WebServlet(urlPatterns={
        "/list", // GET
        "/create", // ADD
        "/update/*", // UPDATE
        "/delete/*" // DELETE
    })

/**
 * @author Caroline Paulus
 *
 */
public class RiskServlet extends HttpServlet {
	
	private static Logger log = Logger.getLogger(RiskServlet.class); 
 
	@Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws IOException, ServletException {
		
        log.debug("In doPost method");
    }

    /**
     * Called when HTTP method is GET 
     * (e.g., from an <a href="...">...</a> link).
     */
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws IOException, ServletException {
    	
        log.debug("In doGet()");
    }

    protected void doPut(HttpServletRequest request,
                         HttpServletResponse response)
            throws IOException, ServletException {
    	
        log.debug("In doPut()");
    }

    protected void doDelete(HttpServletRequest request,
                            HttpServletResponse response)
            throws IOException, ServletException {
    	
        log.debug("In doDelete()");

    }
}
