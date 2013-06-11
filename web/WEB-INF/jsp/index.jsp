<%@ page import="main.java.edu.gatech.cs2340.risk.model.Player" %>
<%@ page import="java.util.*" %>

<% TreeMap<Integer, Player> players = 
   (TreeMap<Integer, Player>) request.getAttribute("players"); %>

<html>
<head>
<title>Risk Web App [home page]</title>
</head>
<body>
<h1>Add Players</h1>

<table>
<tr>
<th>Player Name</th>
</tr>

<% for (Integer id: players.keySet()) { %>
<% Player player = players.get(id); %>
<tr>
<form action="/risk/update/<%= id %>" method="POST">
  <!-- hidden operation element to simulate HTTP PUT method in server -->
  <input type="hidden" name="operation" value="PUT"/>
  <td><input type="text" name="name" value="<%= player.getPlayerName() %>"/></td>
  <td><input type="submit" value="Update"/></td>
</form>
<td valign="bottom">
  <form action="/player/delete/<%= id %>" method="POST">
  <!-- hidden operation element to simulate HTTP DELETE method in server -->
    <input type="hidden" name="operation" value="DELETE"/>
    <input type="submit" value="Delete"/>
  </form>
 </td>
</tr>
<% } %>
<tr>
<form action="/todo/create" method="POST">
  <td><input type="text" name="name"/></td>
  <td><input type="submit" value="Add"/></td>
</form>
<td></td> <!-- empty cell to align with previous cells -->
</tr>
</table>

</body>
</html>