<%@ page import="main.java.edu.gatech.cs2340.risk.model.*" %>
<%@ page import="main.java.edu.gatech.cs2340.risk.service.impl.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.sql.*" %>

<% TerritoryServiceImpl territoryService = new TerritoryServiceImpl(); %>
<% ArrayList<Player> players = 
(ArrayList<Player>) request.getAttribute("players"); %>
<% Player currentPlayer = (Player) request.getAttribute("currentPlayer"); %>

<html>
<head>
	<title>Game of Risk</title>
	<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css" />
	<link rel="stylesheet" type="text/css" href="css/app.css" /> 
</head>
<body>

<div id="wrap" class="container-fluid">

<!-- WRITE PLAYERS IN ROLL ORDER -->

<div class="row-fluid text-center" id="players">
	<% 
		String span = "span" + (12/players.size());
		boolean oddOffset = false;
		
		if (players.size()%2 != 0) {
			span = "span" + (10/players.size());
			oddOffset = true;

		}

	%>
	<% for (Player player : players) { %>

		<div class="<% if (oddOffset) out.write("offset1 "); out.write(span); %> player <% out.write("player" + (player.getPlayerId()-1)); %> <% if (currentPlayer.equals(player)) out.write("active"); %>">
			<% out.write(
			"<h3>" + player.getPlayerName()  + "</h3>"
		  + "<h4>" + player.getAvailableArmies() + " armies</h4>"); %>
		</div>

		<% oddOffset = false; %>

	<% } %>

</div>

<div class="row-fluid" id="map">

	<% for (Player player : players) { %>
		<% for (Territory territory : player.getTerritories()) { %>
			<div class="territory <% out.write("player" + (player.getPlayerId()-1)); %> <% out.write("territory" + territory.getTerritoryId()); %>">
				<form action="app" method="POST">
					<input type="hidden" name="operation" value="POST"/>
					<input type="hidden" name="territoryId" value="<%= territory.getTerritoryId() %>"/>
					<input type="hidden" name="currentPlayerId" value="<%=currentPlayer.getPlayerId()%>"/>
					<input class="territoryButton btn btn-link" type="submit" value="<%= territory.getNumberOfArmies() %>"/>
				</form>
			</div>
	<% }
	} %>
</div>

</div>
</body>
</html>
