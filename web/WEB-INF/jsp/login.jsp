<%@ page import="main.java.edu.gatech.cs2340.risk.model.Player"%>
<%@ page import="java.util.*"%>
<%@ page import="java.sql.*"%>


<%
	ArrayList<Player> players = (ArrayList<Player>) request
			.getAttribute("players");
%>

<html>
<head>
<title>Risk Home Page</title>
<link rel="stylesheet" type="text/css" href="risk/css/login.css" />
</head>

<body>
	<h1>Add Players</h1>
	<table>
		<tr>
			<th>Player Name</th>
		</tr>
		<% for (int id = 0; id < players.size(); id++) { %>
		<% Player player = players.get(id); %>
		<tr>
			<form action="/risk<%=id%>" method="POST">
				<!-- hidden operation element to simulate HTTP PUT method in server -->
				<input type="hidden" name="operation" value="PUT" />
				<td><input type="text" name="name"
					value="<%=player.getPlayerName()%>" /></td>
			</form>
			<td>
				<form action="/risk<%=id%>" method="POST">
					<!-- hidden operation element to simulate HTTP DELETE method in server -->
					<input type="hidden" name="operation" value="DELETE" /> <input
						type="submit" value="Delete" />
				</form>
			</td>
		</tr>
		<% } %>
		<%	if (players.size() >= 3 && players.size() <= 6) { %>
		<tr>
			<td>
				<form action="risk/app" method="GET">
					<a href="javascript:;" onclick="parentNode.submit();"><%="Start Game"%></a>
				</form>
			</td>
		</tr>
		<%	}	%>
		<%	if (players.size() < 6) { %>
		<tr>
			<form action="/risk" method="POST">
				<td><input type="text" name="name" /></td>
				<td><input type="submit" value="Add" /></td>
			</form>
			<td></td>
			<!-- empty cell to align with previous cells -->
		</tr>
		<% } %>
	</table>

</body>
</html>
