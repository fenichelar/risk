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
	
<%if (risk.getStage() == RiskConstants.ATTACK
					&& risk.getStep() == RiskConstants.SELECT_DEFENDING_TERRITORY) {%>
	$(function() {
		$('#attackDialog').modal({
			keyboard : false,
			show : true
		});
		$('.slider').slider();
	});
<%}%>
	
<%if (risk.getStage() == RiskConstants.ATTACK
					&& risk.getStep() == RiskConstants.SELECT_DEFENDING_ARMIES) {%>
	$(function() {
		$('#defendingArmyNumDialog').modal({
			keyboard : false,
			show : true

		});
		$('.slider').slider();
	});
<%}%>
	
<%if (risk.getStage() == RiskConstants.ATTACK
					&& risk.getStep() == RiskConstants.DO_ATTACK) {%>
	$(function() {
		$('#attackResultsDialog').modal({
			keyboard : false,
			show : true
		});
	});
<%}%>
	
<%if (risk.getStage() == RiskConstants.SETUP_TURN
					&& risk.getStep() == RiskConstants.SHOW_OPTIONS) {
	directionsText = currentPlayer.getPlayerName() + ", select your next move.";%>
	$(function() {
		$('#optionsDialog').modal({
			keyboard : false,
			show : true
		});
	});
	$(function() {
		showalert(
<%out.write("'" + directionsText + "'");%>
	, "alert-info");
	});
<%}%>

<%if (risk.getStage() == RiskConstants.MOVE_ARMIES
					&& risk.getStep() == RiskConstants.SELECT_DESTINATION_TERRITORY) {%>
	$(function() {
		$('#fortifyDialog').modal({
			keyboard : false,
			show : true
		});
		$('.slider').slider();
	});
<%}%>

<%if (risk.getStage() == RiskConstants.MOVE_ARMIES
		&& risk.getStep() == RiskConstants.ATTACK_MOVE) {%>
$(function() {
$('#fortifyDialog').modal({
keyboard : false,
show : true
});
$('.slider').slider();
});
<%}%>
	
