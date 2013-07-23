<%@ page import="main.java.edu.gatech.cs2340.risk.model.*"%>
<%@ page import="main.java.edu.gatech.cs2340.risk.service.impl.*"%>
<%@ page import="main.java.edu.gatech.cs2340.risk.util.*"%>
<%@ page import="java.util.*"%>
<%@ page import="java.sql.*"%>
<%
	TerritoryServiceImpl territoryService = new TerritoryServiceImpl();
%>
<%
	ArrayList<Player> players = (ArrayList<Player>) request
			.getAttribute("players");
%>
<%
	Player currentPlayer = (Player) request
			.getAttribute("currentPlayer");
%>
<%
	Risk risk = (Risk) request.getAttribute("risk");
%>
<%@include file="helper/translateStepAndStage.jsp" %>
<%
	String directionsText = "";
	switch (risk.getDirections()) {
	case 0:
		break;
	case 1:
		directionsText = "When it is your turn, click on any territory belonging to you to add an army to it.";
		break;
	case 2:
		directionsText = currentPlayer.getPlayerName()
				+ ", you have "
				+ currentPlayer.getAvailableArmies()
				+ " additional "
				+ (currentPlayer.getAvailableArmies() > 1 ? "armies"
						: "army") + " to distribute.";
		break;
	case 3:
		directionsText = currentPlayer.getPlayerName()
				+ ", select a territory to attack from.";
		break;
	case 4:
		directionsText = currentPlayer.getPlayerName()
				+ ", select a territory to transfer armies from.";
		break;
	}
%>
<html>
<head>
<title>Risk - Game</title>
<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css" href="css/app.css" />
<link rel="stylesheet" type="text/css" href="css/slider.css" />
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
<script type="text/javascript" src="js/bootstrap-slider.js"></script>
<script type="text/javascript">
	function showalert(message, alerttype) {
		$('#alert_placeholder')
				.append(
						'<div id="alertdiv" class="alert ' +  alerttype + '"><a class="close" data-dismiss="alert">×</a><span>'
								+ message + '</span></div>')
	}
<%if (risk.getDirections() != 0) {%>
	$(function() {
		showalert(
<%out.write("'" + directionsText + "'");%>
	, "alert-info");
	});
<%}%>
	
	$(function() {
		$('.slider').slider();
	});

	

	
<%if (risk.getStage() == RiskConstants.SETUP_TURN
					&& risk.getStep() == RiskConstants.SHOW_OPTIONS) {
	directionsText = currentPlayer.getPlayerName() + ", select your next move.";%>
	$(function() {
		showalert(
<%out.write("'" + directionsText + "'");%>
	, "alert-info");
	});
<%}%>

	
	
