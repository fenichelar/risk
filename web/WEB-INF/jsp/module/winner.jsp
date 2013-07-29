<%
	String winnerName = risk.getPlayers().get(0).getPlayerName();
	directionsText = risk.getPlayers().get(0).getPlayerName() + ", you won!";
%>
<script type="text/javascript">
	$(function() {
		showalert(
	<%out.write("'" + directionsText + "'");%>
	, "alert-success");
	});
</script>
<h3>Global Domination!</h3>
<form method="GET" action="/risk">
	<input type="submit" class="optionBtn btn btn-large btn-success" value="New Game">
</form>