<%if (risk.getStage() == RiskConstants.DECLARE_WINNER) {%>
	$(function() {
		$('#winnerDialog').modal({
			keyboard : false,
			show : true
		});
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
				<div class="<%if (oddOffset)
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
							Territory attackingTerritory = risk.getAttack()
									.getAttackingTerritory();
							String territoryName = attackingTerritory.getTerritoryName();
							int minArmies = 1;
							int maxArmies = 1;
							if (attackingTerritory.getNumberOfArmies() > 3) {
								maxArmies = 3;
							} else {
								maxArmies = attackingTerritory.getNumberOfArmies() - 1;
							}
							ArrayList<Territory> neighboringTerritories = attackingTerritory
									.getNeighboringTerritories();
							directionsText = currentPlayer.getPlayerName() + ", click a teritory to attack with " + territoryName + ".";

					%>
						<form action="app" method="POST">
							<script type="text/javascript">
								$(function() {
									showalert(
								<%out.write("'" + directionsText + "'");%>
								, "alert-info");
								});
							</script>
								<%
									if (maxArmies > 1) {
								%>
								<p>Select number of armies to attack with</p>
								<span class="sliderContext minArmies"><%=minArmies%></span>
								<input type="text" name="attackingArmyNum" class="slider" value="<%=maxArmies%>" data-slider-min="<%=minArmies%>" data-slider-max="<%=maxArmies%>" data-slider-value="<%=maxArmies%>">
								<span class="sliderContext maxArmies"><%=maxArmies%></span>
								<hr />
								<%
									} else {
								%>
								<input type="hidden" name="attackingArmyNum" value="1" />
								<%
									}
								%>
								<%
									for (Territory neighboringTerritory : attackingTerritory
												.getNeighboringTerritories()) {
								%>
								<%
									if (!neighboringTerritory.getOwner().equals(currentPlayer)) {
								%>
								<input style="display:none;" type="radio" id="<%=neighboringTerritory.getTerritoryId()%>" name="neighboringTerritoryId" value="<%=neighboringTerritory.getTerritoryId()%>" checked>
								<%
									}
								%>
								<%
									}
								%>
								<input type="hidden" name="currentPlayerId" value="<%=currentPlayer.getPlayerId()%>" />
								<input type="hidden" name="cancelled" value="false" />
								<input style="display:none;" id="attacksubmit" type="submit" class="btn btn-primary" value="Attack!" />
						</form>
						<form class="cancelAttack" action="app" method="POST">
							<input type="hidden" name="currentPlayerId" value="<%=currentPlayer.getPlayerId()%>" />
							<input type="hidden" name="cancelled" value="true" /> <input type="submit" class="btn btn-danger" value="Cancel Attack" />
						</form>
					<%
						}
					%>
					<%
						if (risk.getStage() == RiskConstants.ATTACK
								&& risk.getStep() == RiskConstants.DO_ATTACK) {
							int[] attackingArmyDice = (int[]) request
									.getAttribute("attackingArmyDice");
							int[] defendingArmyDice = (int[]) request
									.getAttribute("defendingArmyDice");
							String attackResultsMessage = (String) request
									.getAttribute("attackResultsMessage");
							directionsText = "Attack Results: " + attackResultsMessage;
							String alerttype = "alert-info";
							if(attackResultsMessage.equals("Attack Successful! Territory acquired.")) {
								alerttype = "alert-success";
							}
							if(attackResultsMessage.substring(3,3).equals("t")) {
								alerttype = "alert-warning";
							}
					%>
							<script type="text/javascript">
							$(function() {
								showalert(
							<%out.write("'" + directionsText + "'");%>
							, "<%=alerttype%>");
							});
							</script>
							<p>Attacker Rolled</p>
							<div class="row attackRolls dice">
								<%
									for (int dieValue : attackingArmyDice) {
								%>
								<div class="value<%=dieValue%>"></div>
								<%
									}
								%>
							</div>
							<p>Defender Rolled</p>
							<div class="row defenceRolls dice">
								<%
									for (int dieValue : defendingArmyDice) {
								%>
								<div class="value<%=dieValue%>"></div>
								<%
									}
								%>
							</div>
							<hr />
							<form method="POST" action="app">
								<input type="submit" class="btn btn-primary" value="Continue" />
							</form>
					<%
						}
					%>
					<%
						if (risk.getStage() == RiskConstants.ATTACK
								&& risk.getStep() == RiskConstants.SELECT_DEFENDING_ARMIES) {
							String territoryName = risk.getAttack().getDefendingTerritory().getTerritoryName();
							String attackingTerritoryName = risk.getAttack().getAttackingTerritory().getTerritoryName();
							int minArmies = 1;
							int maxArmies = 2;
							directionsText = risk.getAttack().getDefendingTerritory().getOwner().getPlayerName() + ", " + territoryName + " is being attacked by " + attackingTerritoryName + ".";
					%>
						<script type="text/javascript">
							$(function() {
								showalert(
							<%out.write("'" + directionsText + "'");%>
							, "alert-info");
							});
						</script>
							<form method="POST" action="app">
								<p>Select number of armies to defend with</p>
								<span class="sliderContext minArmies"><%=minArmies%></span>
								<input type="text" name="defendingArmyNum" class="slider" value="<%=maxArmies%>" data-slider-min="<%=minArmies%>" data-slider-max="<%=maxArmies%>" data-slider-value="<%=maxArmies%>">
								<span class="sliderContext maxArmies"><%=maxArmies%></span>
							<hr />
							<input type="submit" class="btn btn-primary" value="Continue" />
							</form>
					<%
						}
					%>
					<%
						if (risk.getStage() == RiskConstants.SETUP_TURN
								&& risk.getStep() == RiskConstants.SHOW_OPTIONS) {

							Boolean hasFortified = (Boolean) request
									.getAttribute("hasFortified");
					%>
							<%
								if (TerritoryUtil.canAttack(currentPlayer)
											&& (hasFortified == null || !hasFortified)) {
							%>
							<form method="POST" action="app">
								<input type="hidden" name="option" value="attack" /> <input type="submit" class="optionBtn btn btn-large btn-primary " value="Attack">
							</form>
							<%
								}
							%>
							<%
								if (TerritoryUtil.canFortify(currentPlayer)
											&& (hasFortified == null || !hasFortified)) {
							%>
							<form method="POST" action="app">
								<input type="hidden" name="option" value="fortify" /> <input type="submit" class="optionBtn btn btn-large btn-success" value="Fortify">
							</form>
							<%
								}
							%>
							<form method="POST" action="app">
								<input type="hidden" name="option" value="end turn" /> <input type="submit" class="optionBtn btn btn-large btn-danger" value="End Turn">
							</form>
					<%
						}
					%>
					<%
						if (risk.getStage() == RiskConstants.MOVE_ARMIES
								&& risk.getStep() == RiskConstants.SELECT_DESTINATION_TERRITORY) {
							Territory source = risk.getMove().getSource();
							String territoryName = source.getTerritoryName();
							int minArmies = 1;
							int maxArmies = source.getNumberOfArmies() - 1;
							ArrayList<Territory> neighboringTerritories = source .getNeighboringTerritories();
							directionsText = currentPlayer.getPlayerName() + ", click a teritory to fortify with " + territoryName + ".";
					%>
						<form action="app" method="POST">
								<script type="text/javascript">
								$(function() {
									showalert(
								<%out.write("'" + directionsText + "'");%>
								, "alert-info");
								});
							</script>
								<%
									if (maxArmies > 1) {
								%>
								<p>Select number of armies to fortify with</p>
								<span class="sliderContext minArmies"><%=minArmies%></span>
								<input type="text" name="numArmies" class="slider" value="<%=maxArmies%>" data-slider-min="<%=minArmies%>" data-slider-max="<%=maxArmies%>" data-slider-value="<%=maxArmies%>">
								<span class="sliderContext maxArmies"><%=maxArmies%></span>
								<hr />
								<%
									} else {
								%>
								<input type="hidden" name="numArmies" value="1" />
								<%
									}
								%>
								<%
									for (Territory neighboringTerritory : source
												.getNeighboringTerritories()) {
								%>
								<%
									if (neighboringTerritory.getOwner().equals(currentPlayer)) {
								%>
								<input style="display:none;" type="radio" id="<%=neighboringTerritory.getTerritoryId()%>" name="neighboringTerritoryId" value="<%=neighboringTerritory.getTerritoryId()%>" checked>
								<%
									}
								%>
								<%
									}
								%>
								<input type="hidden" name="currentPlayerId" value="<%=currentPlayer.getPlayerId()%>" />
								<input type="hidden" name="cancelled" value="false" />
								<input style="display:none;" type="submit" id="fortifysubmit" class="btn btn-primary" value="Fortify" />
						</form>
						<form class="cancelFortify" action="app" method="POST">
							<input type="hidden" name="currentPlayerId" value="<%=currentPlayer.getPlayerId()%>" />
							<input type="hidden" name="cancelled" value="true" />
							<input type="submit" class="btn btn-danger" value="Cancel Fortify" />
						</form>
				<%
					}
				%>
				<%
						if (risk.getStage() == RiskConstants.MOVE_ARMIES
								&& risk.getStep() == RiskConstants.ATTACK_MOVE) {
							Territory source = risk.getMove().getSource();
							String territoryName = source.getTerritoryName();
							int minArmies = 1;
							int maxArmies = source.getNumberOfArmies() - 1;
							ArrayList<Territory> neighboringTerritories = source .getNeighboringTerritories();
							directionsText = currentPlayer.getPlayerName() + ", you have acquired a new territory.";
					%>
						<form action="app" method="POST">
								<script type="text/javascript">
								$(function() {
									showalert(
								<%out.write("'" + directionsText + "'");%>
								, "alert-info");
								});
							</script>
								<%
									if (maxArmies > 1) {
								%>
								<p>Select number of armies to fortify with</p>
								<span class="sliderContext minArmies"><%=minArmies%></span>
								<input type="text" name="numArmies" class="slider" value="<%=maxArmies%>" data-slider-min="<%=minArmies%>" data-slider-max="<%=maxArmies%>" data-slider-value="<%=maxArmies%>">
								<span class="sliderContext maxArmies"><%=maxArmies%></span>
								<hr />
								<%
									} else {
								%>
								<input type="hidden" name="numArmies" value="1" />
								<%
									}
								%>
								<input type="hidden" name="currentPlayerId" value="<%=currentPlayer.getPlayerId()%>" />
								<input type="hidden" name="cancelled" value="false" />
								<input type="submit" class="btn btn-primary" value="Fortify" />
						</form>
				<%
					}
				%>
				<%
					if (risk.getStage() == RiskConstants.DECLARE_WINNER) {
						String winnerName = risk.getPlayers().get(0).getPlayerName();
					directionsText = risk.getPlayers().get(0).getPlayerName() + ", you won!";
					%>
							<script type="text/javascript">
								$(function() {
									showalert(
								<%out.write("'" + directionsText + "'");%>
								, "alert-sucess");
								});
							</script>
						<h3>Global Domination!</h3>
						<form method="POST" action="app">
						</form>
				<%
					}
				%>
			</div>
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
								int show;
								// 0 = none
								// 1 = current players territories
								// 2 = territories that can attack
								// 3 = territories that can be attacked
								// 4 = territories that can transfer
								// 5 = territories that can accept transfers
								// 6 = all
								if (risk.getStage() == RiskConstants.SETUP_TURN && risk.getStep() == RiskConstants.SHOW_OPTIONS) {
									show = 0;
								} else if (risk.getStage() == RiskConstants.ATTACK && risk.getStep() == RiskConstants.SELECT_DEFENDING_ARMIES) {
									show = 0;
								} else if (risk.getStage() == RiskConstants.ATTACK && risk.getStep() == RiskConstants.DO_ATTACK) {
									show = 0;
								} else if (risk.getStage() == RiskConstants.ATTACK && risk.getStep() == RiskConstants.PROCESS_ATTACK) {
									show = 0;
								} else if (risk.getStage() == RiskConstants.MOVE_ARMIES && risk.getStep() == RiskConstants.ATTACK_MOVE) {
									show = 0;
								} else if (risk.getStage() == RiskConstants.INITIALIZE) {
									show = 1;
								} else if (risk.getStage() == RiskConstants.SETUP_TURN && risk.getStep() == RiskConstants.BEFORE_TURN) {
									show = 1;
								} else if (risk.getStage() == RiskConstants.SETUP_TURN && risk.getStep() == RiskConstants.BEGINNING_OF_TURN) {
									show = 1;
								} else if (risk.getStage() == RiskConstants.ATTACK && risk.getStep() == RiskConstants.SELECT_ATTACKING_TERRITORY) {
									show = 2;
								} else if (risk.getStage() == RiskConstants.ATTACK && risk.getStep() == RiskConstants.SELECT_DEFENDING_TERRITORY) {
									show = 3;
								} else if (risk.getStage() == RiskConstants.MOVE_ARMIES && risk.getStep() == RiskConstants.SELECT_SOURCE_TERRITORY) {
									show = 4;
								} else if (risk.getStage() == RiskConstants.MOVE_ARMIES && risk.getStep() == RiskConstants.SELECT_DESTINATION_TERRITORY) {
									show = 5;
								} else {
									show = 6;
								}
								boolean drawn;
								switch (show) {
								case 0:%>
									<input class="territoryButton btn btn-link" disabled type="submit" value="<%=territory.getNumberOfArmies()%>" />
									<%break;
								case 1:
									if(player == currentPlayer) {%>
										<input class="territoryButton btn btn-link" type="submit" value="<%=territory.getNumberOfArmies()%>" />
									<%} else {%>
										<input class="territoryButton btn btn-link" disabled type="submit" value="<%=territory.getNumberOfArmies()%>" />
									<%}
									break;
								case 2:
									if(player == currentPlayer && TerritoryUtil.validAttackTerritory(territory)) {%>
										<input class="territoryButton btn btn-link" type="submit" value="<%=territory.getNumberOfArmies()%>" />
									<%} else {%>
										<input class="territoryButton btn btn-link" disabled type="submit" value="<%=territory.getNumberOfArmies()%>" />
									<%}
									break;
								case 3:
									Territory attackingTerritory = risk.getAttack().getAttackingTerritory();
									drawn = false;
									for (Territory neighboringTerritory : attackingTerritory.getNeighboringTerritories()) {
										if (!neighboringTerritory.getOwner().equals(currentPlayer) && territory==neighboringTerritory) {%>
										<input onClick="javascript: document.getElementById(<%=neighboringTerritory.getTerritoryId()%>).click(),document.getElementById('attacksubmit').click()" class="territoryButton btn btn-link" type="button" value="<%=territory.getNumberOfArmies()%>">
										<%drawn = true;
										}
									}
									if(!drawn) {%>
									<input class="territoryButton btn btn-link" disabled type="submit" value="<%=territory.getNumberOfArmies()%>" />
									<%}
									break;
								case 4:
									if(player == currentPlayer && TerritoryUtil.validFortifyTerritory(territory)) {%>
										<input class="territoryButton btn btn-link" type="submit" value="<%=territory.getNumberOfArmies()%>" />
									<%} else {%>
										<input class="territoryButton btn btn-link" disabled type="submit" value="<%=territory.getNumberOfArmies()%>" />
									<%}
									break;
								case 5:
									Territory source = risk.getMove().getSource();
									drawn = false;
									for (Territory neighboringTerritory : source.getNeighboringTerritories()) {
										if (neighboringTerritory.getOwner().equals(currentPlayer) && territory==neighboringTerritory) {%>
										<input onClick="javascript: document.getElementById(<%=neighboringTerritory.getTerritoryId()%>).click(),document.getElementById('fortifysubmit').click()" class="territoryButton btn btn-link" type="button" value="<%=territory.getNumberOfArmies()%>">
										<%drawn = true;
										}
									}
									if(!drawn) {%>
									<input class="territoryButton btn btn-link" disabled type="submit" value="<%=territory.getNumberOfArmies()%>" />
									<%}
									break;
								case 6:%>
									<input class="territoryButton btn btn-link" type="submit" value="<%=territory.getNumberOfArmies()%>" />
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