</script>
</head>
<body>
	<div class="navbar navbar-fixed-top">
		<div class="navbar-inner">
			<div class="row-fluid text-center" id="players">
				<%
					String span = "span" + (12 / players.size());
					boolean oddOffset = false;

					if (players.size() * (12 / players.size()) != 12) {
						span = "span" + (10 / players.size());
						oddOffset = true;

					}
				%>
				<%
					for (Player player : players) {
				%>
				<div
					class="<%if (oddOffset)
					out.write("offset1 ");
				out.write(span);%> player <%out.write("player" + (player.getPlayerId()));%> <%if (currentPlayer.equals(player))
					out.write("active");%>">
					<%
						out.write("<h3>" + player.getPlayerName() + "</h3>" + "<h4>"
									+ player.getAvailableArmies() + " armies</h4>");
					%>
				</div>
				<%
					oddOffset = false;
				%>
				<%
					}
				%>
			</div>
		</div>
		<div id="alert_placeholder"></div>
	</div>
	<div id="wrap" class="container-fluid">
		<div class="row-fluid">
			<div class="span3">
				<div id="sidebar">
					<%
						if (risk.getStage() == RiskConstants.ATTACK
								&& risk.getStep() == RiskConstants.SELECT_DEFENDING_TERRITORY) {
					%>
						<%@include file="module/attack.jsp" %>

					<%
						}
					%>
					<%
						if (risk.getStage() == RiskConstants.ATTACK
								&& risk.getStep() == RiskConstants.DO_ATTACK) {	
					%>
						<%@include file="module/attackResults.jsp" %>	

					<%
						}
					%>

					<%
						if (risk.getStage() == RiskConstants.ATTACK
								&& risk.getStep() == RiskConstants.SELECT_DEFENDING_ARMIES) {
					%>
						<%@include file="module/defendingArmyNum.jsp" %>

					<%
						}
					%>

					<%
						if (risk.getStage() == RiskConstants.SETUP_TURN
								&& risk.getStep() == RiskConstants.SHOW_OPTIONS) {
					%>
						<%@include file="module/options.jsp" %>

					<%
						}
					%>

					<%
						if (risk.getStage() == RiskConstants.MOVE_ARMIES
								&& risk.getStep() == RiskConstants.SELECT_DESTINATION_TERRITORY) {	
					%>
						<%@include file="module/fortify.jsp" %>
					<%
						}
					%>

					<%
						if (risk.getStage() == RiskConstants.MOVE_ARMIES
								&& risk.getStep() == RiskConstants.ATTACK_MOVE) {
					%>
						<%@include file="module/attackMove.jsp" %>
					<%
						}
					%>

					<%
						if (risk.getStage() == RiskConstants.DECLARE_WINNER) {
					%>
						<%@include file="module/winner.jsp" %>
					<%
						}
					%>

			</div>
			<div class="span9" id="map-container">
				<div id="map">
					<%for (Player player : players) {%>
						<%for (Territory territory : player.getTerritories()) {%>
						<div class="territory <%out.write("player" + (player.getPlayerId()));%> <%out.write("territory" + territory.getTerritoryId());%>">
							<form action="app" method="POST">
								<input type="hidden" name="operation" value="POST" />
								<input type="hidden" name="territoryId" value="<%=territory.getTerritoryId()%>" />
								<input type="hidden" name="currentPlayerId" value="<%=currentPlayer.getPlayerId()%>" />
								<%

								boolean drawn;
								switch (show) {
								case 0:%>
							<input class="territoryButton btn btn-link" disabled
								type="submit" value="<%=territory.getNumberOfArmies()%>" />
							<%break;
								case 1:
									if(player == currentPlayer) {%>
							<input class="territoryButton btn btn-link" type="submit"
								value="<%=territory.getNumberOfArmies()%>" />
							<%} else {%>
							<input class="territoryButton btn btn-link" disabled
								type="submit" value="<%=territory.getNumberOfArmies()%>" />
							<%}
									break;
								case 2:
									if(player == currentPlayer && TerritoryUtil.validAttackTerritory(territory)) {%>
							<input class="territoryButton btn btn-link" type="submit"
								value="<%=territory.getNumberOfArmies()%>" />
							<%} else {%>
							<input class="territoryButton btn btn-link" disabled
								type="submit" value="<%=territory.getNumberOfArmies()%>" />
							<%}
									break;
								case 3:
									Territory attackingTerritory = risk.getAttack().getAttackingTerritory();
									drawn = false;
									for (Territory neighboringTerritory : attackingTerritory.getNeighboringTerritories()) {
										if (!neighboringTerritory.getOwner().equals(currentPlayer) && territory==neighboringTerritory) {%>
							<input
								onClick="javascript: document.getElementById(<%=neighboringTerritory.getTerritoryId()%>).click(),document.getElementById('attacksubmit').click()"
								class="territoryButton btn btn-link" type="button"
								value="<%=territory.getNumberOfArmies()%>">
							<%drawn = true;
										}
									}
									if(!drawn) {%>
							<input class="territoryButton btn btn-link" disabled
								type="submit" value="<%=territory.getNumberOfArmies()%>" />
							<%}
									break;
								case 4:
									if(player == currentPlayer && TerritoryUtil.validFortifyTerritory(territory)) {%>
							<input class="territoryButton btn btn-link" type="submit"
								value="<%=territory.getNumberOfArmies()%>" />
							<%} else {%>
							<input class="territoryButton btn btn-link" disabled
								type="submit" value="<%=territory.getNumberOfArmies()%>" />
							<%}
									break;
								case 5:
									Territory source = risk.getMove().getSource();
									drawn = false;
									for (Territory neighboringTerritory : source.getNeighboringTerritories()) {
										if (neighboringTerritory.getOwner().equals(currentPlayer) && territory==neighboringTerritory) {%>
							<input
								onClick="javascript: document.getElementById(<%=neighboringTerritory.getTerritoryId()%>).click(),document.getElementById('fortifysubmit').click()"
								class="territoryButton btn btn-link" type="button"
								value="<%=territory.getNumberOfArmies()%>">
							<%drawn = true;
										}
									}
									if(!drawn) {%>
							<input class="territoryButton btn btn-link" disabled
								type="submit" value="<%=territory.getNumberOfArmies()%>" />
							<%}
									break;
								case 6:%>
							<input class="territoryButton btn btn-link" type="submit"
								value="<%=territory.getNumberOfArmies()%>" />
							<%break;
								}%>
						</form>
					</div>
					<%}%>
					<%}%>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
