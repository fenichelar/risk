<%@ page import="main.java.edu.gatech.cs2340.risk.model.Player" %>
<%@ page import="java.util.*" %>
<%@ page import="java.sql.*" %>

<% ArrayList<Player> players =
   (ArrayList<Player>) request.getAttribute("players"); %>
<% int i = players.size(); %>
<% out.write(i); %>

<html>
	<head>
		<title>Game of Risk</title>
		<link rel="stylesheet" type="text/css" href="css/app.css" /> 
	</head>

<body>

<h1>GAME OF RISK</h1>

<table>
<tr>
<th>TEST</th>
</tr>

<% for (int id = 0; id < players.size(); id ++) { %>
<% Player player = players.get(id); %>
<% System.out.println(player.getPlayerName()); %>
<% } %>
</table>

</body>
</html>
